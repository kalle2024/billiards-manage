package cn.hy.param;

import cn.hy.model.Table;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Getter;
import lombok.Setter;

/**
 * 球桌分页 参数
 *
 * @author hy
 */
@Setter
@Getter
public class TablePageParam extends Page<Table> {

    /**
     * 球桌类型(1:中式八球、2:斯诺克)
     */
    private Integer type;

    /**
     * 球桌名称
     */
    private String name;
}
