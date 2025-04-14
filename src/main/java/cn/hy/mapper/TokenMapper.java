package cn.hy.mapper;

import cn.hy.model.Token;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 令牌 Mapper
 *
 * @author hy
 */
@Mapper
public interface TokenMapper extends BaseMapper<Token> {

    default Token getByLoginId(String loginId) {
        return selectOne(new LambdaQueryWrapper<Token>()
                .eq(Token::getLoginId, loginId)
        );
    }

    @Insert("insert into bm_token(login_id, token, expire_time) value (#{param.loginId}, #{param.token}, #{param.expireTime}) on duplicate key update token = values(token), expire_time = values(expire_time)")
    void saveToken(@Param("param") Token token);

    default void deleteByLoginId(String loginId) {
        delete(new LambdaUpdateWrapper<Token>()
                .eq(Token::getLoginId, loginId)
        );
    }

    @Update("update bm_token set expire_time = #{expireTime} where login_id = #{loginId}")
    void updateExpireTime(@Param("loginId") String loginId, @Param("expireTime") LocalDateTime expireTime);

    default List<String> selectLoginIdsByLoginId(String loginIdPattern) {
        List<Token> tokens = selectList(new LambdaQueryWrapper<Token>()
                .like(Token::getLoginId, loginIdPattern)
        );
        return tokens.stream().map(Token::getLoginId).collect(Collectors.toList());
    }
}
