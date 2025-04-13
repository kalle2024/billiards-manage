package cn.hy.param;

import cn.hy.model.Goods;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Getter;
import lombok.Setter;

/**
 * 商品分页查询 参数
 *
 * @author hy
 */
@Setter
@Getter
public class GoodsPageParam extends Page<Goods> {

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品类型id
     */
    private Integer goodsTypeId;
}
