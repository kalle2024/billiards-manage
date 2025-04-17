package cn.hy.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.core.bean.BeanUtil;
import cn.hy.mapper.GoodsMapper;
import cn.hy.mapper.GoodsTypeMapper;
import cn.hy.model.GoodsType;
import cn.hy.param.GoodsTypeCreateParam;
import cn.hy.param.GoodsTypeModifyParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Objects;

/**
 * 商品类型 Controller
 *
 * @author hy
 */
@RestController
@RequestMapping("/api/goods-type")
@SaCheckRole("SUPER_ADMIN")
public class GoodsTypeController {

    @Resource
    private GoodsTypeMapper goodsTypeMapper;

    @Resource
    private GoodsMapper goodsMapper;

    @PostMapping("/create")
    public SaResult create(@RequestBody @Valid GoodsTypeCreateParam param) {
        GoodsType goodsType = goodsTypeMapper.getByType(param.getType());
        if (Objects.nonNull(goodsType)) {
            return SaResult.error("商品类型已存在，请勿宠物创建");
        }
        goodsType = new GoodsType();
        goodsType.setType(param.getType());
        goodsTypeMapper.insert(goodsType);
        return SaResult.ok("新增成功");
    }

    @PostMapping("/modify")
    public SaResult modify(@RequestBody @Valid GoodsTypeModifyParam param) {
        GoodsType goodsType = new GoodsType();
        BeanUtil.copyProperties(param, goodsType);
        goodsTypeMapper.updateById(goodsType);
        return SaResult.ok("修改成功");
    }

    @PostMapping("/delete/{id}")
    public SaResult deleteById(@PathVariable("id") Integer id) {
        long count = goodsMapper.selectCountByGoodsTypeId(id);
        if (count > 0) {
            return SaResult.error("该商品类型下存在商品，无法删除");
        }
        int delete = goodsTypeMapper.deleteById(id);
        if (delete == 0) {
            return SaResult.error("删除失败");
        }
        return SaResult.data("删除成功");
    }
}
