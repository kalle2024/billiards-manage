package cn.hy.satoken;

import cn.dev33.satoken.dao.SaTokenDao;
import cn.dev33.satoken.dao.auto.SaTokenDaoByObjectFollowString;
import cn.dev33.satoken.util.SaFoxUtil;
import cn.hy.mapper.TokenMapper;
import cn.hy.model.Token;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

/**
 * Token存到数据库
 *
 * @author hy
 */
@Component
public class SaTokenDaoForMySqlTemplate implements SaTokenDaoByObjectFollowString {

    @Resource
    private TokenMapper tokenMapper;

    @Override
    public String get(String key) {
        Token token = tokenMapper.getByLoginId(key);
        if (Objects.nonNull(token)) {
            LocalDateTime now = LocalDateTime.now();
            if (Objects.isNull(token.getExpireTime()) || !now.isAfter(token.getExpireTime())) {
                return token.getToken();
            }
        }
        return null;
    }

    @Override
    public void set(String key, String value, long timeout) {
        if (timeout == 0 || timeout <= SaTokenDao.NOT_VALUE_EXPIRE) {
            return;
        }
        // 判断是否为永不过期
        Token token = new Token();
        token.setLoginId(key);
        token.setToken(value);
        if (timeout != SaTokenDao.NEVER_EXPIRE) {
            token.setExpireTime(LocalDateTime.now().plusSeconds(timeout));
        }
        tokenMapper.saveToken(token);
    }

    @Override
    public void update(String key, String value) {
        long expire = getTimeout(key);
        // -2 = 无此键
        if (expire == SaTokenDao.NOT_VALUE_EXPIRE) {
            return;
        }
        this.set(key, value, expire);
    }

    @Override
    public void delete(String key) {
        tokenMapper.deleteByLoginId(key);
    }

    @Override
    public long getTimeout(String key) {
        Token token = tokenMapper.getByLoginId(key);
        if (Objects.nonNull(token)) {
            if (Objects.isNull(token.getExpireTime())) {
                return -1;
            }
            LocalDateTime now = LocalDateTime.now();
            if (token.getExpireTime().isAfter(now)) {
                return ChronoUnit.SECONDS.between(token.getExpireTime(), now);
            }
        }
        return 0;
    }

    @Override
    public void updateTimeout(String key, long timeout) {
        // 判断是否想要设置为永久
        if (timeout == SaTokenDao.NEVER_EXPIRE) {
            long expire = getTimeout(key);
            if (expire != SaTokenDao.NEVER_EXPIRE) {
                // 如果尚未被设置为永久，那么再次set一次
                this.set(key, this.get(key), timeout);
            }
            return;
        }
        tokenMapper.updateExpireTime(key, LocalDateTime.now().plusSeconds(timeout));
    }

    @Override
    public List<String> searchData(String prefix, String keyword, int start, int size, boolean sortType) {
        List<String> loginIds = tokenMapper.selectLoginIdsByLoginId(keyword);
        return SaFoxUtil.searchList(loginIds, start, size, sortType);
    }
}
