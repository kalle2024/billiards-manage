package cn.hy.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.core.bean.BeanUtil;
import cn.hy.mapper.GoodsMapper;
import cn.hy.model.Goods;
import cn.hy.param.GoodsCreateParam;
import cn.hy.param.GoodsModifyParam;
import cn.hy.param.GoodsPageParam;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 商品 Controller
 *
 * @author hy
 */
@RestController
@RequestMapping("/goods")
@SaCheckRole("SUPER_ADMIN")
public class GoodsController {

    @Resource
    private GoodsMapper goodsMapper;

    @PostMapping("/create")
    public SaResult create(@RequestBody @Valid GoodsCreateParam param) {
        Goods goods = new Goods();
        BeanUtil.copyProperties(param, goods);
        goodsMapper.insert(goods);
        return SaResult.ok("创建成功");
    }

    @PostMapping("/modify")
    public SaResult modify(@RequestBody @Valid GoodsModifyParam param) {
        Goods goods = new Goods();
        BeanUtil.copyProperties(param, goods);
        goodsMapper.updateById(goods);
        return SaResult.ok("修改成功");
    }

    @PostMapping("/delete/{id}")
    public SaResult deleteById(@PathVariable Integer id) {
        int delete = goodsMapper.deleteById(id);
        if (delete == 0) {
            return SaResult.error("删除失败");
        }
        return SaResult.ok("删除成功");
    }

    @PostMapping("/page")
    public SaResult page(@RequestBody @Valid GoodsPageParam param) {
        Page<Goods> goodsPage = goodsMapper.selectPage(param);
        return SaResult.data(goodsPage);
    }

    @GetMapping("/get/{id}")
    public SaResult getById(@PathVariable("id") Integer id) {
        Goods goods = goodsMapper.selectById(id);
        return SaResult.data(goods);
    }
}
