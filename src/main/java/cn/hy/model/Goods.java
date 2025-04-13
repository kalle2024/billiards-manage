package cn.hy.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品表
 *
 * @author hy
 */
@Data
@TableName("bm_goods")
public class Goods {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 商品类型id
     */
    private Integer goodsTypeId;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
