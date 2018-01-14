package top.kylewang.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import top.kylewang.bos.domain.base.Courier;

import java.util.List;

/**
 * @author Kyle.Wang
 * 2017/12/30 0030 10:07
 */
public interface CourierService {
    /**
     * 保存快递员
     * @param courier
     */
    void save(Courier courier);

    /**
     * 分页条件查询
     * @param specification
     * @param pageable
     * @return
     */
    Page<Courier> findPageData(Specification<Courier> specification,Pageable pageable);

    /**
     * 批量作废
     * @param idArray
     */
    void delBatch(String[] idArray);

    /**
     * 查询未关联定区快递员
     * @return
     */
    List<Courier> findNoAssociation();

}
