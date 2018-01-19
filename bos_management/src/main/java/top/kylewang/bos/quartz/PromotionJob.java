package top.kylewang.bos.quartz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import top.kylewang.bos.service.take_delivery.PromotionService;

/**
 * 定时设置宣传任务状态
 * @author Kyle.Wang
 * 2018/1/6 0006 20:18
 */
@Component
@EnableScheduling
public class PromotionJob{

    @Autowired
    private PromotionService promotionService;

    public void updatePromotionStatus(){
        // 每分钟执行一次, 当前时间大于 promotion数据表endDate ,活动已经过期, 设置status='2'
        System.out.println("活动过期处理程序执行....");
//        promotionService.updateStatus(new Date());
    }
}
