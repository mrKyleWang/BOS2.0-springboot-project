package top.kylewang.bos.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import top.kylewang.bos.service.take_delivery.WayBillService;

/**
 * @author Kyle.Wang
 * 2018/1/13 0013 10:24
 */
public class WayBillIndexJob implements Job{

    @Autowired
    private WayBillService wayBillService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("同步运单索引库处理程序执行....");
        wayBillService.syncIndex();
    }
}
