package cn.hy.param;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 用户修改 参数
 *
 * @author hy
 */
@Setter
@Getter
public class UserModifyParam {

    /**
     * 主键
     */
    @NotNull(message = "用户id不能为空")
    private Integer id;

    /**
     * 用户名称
     */
    @NotNull(message = "用户名称不能为空")
    private String username;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像链接
     */
    private String avatarUrl;

    /**
     * 用户类型(0:普通用户、1:会员、2:员工、-1:超级管理员)
     */
    private Integer userType;
}
