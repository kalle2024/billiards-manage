package cn.hy.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 用户充值 参数
 *
 * @author hy
 */
@Setter
@Getter
public class RechargeParam {

    /**
     * 充值用户id
     */
    @NotNull(message = "充值用户id不能为空")
    private Integer userId;

    /**
     * 金额
     */
    @NotNull(message = "金额不能为空")
    private BigDecimal amount;
}
