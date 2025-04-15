package cn.hy.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 球桌状态 枚举
 *
 * @author hy
 */
@Getter
@AllArgsConstructor
public enum TableStatusEnum {

    NOT_ENABLED(0, "未启用"),
    ENABLED(1, "已启用"),
    IN_USE(2, "营运中");

    final int status;
    final String msg;
}
