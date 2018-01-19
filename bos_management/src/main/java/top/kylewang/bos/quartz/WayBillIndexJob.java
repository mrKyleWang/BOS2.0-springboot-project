package top.kylewang.bos.quartz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.kylewang.bos.service.take_delivery.WayBillService;

/**
 * @author Kyle.Wang
 * 2018/1/13 0013 10:24
 */
@Component("wayBillIndexJob")
public class WayBillIndexJob{

    @Autowired
    private WayBillService wayBillService;

    public void syncIndex(){
        System.out.println("同步运单索引库处理程序执行....");
//        wayBillService.syncIndex();
    }
}
