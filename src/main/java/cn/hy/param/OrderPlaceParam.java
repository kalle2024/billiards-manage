package cn.hy.param;

import lombok.Getter;
import lombok.Setter;

/**
 * 下单 参数
 *
 * @author hy
 */
@Setter
@Getter
public class OrderPlaceParam {

    /**
     * 商品id
     */
    private Integer goodsId;
}
