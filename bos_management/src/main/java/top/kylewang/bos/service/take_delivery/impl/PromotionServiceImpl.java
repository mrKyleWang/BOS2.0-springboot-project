package top.kylewang.bos.service.take_delivery.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.kylewang.bos.dao.take_delivery.PromotionRepository;
import top.kylewang.bos.domain.page.PageBean;
import top.kylewang.bos.domain.take_delivery.Promotion;
import top.kylewang.bos.service.take_delivery.PromotionService;

import java.util.Date;

/**
 * @author Kyle.Wang
 * 2018/1/4 0004 20:59
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    @Override
    public void save(Promotion model) {
        promotionRepository.save(model);
    }

    @Override
    public Page<Promotion> findPageData(Pageable pageable) {
        return promotionRepository.findAll(pageable);
    }

    @Override
    public PageBean<Promotion> findPageData(Integer page, Integer rows) {
        Pageable pageable = new PageRequest(page-1,rows);
        Page<Promotion> pageData = promotionRepository.findAll(pageable);
        // 封装pageBean
        PageBean<Promotion> promotionPageBean = new PageBean<>();
        promotionPageBean.setTotalCount(pageData.getTotalElements());
        promotionPageBean.setPageData(pageData.getContent());
        return promotionPageBean;
    }

    @Override
    public Promotion findById(Integer id) {
        return promotionRepository.findOne(id);
    }

    @Override
    public void updateStatus(Date now) {
        promotionRepository.updateStatus(now);
    }
}
