package cn.hy.satoken;

import cn.dev33.satoken.stp.StpInterface;
import cn.hy.enums.UserTypeEnum;
import cn.hy.mapper.UserMapper;
import cn.hy.model.User;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * 角色权限认证，目前没有做权限，只有角色
 *
 * @author hy
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private UserMapper userMapper;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return Collections.emptyList();
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        Integer userId = Integer.parseInt((String) loginId);
        // 获取当前用户的所有角色id
        User user = userMapper.selectById(userId);
        // 返回角色代码
        UserTypeEnum userTypeEnum = UserTypeEnum.of(user.getUserType());

        // 一个用户有且仅有单个角色，不能同时存在多角色
        return Collections.singletonList(userTypeEnum.getCode());
    }
}
