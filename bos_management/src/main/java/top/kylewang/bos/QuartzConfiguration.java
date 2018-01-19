package top.kylewang.bos;

import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import top.kylewang.bos.quartz.PromotionJob;
import top.kylewang.bos.quartz.WayBillIndexJob;

/**
 * @author Kyle.Wang
 * 2018/1/18 0018 19:48
 */

@Configuration
public class QuartzConfiguration {

    @Bean(name = "promotionJobDetail")
    public MethodInvokingJobDetailFactoryBean promotionJobDetail(PromotionJob promotionJob) {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        // 是否并发执行
        jobDetail.setConcurrent(false);
        // 为需要执行的实体类对应的对象
        jobDetail.setTargetObject(promotionJob);
        // 需要执行的方法
        jobDetail.setTargetMethod("updatePromotionStatus");
        return jobDetail;
    }


    @Bean(name = "promotionTrigger")
    public SimpleTriggerFactoryBean promotionTrigger(JobDetail promotionJobDetail) {
        SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
        trigger.setJobDetail(promotionJobDetail);
        trigger.setStartDelay(0);
        trigger.setRepeatInterval(3600000);
        return trigger;
    }

    @Bean(name = "wayBillIndexJobDetail")
    public MethodInvokingJobDetailFactoryBean wayBillIndexJobDetail(WayBillIndexJob wayBillIndexJob) {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        // 是否并发执行
        jobDetail.setConcurrent(false);
        // 为需要执行的实体类对应的对象
        jobDetail.setTargetObject(wayBillIndexJob);
        // 需要执行的方法
        jobDetail.setTargetMethod("syncIndex");
        return jobDetail;
    }

    @Bean(name = "wayBillIndexTrigger")
    public SimpleTriggerFactoryBean wayBillIndexTrigger(JobDetail wayBillIndexJobDetail) {
        SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
        trigger.setJobDetail(wayBillIndexJobDetail);
        trigger.setStartDelay(0);
        trigger.setRepeatInterval(3600000);
        return trigger;
    }


    @Bean(name = "scheduler")
    public SchedulerFactoryBean schedulerFactory(Trigger promotionTrigger, Trigger wayBillIndexTrigger) {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();  
        // 延时启动，应用启动1秒后
        bean.setStartupDelay(1);  
        // 注册触发器
        bean.setTriggers(promotionTrigger,wayBillIndexTrigger);
        return bean;
    }  
}  