package top.kylewang.bos.service.transit.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.kylewang.bos.dao.transit.DeliveryInfoRepository;
import top.kylewang.bos.dao.transit.TransitRepository;
import top.kylewang.bos.domain.transit.DeliveryInfo;
import top.kylewang.bos.domain.transit.TransitInfo;
import top.kylewang.bos.service.transit.DeliveryInfoService;

/**
 * @author Kyle.Wang
 * 2018/1/13 0013 17:00
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DeliveryInfoServiceImpl implements DeliveryInfoService {

    @Autowired
    private DeliveryInfoRepository deliveryInfoRepository;

    @Autowired
    private TransitRepository transitRepository;


    @Override
    public void save(String transitInfoId, DeliveryInfo deliveryInfo) {
        // 保存出入库信息
        deliveryInfoRepository.save(deliveryInfo);
        // 保存运单信息
        TransitInfo transitInfo = transitRepository.findOne(Integer.parseInt(transitInfoId));
        transitInfo.setDeliveryInfo(deliveryInfo);

        // 修改状态
        transitInfo.setStatus("开始配送");
    }
}
