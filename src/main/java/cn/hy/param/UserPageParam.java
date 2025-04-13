package cn.hy.param;

import cn.hy.model.User;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户分页查询 参数
 *
 * @author hy
 */
@Setter
@Getter
public class UserPageParam extends Page<User> {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 用户类型(0:普通用户、1:会员、2:员工、-1:超级管理员)
     */
    private Integer userType;
}
