package cn.hy.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 用户类型 枚举
 *
 * @author hy
 */
@Getter
@AllArgsConstructor
public enum UserTypeEnum {

    USER(0, "USER"),
    MEMBER(1, "MEMBER"),
    STAFF(2, "STAFF"),
    SUPER_ADMIN(-1, "SUPER_ADMIN");

    final int type;
    final String code;

    public static UserTypeEnum of(int type) {
        for (UserTypeEnum userTypeEnum : values()) {
            if (Objects.equals(type, userTypeEnum.getType())) {
                return userTypeEnum;
            }
        }
        return USER;
    }
}
