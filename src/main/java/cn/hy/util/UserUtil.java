package cn.hy.util;

import cn.dev33.satoken.stp.StpUtil;
import lombok.experimental.UtilityClass;

/**
 * 用户工具类
 *
 * @author hy
 */
@UtilityClass
public class UserUtil {

    public static Integer getLoginUserId() {
        return Integer.parseInt(StpUtil.getLoginId().toString());
    }
}
