package cn.hy.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 球桌表
 *
 * @author hy
 */
@Data
@TableName("bm_goods_type")
public class Table implements Serializable {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 球桌类型(1:中式八球、2:斯诺克)
     */
    private Integer type;

    /**
     * 球桌名称
     */
    private String name;

    /**
     * 球桌状态(0:未启用、1:已启用、2:营运中)
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
