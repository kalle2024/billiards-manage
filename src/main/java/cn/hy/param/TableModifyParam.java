package cn.hy.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 球桌修改 参数
 *
 * @author hy
 */
@Setter
@Getter
public class TableModifyParam {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空")
    private Integer id;

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

    /**
     * 球桌状态(0:未启用、1:已启用、2:营运中)
     */
    private Integer status;
}
