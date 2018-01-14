package top.kylewang.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.kylewang.bos.dao.base.StandardRepository;
import top.kylewang.bos.domain.base.Standard;
import top.kylewang.bos.service.base.StandardService;

import java.util.List;

/**
 * @author Kyle.Wang
 * 2017/12/29 0029 12:52
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class StandardServiceImpl implements StandardService{

    @Autowired
    private StandardRepository standardRepository;

    @Override
    @CacheEvict(value = "standard",allEntries = true)
    public void save(Standard standard) {
        standardRepository.save(standard);
    }

    @Override
    @Cacheable(value = "standard",key = "pageable.pageNumber+'_'+pageable.pageSize")
    public Page<Standard> pageQuery(Pageable pageable) {
        return standardRepository.findAll(pageable);
    }

    @Override
    @Cacheable(value = "standard")
    public List<Standard> findAll() {
        return standardRepository.findAll();
    }
}
