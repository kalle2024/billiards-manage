package cn.hy.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 球桌创建 参数
 *
 * @author hy
 */
@Setter
@Getter
public class TableCreateParam {

    /**
     * 球桌类型(1:中式八球、2:斯诺克)
     */
    @NotNull(message = "球桌类型不能为空")
    private Integer type;

    /**
     * 球桌名称
     */
    @NotBlank(message = "球桌名称不能为空")
    private String name;
}
