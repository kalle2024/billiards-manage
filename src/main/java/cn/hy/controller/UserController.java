package cn.hy.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hy.enums.UserTypeEnum;
import cn.hy.mapper.UserMapper;
import cn.hy.model.User;
import cn.hy.param.UserEnterParam;
import cn.hy.param.UserModifyParam;
import cn.hy.param.UserPageParam;
import cn.hy.param.UsernameLoginParam;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Objects;

/**
 * 用户 Controller
 *
 * @author hy
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserMapper userMapper;

    @PostMapping("/login")
    public SaResult login(@RequestBody @Valid UsernameLoginParam param) {
        User user = userMapper.getByUsername(param.getUsername());
        if (user == null || !StrUtil.equals(user.getPassword(), param.getPassword())) {
            return SaResult.error("用户名或者密码不正确");
        }
        StpUtil.login(user.getId());
        return SaResult.ok("登录成功");
    }

    @PostMapping("/logout")
    public SaResult logout() {
        StpUtil.logout();
        return SaResult.ok("退出登录成功");
    }

    @PostMapping("/enter")
    public SaResult enter(@RequestBody @Valid UserEnterParam param) {
        // 只有超级管理员和员工可以录入信息
        if (!StpUtil.hasRoleOr(UserTypeEnum.SUPER_ADMIN.getCode(), UserTypeEnum.STAFF.getCode())) {
            return SaResult.error("您当前没有录入人员信息的权限");
        }
        // 超级管理员只能后台脚本添加，不允许页面添加，没有任何角色的就是普通人，不享有会员的福利
        // 判断用户是否已存在
        User user = userMapper.getByUsername(param.getUsername());
        if (Objects.nonNull(user)) {
            return SaResult.error("用户已存在，请勿重复录入");
        }
        // 如果没有超级管理员的用户类型，但是又想给别人赋予超管类型，那么直接提示不允许
        if (!StpUtil.hasRole(UserTypeEnum.SUPER_ADMIN.getCode()) && Objects.equals(param.getUserType(), UserTypeEnum.SUPER_ADMIN.getType())) {
            return SaResult.error("您当前没有超级管理员类型，无法给别人赋予超级管理员类型");
        }
        // 添加用户
        user = new User();
        user.setUsername(param.getUsername());
        user.setPassword(param.getPassword());
        user.setMobile(param.getMobile());
        user.setNickname(param.getNickname());
        user.setAvatarUrl(param.getAvatarUrl());
        user.setUserType(param.getUserType());
        userMapper.insert(user);
        return SaResult.ok("录入成功");
    }

    @PostMapping("/modify")
    public SaResult modify(@RequestBody @Valid UserModifyParam param) {
        // 只有超级管理员和员工可以录入信息
        if (!StpUtil.hasRoleOr(UserTypeEnum.SUPER_ADMIN.getCode(), UserTypeEnum.STAFF.getCode())) {
            return SaResult.error("您当前没有录入人员信息的权限");
        }
        // 如果没有超级管理员的用户类型，但是又想给别人赋予超管类型，那么直接提示不允许
        if (!StpUtil.hasRole(UserTypeEnum.SUPER_ADMIN.getCode()) && Objects.equals(param.getUserType(), UserTypeEnum.SUPER_ADMIN.getType())) {
            return SaResult.error("您当前没有超级管理员类型，无法给别人赋予超级管理员类型");
        }
        // 修改信息
        User user = new User();
        BeanUtil.copyProperties(param, user);
        userMapper.updateById(user);
        return SaResult.ok("修改成功");
    }

    @PostMapping("/delete/{id}")
    public SaResult deleteById(@PathVariable("id") Integer id) {
        // 只有超级管理员和员工可以录入信息
        if (!StpUtil.hasRoleOr(UserTypeEnum.SUPER_ADMIN.getCode(), UserTypeEnum.STAFF.getCode())) {
            return SaResult.error("您当前没有录入人员信息的权限");
        }
        User user = userMapper.selectById(id);
        // 如果没有超级管理员的用户类型，但是又想给别人赋予超管类型，那么直接提示不允许
        if (!StpUtil.hasRole(UserTypeEnum.SUPER_ADMIN.getCode()) && Objects.equals(user.getUserType(), UserTypeEnum.SUPER_ADMIN.getType())) {
            return SaResult.error("您当前没有超级管理员类型，无法删除超级管理员");
        }
        if (!StpUtil.hasRole(UserTypeEnum.SUPER_ADMIN.getCode()) && Objects.equals(user.getUserType(), UserTypeEnum.STAFF.getType())) {
            return SaResult.error("您当前没有超级管理员类型，无法删除员工");
        }
        int delete = userMapper.deleteById(id);
        if (delete == 0) {
            return SaResult.error("删除失败");
        }
        return SaResult.ok("删除成功");
    }

    @PostMapping("/page")
    public SaResult page(@RequestBody @Valid UserPageParam param) {
        Page<User> page = userMapper.selectPage(param);
        return SaResult.data(page);
    }

    @GetMapping("/get/{id}")
    public SaResult getById(@PathVariable("id") Integer id) {
        User user = userMapper.selectById(id);
        return SaResult.data(user);
    }
}
