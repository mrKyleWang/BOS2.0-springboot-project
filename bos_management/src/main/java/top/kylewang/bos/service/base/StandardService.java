package top.kylewang.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import top.kylewang.bos.domain.base.Standard;

import java.util.List;

/**
 * @author Kyle.Wang
 * 2017/12/29 0029 12:52
 */
public interface StandardService {
    /**
     * 添加收派标准
     * @param standard
     */
    void save(Standard standard);

    /**
     * 分页查询
     * @param pageable
     * @return
     */
    Page<Standard> pageQuery(Pageable pageable);

    /**
     * 查询所有
     * @return
     */
    List<Standard> findAll();

}
