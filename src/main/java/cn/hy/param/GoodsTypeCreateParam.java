package cn.hy.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 商品类型创建 参数
 *
 * @author hy
 */
@Setter
@Getter
public class GoodsTypeCreateParam {

    /**
     * 商品类型
     */
    @NotBlank(message = "商品类型不能为空")
    private String type;
}
