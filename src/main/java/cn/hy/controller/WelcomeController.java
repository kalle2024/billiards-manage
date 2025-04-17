package cn.hy.controller;

import cn.dev33.satoken.util.SaResult;
import cn.hy.mapper.GoodsMapper;
import cn.hy.mapper.OrderMapper;
import cn.hy.mapper.TableMapper;
import cn.hy.mapper.UserMapper;
import cn.hy.model.Goods;
import cn.hy.model.Order;
import cn.hy.model.Table;
import cn.hy.model.User;
import cn.hy.util.UserUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/welcome")
public class WelcomeController {

    @Resource
    private UserMapper userMapper;

    @Resource
    private TableMapper tableMapper;

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private OrderMapper orderMapper;

    @GetMapping("/welcome-stat")
    public SaResult welcomeStat() {
        Integer loginUserId = UserUtil.getLoginUserId();
        User currentUser = userMapper.selectById(loginUserId);
        long totalUserCount = userMapper.selectCount(null);
        long totalTableCount = tableMapper.selectCount(null);
        long totalGoodsCount = goodsMapper.selectCount(null);
        long totalOrderCount = orderMapper.selectCount(null);
        // 本月
        LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
        long increasingUserCount = userMapper.selectCount(new LambdaQueryWrapper<User>()
                .gt(User::getCreateTime, startOfMonth)
        );
        long increasingTableCount = tableMapper.selectCount(new LambdaQueryWrapper<Table>()
                .gt(Table::getCreateTime, startOfMonth)
        );
        long increasingGoodsCount = goodsMapper.selectCount(new LambdaQueryWrapper<Goods>()
                .gt(Goods::getCreateTime, startOfMonth)
        );
        long increasingOrderCount = orderMapper.selectCount(new LambdaQueryWrapper<Order>()
                .gt(Order::getCreateTime, startOfMonth)
        );

        Map<String, Object> welcomeStatMap = new HashMap<>();
        welcomeStatMap.put("currentUser", currentUser);
        welcomeStatMap.put("totalUserCount", totalUserCount);
        welcomeStatMap.put("totalTableCount", totalTableCount);
        welcomeStatMap.put("totalGoodsCount", totalGoodsCount);
        welcomeStatMap.put("totalOrderCount", totalOrderCount);
        welcomeStatMap.put("increasingUserCount", increasingUserCount);
        welcomeStatMap.put("increasingTableCount", increasingTableCount);
        welcomeStatMap.put("increasingGoodsCount", increasingGoodsCount);
        welcomeStatMap.put("increasingOrderCount", increasingOrderCount);
        return SaResult.data(welcomeStatMap);
    }
}
