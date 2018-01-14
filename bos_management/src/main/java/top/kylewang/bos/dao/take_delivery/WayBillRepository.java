package top.kylewang.bos.dao.take_delivery;

import org.springframework.data.jpa.repository.JpaRepository;
import top.kylewang.bos.domain.take_delivery.WayBill;

/**
 * @author Kyle.Wang
 * 2018/1/8 0008 15:14
 */
public interface WayBillRepository extends JpaRepository<WayBill,Integer> {

    /**
     * 根据运单号查询
     * @param wayBillNum
     * @return
     */
    WayBill findByWayBillNum(String wayBillNum);
}
