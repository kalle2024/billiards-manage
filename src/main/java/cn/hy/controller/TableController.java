package cn.hy.controller;

import cn.dev33.satoken.util.SaResult;
import cn.hy.mapper.TableMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 球桌 Controller
 *
 * @author hy
 */
@RestController
@RequestMapping("/table")
public class TableController {

    @Resource
    private TableMapper tableMapper;

    @PostMapping("/create")
    public SaResult create(@RequestBody @Valid Object param) {
        return SaResult.ok("创建成功");
    }

    @PostMapping("/modify")
    public SaResult modify(@RequestBody @Valid Object param) {
        return SaResult.ok("修改成功");
    }

    @PostMapping("/delete/{id}")
    public SaResult deleteById(@PathVariable("id") Integer id) {
        return SaResult.ok("删除成功");
    }

    @PostMapping("/page")
    public SaResult page(@RequestBody @Valid Object param) {
        return SaResult.data(null);
    }

    @PostMapping("/get/{id}")
    public SaResult getById(@PathVariable("id") Integer id) {
        return SaResult.data(null);
    }
}
