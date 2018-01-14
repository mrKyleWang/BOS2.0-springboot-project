import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.kylewang.bos.dao.take_delivery.WayBillRepository;
import top.kylewang.bos.domain.take_delivery.WayBill;
import top.kylewang.bos.service.take_delivery.WayBillService;

import java.util.List;

/**
 * @author Kyle.Wang
 * 2018/1/14 0014 10:17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class WayBillPageQueryTest {

    @Autowired
    private WayBillRepository wayBillRepository;

    @Autowired
    private WayBillService wayBillService;

    @Test
    public void test1(){
        Pageable pageable = new PageRequest(0,10);
        Page<WayBill> page = wayBillRepository.findAll(pageable);
        List<WayBill> all = page.getContent();
        System.out.println(all);
        System.out.println(page.getTotalElements());
    }

    @Test
    public void test2(){
        Pageable pageable = new PageRequest(0,10);
        WayBill wayBill = new WayBill();
        wayBill.setWayBillNum("");
        wayBill.setSendAddress("");
        wayBill.setRecAddress("");
        wayBill.setSendProNum("");
        wayBill.setSignStatus(0);
        Page<WayBill> page = wayBillService.findPageData(wayBill,pageable);
        List<WayBill> all = page.getContent();
        System.out.println(all);
        System.out.println(page.getTotalElements());
    }

    @Test
    public void test3(){
        Pageable pageable = new PageRequest(0, 10,new Sort(new Sort.Order(Sort.Direction.DESC,"id")));
        WayBill wayBill = new WayBill();
        wayBill.setWayBillNum("");
        wayBill.setSendAddress("");
        wayBill.setRecAddress("");
        wayBill.setSendProNum("");
        wayBill.setSignStatus(0);
        Page<WayBill> page = wayBillService.findPageData(wayBill,pageable);
        List<WayBill> all = page.getContent();
        System.out.println(all);
        System.out.println(page.getTotalElements());
    }
}
