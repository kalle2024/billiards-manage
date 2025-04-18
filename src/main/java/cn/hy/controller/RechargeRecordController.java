package cn.hy.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.core.util.StrUtil;
import cn.hy.enums.UserTypeEnum;
import cn.hy.mapper.RechargeRecordMapper;
import cn.hy.mapper.UserMapper;
import cn.hy.model.RechargeRecord;
import cn.hy.model.User;
import cn.hy.param.RechargePageParam;
import cn.hy.param.RechargeParam;
import cn.hy.util.UserUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 充值记录 Controller
 *
 * @author hy
 */
@RestController
@RequestMapping("/api/recharge-record")
public class RechargeRecordController {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RechargeRecordMapper rechargeRecordMapper;

    @PostMapping("/recharge")
    @Transactional(rollbackFor = Exception.class)
    @SaCheckRole(value = {"SUPER_ADMIN", "STAFF"}, mode = SaMode.OR)
    public SaResult recharge(@RequestBody @Valid RechargeParam param) {
        if (param.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            return SaResult.error("充值金额必须大于0");
        }

        // 给用户充值
        userMapper.plusBalance(param.getUserId(), param.getAmount());

        // 新增充值记录
        RechargeRecord rechargeRecord = new RechargeRecord();
        rechargeRecord.setUserId(param.getUserId());
        rechargeRecord.setAmount(param.getAmount());
        rechargeRecord.setOperatorId(UserUtil.getLoginUserId());
        rechargeRecordMapper.insert(rechargeRecord);
        return SaResult.ok("充值成功");
    }

    @PostMapping("/page")
    public SaResult page(@RequestBody @Valid RechargePageParam param) {
        // 会员或者普通用户，就只能看到自己的充值记录
        if (StpUtil.hasRoleOr(UserTypeEnum.MEMBER.getCode(), UserTypeEnum.USER.getCode())) {
            param.setUserId(UserUtil.getLoginUserId());
        }

        // 充值用户ids
        if (StrUtil.isNotBlank(param.getUsername())) {
            List<User> users = userMapper.selectList(new LambdaQueryWrapper<User>()
                    .like(User::getUsername, param.getUsername())
            );
            param.setUserIds(users.stream().map(User::getId).collect(Collectors.toList()));
        }

        // 获取充值记录列表
        Page<RechargeRecord> page = rechargeRecordMapper.selectPage(param);

        // 所有用户信息
        List<User> users = userMapper.selectList(null);
        Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(User::getId, Function.identity()));

        User emptyUser = new User();
        Page<Map<String, Object>> resultPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        resultPage.setRecords(page.getRecords().stream().map(rechargeRecord -> {
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("id", rechargeRecord.getId());
            dataMap.put("rechargeRecord", rechargeRecord);
            dataMap.put("user", userMap.getOrDefault(rechargeRecord.getUserId(), emptyUser));
            dataMap.put("operator", userMap.getOrDefault(rechargeRecord.getOperatorId(), emptyUser));
            return dataMap;
        }).collect(Collectors.toList()));
        return SaResult.data(resultPage);
    }
}
