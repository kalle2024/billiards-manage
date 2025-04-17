package cn.hy.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.hy.enums.OrderStatusEnum;
import cn.hy.enums.UserTypeEnum;
import cn.hy.mapper.GoodsMapper;
import cn.hy.mapper.OrderMapper;
import cn.hy.mapper.UserMapper;
import cn.hy.model.Goods;
import cn.hy.model.Order;
import cn.hy.param.OrderPlaceParam;
import cn.hy.util.UserUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 订单 Controller
 *
 * @author hy
 */
@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private UserMapper userMapper;

    @PostMapping("/place")
    @Transactional(rollbackFor = Exception.class)
    public SaResult place(@RequestBody @Valid OrderPlaceParam param) {
        // 当前登录人id
        Integer loginUserId = UserUtil.getLoginUserId();

        // 获取商品信息
        Goods goods = goodsMapper.selectById(param.getGoodsId());

        // 对应用户的余额扣减
        int update = userMapper.minusBalance(loginUserId, goods.getPrice());
        if (update == 0) {
            return SaResult.error("用户余额扣减失败，请确保余额充足");
        }

        // 下单
        Order order = new Order();
        order.setStatus(OrderStatusEnum.TO_BE_USED.getStatus());
        order.setUserId(loginUserId);
        order.setGoodsId(param.getGoodsId());
        order.setOriginGoodsName(goods.getName());
        order.setPrice(goods.getPrice());
        orderMapper.insert(order);
        return SaResult.ok("下单成功");
    }

    @PostMapping("/write-off/{id}")
    @SaCheckRole(value = {"SUPER_ADMIN", "STAFF"}, mode = SaMode.OR)
    public SaResult writeOffById(@PathVariable("id") Integer id) {
        // 核销订单，变更订单状态为已使用
        int update = orderMapper.update(null, new LambdaUpdateWrapper<Order>()
                .eq(Order::getId, id)
                .eq(Order::getStatus, OrderStatusEnum.TO_BE_USED.getStatus())
                .set(Order::getStatus, OrderStatusEnum.USED.getStatus())
        );
        if (update == 0) {
            return SaResult.error("核销失败，当前订单已核销或者已取消");
        }

        return SaResult.ok("核销成功");
    }

    @PostMapping("/cancel/{id}")
    @Transactional(rollbackFor = Exception.class)
    public SaResult cancelById(@PathVariable("id") Integer id) {
        // 如果是待使用的订单，当前人可以取消，或者超级管理员，店员可以取消
        if (StpUtil.hasRoleOr(UserTypeEnum.SUPER_ADMIN.getCode(), UserTypeEnum.STAFF.getCode())) {
            int update = orderMapper.update(null, new LambdaUpdateWrapper<Order>()
                    .eq(Order::getId, id)
                    .ne(Order::getStatus, OrderStatusEnum.CANCELLED.getStatus())
                    .set(Order::getStatus, OrderStatusEnum.CANCELLED.getStatus())
            );
            if (update == 0) {
                return SaResult.error("取消失败");
            }
        } else {
            // 取消自己的订单
            int update = orderMapper.update(null, new LambdaUpdateWrapper<Order>()
                    .eq(Order::getId, id)
                    .eq(Order::getUserId, UserUtil.getLoginUserId())
                    .ne(Order::getStatus, OrderStatusEnum.CANCELLED.getStatus())
                    .set(Order::getStatus, OrderStatusEnum.CANCELLED.getStatus())
            );
            if (update == 0) {
                return SaResult.error("取消失败");
            }
        }

        // 把金额加回到用户余额中
        Order order = orderMapper.selectById(id);
        userMapper.plusBalance(order.getUserId(), order.getPrice());
        return SaResult.ok("取消成功");
    }
}
