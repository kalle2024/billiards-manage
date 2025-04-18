package cn.hy.param;

import cn.hy.model.RechargeRecord;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 充值记录分页 参数
 *
 * @author hy
 */
@Setter
@Getter
public class RechargePageParam extends Page<RechargeRecord> {

    /**
     * 充值用户id
     */
    private Integer userId;

    /**
     * 操作人id
     */
    private Integer operatorId;

    /**
     * 充值用户名称
     */
    private String username;

    /**
     * 充值用户ids
     */
    private List<Integer> userIds;
}
