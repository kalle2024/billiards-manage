package cn.hy.mapper;

import cn.hutool.core.collection.CollUtil;
import cn.hy.model.RechargeRecord;
import cn.hy.param.RechargePageParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.Objects;

/**
 * 充值记录 Mapper
 *
 * @author hy
 */
@Mapper
public interface RechargeRecordMapper extends BaseMapper<RechargeRecord> {

    default Page<RechargeRecord> selectPage(RechargePageParam param) {
        return selectPage(param, new LambdaQueryWrapper<RechargeRecord>()
                .eq(Objects.nonNull(param.getUserId()), RechargeRecord::getUserId, param.getUserId())
                .eq(Objects.nonNull(param.getOperatorId()), RechargeRecord::getOperatorId, param.getOperatorId())
                .in(CollUtil.isNotEmpty(param.getUserIds()), RechargeRecord::getUserId, param.getUserIds())
        );
    }
}
