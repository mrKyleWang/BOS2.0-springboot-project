package top.kylewang.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.kylewang.bos.dao.base.AreaRepository;
import top.kylewang.bos.domain.base.Area;
import top.kylewang.bos.service.base.AreaService;

import java.util.List;

/**
 * @author Kyle.Wang
 * 2017/12/30 0030 17:03
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaRepository areaRepository;

    @Override
    public void saveBatch(List<Area> list) {
        areaRepository.save(list);
    }

    @Override
    public Page<Area> findPageData(Specification<Area> specification,Pageable pageable ) {
        return areaRepository.findAll(specification,pageable);
    }
}
