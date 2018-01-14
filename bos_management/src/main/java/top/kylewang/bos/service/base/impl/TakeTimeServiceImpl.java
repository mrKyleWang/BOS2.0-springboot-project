package top.kylewang.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.kylewang.bos.dao.base.TakeTimeRepository;
import top.kylewang.bos.domain.base.TakeTime;
import top.kylewang.bos.service.base.TakeTimeService;

import java.util.List;

/**
 * @author Kyle.Wang
 * 2018/1/2 0002 15:26
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TakeTimeServiceImpl implements TakeTimeService {

    @Autowired
    private TakeTimeRepository takeTimeRepository;

    @Override
    public List<TakeTime> findAll() {
        return takeTimeRepository.findAll();
    }
}
