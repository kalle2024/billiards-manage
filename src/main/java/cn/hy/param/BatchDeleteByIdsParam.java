package cn.hy.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 批量删除 ids 参数
 *
 * @author hy
 */
@Setter
@Getter
public class BatchDeleteByIdsParam {

    /**
     * ids
     */
    @NotNull(message = "ids不能为空")
    private List<Integer> ids;
}
