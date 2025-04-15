package cn.hy.mapper;

import cn.hy.model.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单 Mapper
 *
 * @author hy
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

}
