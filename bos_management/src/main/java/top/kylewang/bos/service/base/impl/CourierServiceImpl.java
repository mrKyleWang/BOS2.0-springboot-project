package top.kylewang.bos.service.base.impl;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.kylewang.bos.dao.base.CourierRepository;
import top.kylewang.bos.domain.base.Courier;
import top.kylewang.bos.service.base.CourierService;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Set;

/**
 * @author Kyle.Wang
 * 2017/12/30 0030 10:07
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CourierServiceImpl implements CourierService {

    @Autowired
    private CourierRepository courierRepository;


    @Override
    @RequiresPermissions("courier:add")
    public void save(Courier courier) {
        courierRepository.save(courier);
    }

    @Override
    public Page<Courier> findPageData(Specification<Courier> specification, Pageable pageable) {
        return courierRepository.findAll(specification, pageable);
    }

    @Override
    public void delBatch(String[] idArray) {
        for (String s : idArray) {
            Integer id = Integer.parseInt(s);
            courierRepository.updateDelTag(id);
        }
    }

    @Override
    public List<Courier> findNoAssociation() {
        Specification<Courier> specification = new Specification<Courier>() {
            @Override
            public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.isEmpty(root.get("fixedAreas").as(Set.class));
                return predicate;
            }
        };
        return courierRepository.findAll(specification);
    }
}
