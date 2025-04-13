package cn.hy.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 用户录入 参数
 *
 * @author hy
 */
@Setter
@Getter
public class UserEnterParam {

    /**
     * 用户名称
     */
    @NotBlank(message = "用户名称不能为空")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 手机号码
     */
    private String mobile;

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
