package cn.hy.mapper;

import cn.hy.model.User;
import cn.hy.param.RechargeParam;
import cn.hy.param.UserPageParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 用户 Mapper
 *
 * @author hy
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    default User getByUsername(String username) {
        return selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username)
        );
    }

    default Page<User> selectPage(UserPageParam param) {
        return selectPage(param, new LambdaQueryWrapper<User>()
                .eq(User::getId, param.getId())
                .like(User::getUsername, param.getUsername())
                .like(User::getMobile, param.getMobile())
                .like(User::getNickname, param.getNickname())
                .eq(User::getUserType, param.getUserType())
        );
    }

    @Update("update bm_user set balance = balance + #{param.amount} where id = #{param.userId}")
    void recharge(@Param("param") RechargeParam param);
}
