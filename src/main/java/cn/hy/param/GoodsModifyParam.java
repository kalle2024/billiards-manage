package cn.hy.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 商品修改 参数
 *
 * @author hy
 */
@Setter
@Getter
public class GoodsModifyParam {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空")
    private Integer id;

    /**
     * 商品类型id
     */
    @NotNull(message = "商品类型id不能为空")
    private Integer goodsTypeId;

    /**
     * 商品名称
     */
    @NotBlank(message = "商品名称不能为空")
    private String name;

    /**
     * 商品价格
     */
    @NotNull(message = "商品价格不能为空")
    private BigDecimal price;
}
