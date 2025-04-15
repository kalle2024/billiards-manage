package cn.hy.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单状态 枚举
 *
 * @author hy
 */
@Getter
@AllArgsConstructor
public enum OrderStatusEnum {

    TO_BE_USED(0, "待使用"),
    USED(1, "已使用"),
    CANCELLED(2, "已取消");

    final int status;
    final String msg;
}
