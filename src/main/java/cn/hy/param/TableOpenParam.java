package cn.hy.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 球桌开台 参数
 *
 * @author hy
 */
@Setter
@Getter
public class TableOpenParam {

    /**
     * 订单id
     */
    @NotNull(message = "订单id不能为空")
    private Integer orderId;

    /**
     * 球桌id
     */
    @NotNull(message = "球桌id不能为空")
    private Integer tableId;

    /**
     * 开台时间，默认2小时
     */
    @Min(value = 1, message = "开台时间不能低于1小时")
    private Integer hours = 2;
}
