package cn.hy.scheduler;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 球桌自动关闭 定时任务
 *
 * @author hy
 */
@Component
public class TableAutoCloseScheduler {

    @Resource
    private JdbcTemplate jdbcTemplate;

    /**
     * 每分钟更新需要关台的球桌信息，将已到期且还在运营中的球桌信息设置为已启用
     */
    @Scheduled(fixedDelay = 1L, timeUnit = TimeUnit.MINUTES)
    public void execute() {
        jdbcTemplate.update("update bm_table t1 " +
                "inner join bm_table_open_record t2 " +
                "on t1.id = t2.table_id " +
                "set t1.status = 1 " +
                "where t1.status = 2 " +
                "and t2.expire_time < now()");
    }
}
