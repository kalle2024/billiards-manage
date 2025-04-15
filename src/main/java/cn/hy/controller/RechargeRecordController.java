package cn.hy.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.hy.enums.UserTypeEnum;
import cn.hy.mapper.RechargeRecordMapper;
import cn.hy.mapper.UserMapper;
import cn.hy.model.RechargeRecord;
import cn.hy.param.RechargePageParam;
import cn.hy.param.RechargeParam;
import cn.hy.util.UserUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 充值记录 Controller
 *
 * @author hy
 */
@RestController
@RequestMapping("/recharge-record")
public class RechargeRecordController {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RechargeRecordMapper rechargeRecordMapper;

    @PostMapping("/recharge")
    @Transactional(rollbackFor = Exception.class)
    @SaCheckRole(value = {"SUPER_ADMIN", "STAFF"}, mode = SaMode.OR)
    public SaResult recharge(@RequestBody @Valid RechargeParam param) {
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

        // 获取充值记录列表
        Page<RechargeRecord> page = rechargeRecordMapper.selectPage(param);
        return SaResult.data(page);
    }
}
