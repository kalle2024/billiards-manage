package cn.hy.mapper;

import cn.hutool.core.util.StrUtil;
import cn.hy.model.User;
import cn.hy.param.UserPageParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.Objects;

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
                .eq(Objects.nonNull(param.getId()), User::getId, param.getId())
                .like(StrUtil.isNotBlank(param.getUsername()), User::getUsername, param.getUsername())
                .like(StrUtil.isNotBlank(param.getMobile()), User::getMobile, param.getMobile())
                .like(StrUtil.isNotBlank(param.getNickname()), User::getNickname, param.getNickname())
                .eq(Objects.nonNull(param.getUserType()), User::getUserType, param.getUserType())
        );
    }

    @Update("update bm_user set balance = balance + #{param.amount} where id = #{param.userId}")
    void plusBalance(@Param("userId") Integer userId, @Param("price") BigDecimal amount);

    @Update("update bm_user set balance = balance - #{amount} where id = #{userId} and balance > #{amount}")
    int minusBalance(@Param("userId") Integer userId, @Param("price") BigDecimal amount);
}
