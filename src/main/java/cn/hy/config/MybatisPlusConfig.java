package cn.hy.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MybatisPlus配置
 *
 * @author hy
 */
@Configuration
@MapperScan(basePackages = "cn.hy.mapper")
public class MybatisPlusConfig {
}
