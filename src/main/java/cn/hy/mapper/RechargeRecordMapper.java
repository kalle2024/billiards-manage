package cn.hy.mapper;

import cn.hy.model.RechargeRecord;
import cn.hy.param.RechargePageParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;

/**
 * 充值记录 Mapper
 *
 * @author hy
 */
@Mapper
public interface RechargeRecordMapper extends BaseMapper<RechargeRecord> {

    default Page<RechargeRecord> selectPage(RechargePageParam param) {
        return selectPage(param, new LambdaQueryWrapper<RechargeRecord>()
                .eq(RechargeRecord::getUserId, param.getUserId())
                .eq(RechargeRecord::getOperatorId, param.getOperatorId())
        );
    }
}
