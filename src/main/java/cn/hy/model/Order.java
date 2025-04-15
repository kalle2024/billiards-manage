package cn.hy.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 球桌表
 *
 * @author hy
 */
@Data
@TableName("bm_table")
public class Order implements Serializable {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 下单用户id
     */
    private Integer userId;

    /**
     * 商品id
     */
    private Integer goodsId;

    /**
     * 原商品名称
     */
    private String originGoodsName;

    /**
     * 支付价格
     */
    private BigDecimal price;

    /**
     * 订单状态(0:待使用、1:已使用、2:已取消)
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
