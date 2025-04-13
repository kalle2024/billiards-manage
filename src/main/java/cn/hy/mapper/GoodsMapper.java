package cn.hy.mapper;

import cn.hy.model.Goods;
import cn.hy.param.GoodsPageParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品 Mapper
 *
 * @author hy
 */
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

    default long selectCountByGoodsTypeId(Integer goodsTypeId) {
        return selectCount(new LambdaQueryWrapper<Goods>()
                .eq(Goods::getGoodsTypeId, goodsTypeId)
        );
    }

    default Page<Goods> selectPage(GoodsPageParam param) {
        return selectPage(param, new LambdaQueryWrapper<Goods>()
                .eq(Goods::getGoodsTypeId, param.getGoodsTypeId())
                .like(Goods::getName, param.getName())
        );
    }
}
