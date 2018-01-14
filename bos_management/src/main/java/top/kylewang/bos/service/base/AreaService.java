package top.kylewang.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import top.kylewang.bos.domain.base.Area;

import java.util.List;

/**
 * @author Kyle.Wang
 * 2017/12/30 0030 17:03
 */
public interface AreaService {
    /**
     * 批量保存
     * @param list
     */
    void saveBatch(List<Area> list);

    /**
     * 条件分页查询
     * @param specification
     * @param pageable
     * @return
     */
    Page<Area> findPageData(Specification<Area> specification, Pageable pageable);
}
