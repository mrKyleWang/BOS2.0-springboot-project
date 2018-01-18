package top.kylewang.bos.service.transit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import top.kylewang.bos.domain.transit.TransitInfo;

/**
 * @author Kyle.Wang
 * 2018/1/13 0013 9:47
 */
public interface TransitService {

    /**
     * 对运单开启中转配送
     * @param wayBillIds
     */
    void createTransits(String wayBillIds);

    /**
     * 分页查询
     * @return
     * @param pageable
     */
    Page<TransitInfo> findPageData(Pageable pageable);

}
