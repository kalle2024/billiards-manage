package cn.hy.mapper;

import cn.hy.model.GoodsType;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品类型 Mapper
 *
 * @author hy
 */
@Mapper
public interface GoodsTypeMapper extends BaseMapper<GoodsType> {

    default GoodsType getByType(String type) {
        return selectOne(new LambdaQueryWrapper<GoodsType>()
                .eq(GoodsType::getType, type));
    }
}
