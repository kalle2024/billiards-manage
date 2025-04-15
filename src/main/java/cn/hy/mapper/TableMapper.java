package cn.hy.mapper;

import cn.hy.model.Table;
import cn.hy.param.TablePageParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;

/**
 * 球桌 Mapper
 *
 * @author hy
 */
@Mapper
public interface TableMapper extends BaseMapper<Table> {

    default Page<Table> selectPage(TablePageParam param) {
        return selectPage(param, new LambdaQueryWrapper<Table>()
                .eq(Table::getType, param.getType())
                .like(Table::getName, param.getName())
        );
    }
}
