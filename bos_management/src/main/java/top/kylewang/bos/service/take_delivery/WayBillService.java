package top.kylewang.bos.service.take_delivery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import top.kylewang.bos.domain.take_delivery.WayBill;

import java.util.List;

/**
 * @author Kyle.Wang
 * 2018/1/8 0008 15:13
 */
public interface WayBillService {

    /**
     * 保存运单
     * @param model
     */
    void save(WayBill model);

    /**
     * 分页条件查询
     *
     * @param wayBill
     * @param pageable
     * @return
     */
    Page<WayBill> findPageData(WayBill wayBill, Pageable pageable);

    /**
     * 根据运单号查询
     * @param wayBillNum
     * @return
     */
    WayBill findByWayBillNum(String wayBillNum);


    /**
     * 同步索引库
     */
    void syncIndex();

    /**
     * 条件查询
     * @param model
     * @return
     */
    List<WayBill> findWayBills(WayBill model);
}
