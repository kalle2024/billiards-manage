package cn.hy.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 用户名称-密码登录 参数
 *
 * @author hy
 */
@Setter
@Getter
public class UsernameLoginParam {

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
}
