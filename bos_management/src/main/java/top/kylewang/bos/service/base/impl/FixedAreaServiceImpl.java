package top.kylewang.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.kylewang.bos.dao.base.CourierRepository;
import top.kylewang.bos.dao.base.FixedAreaRepository;
import top.kylewang.bos.dao.base.TakeTimeRepository;
import top.kylewang.bos.domain.base.Courier;
import top.kylewang.bos.domain.base.FixedArea;
import top.kylewang.bos.domain.base.TakeTime;
import top.kylewang.bos.service.base.FixedAreaService;

/**
 * @author Kyle.Wang
 * 2017/12/30 0030 21:59
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FixedAreaServiceImpl implements FixedAreaService {

    @Autowired
    private FixedAreaRepository fixedAreaRepository;

    @Autowired
    private CourierRepository courierRepository;

    @Autowired
    private TakeTimeRepository takeTimeRepository;

    @Override
    public void save(FixedArea fixedArea) {
        fixedAreaRepository.save(fixedArea);
    }

    @Override
    public Page<FixedArea> findPageData(Specification<FixedArea> specification, Pageable pageable) {
        return fixedAreaRepository.findAll(specification,pageable);
    }

    @Override
    public void associationCourierToFixedArea(FixedArea model, Integer courierId, Integer takeTimeId) {
        FixedArea fixedArea = fixedAreaRepository.findOne(model.getId());
        Courier courier = courierRepository.findOne(courierId);
        TakeTime takeTime = takeTimeRepository.findOne(takeTimeId);
        //关联快递员到定区
        fixedArea.getCouriers().add(courier);
        //关联收派时间到快递员
        courier.setTakeTime(takeTime);
    }
}
