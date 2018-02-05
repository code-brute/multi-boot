package com.example.quartz;

import org.junit.Test;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @Description:
 * @author: mitnick
 * @date: 2018-01-22 下午2:05
 */
public class QuartzTest {

    @Test
    public void quartzDemo() {
        try {
            //创建scheduler
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            //定义一个Trigger
            Trigger trigger = newTrigger().withIdentity("trigger1", "group1") //定义name/group
                    .startAt(minusMinutes(10))//一旦加入scheduler，立即生效
                    .withSchedule(simpleSchedule() //使用SimpleTrigger
                            .withIntervalInSeconds(1) //每隔一秒执行一次
                            .repeatForever()) //一直执行，奔腾到老不停歇
                    .build();

            //定义一个JobDetail
            JobDetail job = newJob(HelloQuartz.class) //定义Job类为HelloQuartz类，这是真正的执行逻辑所在
                    .withIdentity("job1", "group1") //定义name/group
                    .usingJobData("name", "quartz") //定义属性
                    .build();

            //加入这个调度
            scheduler.scheduleJob(job, trigger);

            //启动之
            scheduler.start();

            //运行一段时间后关闭
            Thread.sleep(1000000);
            scheduler.shutdown(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static LocalDateTime dateCovertLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    private static Date localDateTimeCovertDate(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * 现有的时间增加一一一分钟 并转换成 date
     */
    private static Date minusMinutes(int minutes) {
        LocalDateTime time = LocalDateTime.now();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime newTime = time.minusMinutes(minutes);
        Instant instant = newTime.atZone(zone).toInstant();
        return Date.from(instant);
    }
}
