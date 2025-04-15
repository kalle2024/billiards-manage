package cn.hy.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.core.bean.BeanUtil;
import cn.hy.enums.OrderStatusEnum;
import cn.hy.enums.TableStatusEnum;
import cn.hy.mapper.OrderMapper;
import cn.hy.mapper.TableMapper;
import cn.hy.mapper.TableOpenRecordMapper;
import cn.hy.model.Order;
import cn.hy.model.Table;
import cn.hy.model.TableOpenRecord;
import cn.hy.param.TableCreateParam;
import cn.hy.param.TableModifyParam;
import cn.hy.param.TableOpenParam;
import cn.hy.param.TablePageParam;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 球桌 Controller
 *
 * @author hy
 */
@RestController
@RequestMapping("/table")
public class TableController {

    @Resource
    private TableMapper tableMapper;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private TableOpenRecordMapper tableOpenRecordMapper;

    /**
     * 创建一个定时任务，到点自动关台
     */
    private static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = Executors.newScheduledThreadPool(2);

    @PostMapping("/create")
    public SaResult create(@RequestBody @Valid TableCreateParam param) {
        Table table = new Table();
        BeanUtil.copyProperties(param, table);
        table.setStatus(TableStatusEnum.NOT_ENABLED.getStatus());
        tableMapper.insert(table);
        return SaResult.ok("创建成功");
    }

    @PostMapping("/modify")
    public SaResult modify(@RequestBody @Valid TableModifyParam param) {
        Table table = new Table();
        BeanUtil.copyProperties(param, table);
        // 如果当前是修改球桌状态，要判断是否能修改
        int update = tableMapper.update(null, new LambdaUpdateWrapper<Table>()
                .eq(Table::getId, param.getId())
                .ne(Table::getStatus, TableStatusEnum.IN_USE.getStatus())
                .set(Table::getType, param.getType())
                .set(Table::getName, param.getName())
        );
        if (update == 0) {
            SaResult.error("修改失败");
        }
        return SaResult.ok("修改成功");
    }

    @PostMapping("/delete/{id}")
    public SaResult deleteById(@PathVariable("id") Integer id) {
        int delete = tableMapper.deleteById(id);
        if (delete == 0) {
            return SaResult.error("删除失败");
        }
        return SaResult.ok("删除成功");
    }

    @PostMapping("/page")
    public SaResult page(@RequestBody @Valid TablePageParam param) {
        Page<Table> page = tableMapper.selectPage(param);
        return SaResult.data(page);
    }

    @GetMapping("/get/{id}")
    public SaResult getById(@PathVariable("id") Integer id) {
        Table table = tableMapper.selectById(id);
        return SaResult.data(table);
    }

    @PostMapping("/open")
    @Transactional(rollbackFor = Exception.class)
    @SaCheckRole(value = {"SUPER_ADMIN", "STAFF"}, mode = SaMode.OR)
    public SaResult open(@RequestBody @Valid TableOpenParam param) {
        // 检查订单状态是否为已使用
        Order order = orderMapper.selectById(param.getOrderId());
        if (Objects.equals(order.getStatus(), OrderStatusEnum.CANCELLED.getStatus())) {
            return SaResult.error("订单已取消，无法开台");
        } else if (Objects.equals(order.getStatus(), OrderStatusEnum.TO_BE_USED.getStatus())) {
            return SaResult.error("请先核销订单后再开台");
        }

        // 检查球桌是否允许开台
        Table table = tableMapper.selectById(param.getTableId());
        if (Objects.equals(table.getStatus(), TableStatusEnum.NOT_ENABLED.getStatus())) {
            return SaResult.error("请先启用球桌后再开台");
        } else if (Objects.equals(table.getStatus(), TableStatusEnum.IN_USE.getStatus())) {
            return SaResult.error("该球桌当前营运中，请选择其他球桌");
        }

        // 新增开台记录，记录到期时间
        TableOpenRecord tableOpenRecord = new TableOpenRecord();
        tableOpenRecord.setOrderId(param.getOrderId());
        tableOpenRecord.setTableId(param.getTableId());
        /*
         * 这里的时长默认是2个小时，可以改的
         */
        tableOpenRecord.setExpireTime(LocalDateTime.now().plusHours(param.getHours()));
        tableOpenRecordMapper.insert(tableOpenRecord);

        // 修改球桌状态为运营中
        table.setStatus(TableStatusEnum.IN_USE.getStatus());
        table.setUpdateTime(LocalDateTime.now());
        tableMapper.updateById(table);

        // 自动关台任务
        SCHEDULED_EXECUTOR_SERVICE.schedule(() -> {
            // 设置状态为已启用，不再是运营状态
            table.setStatus(TableStatusEnum.ENABLED.getStatus());
            table.setUpdateTime(LocalDateTime.now());
            tableMapper.updateById(table);
        }, param.getHours(), TimeUnit.HOURS);
        return SaResult.ok("开台成功");
    }
}
