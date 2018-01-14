package top.kylewang.bos.service.transit.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.kylewang.bos.dao.transit.SignInfoRepository;
import top.kylewang.bos.dao.transit.TransitRepository;
import top.kylewang.bos.domain.transit.SignInfo;
import top.kylewang.bos.domain.transit.TransitInfo;
import top.kylewang.bos.index.WayBillIndexRepository;
import top.kylewang.bos.service.transit.SignInfoService;

/**
 * @author Kyle.Wang
 * 2018/1/13 0013 17:08
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SignInfoServiceImpl implements SignInfoService {

    @Autowired
    private SignInfoRepository signInfoRepository;

    @Autowired
    private TransitRepository transitRepository;

    @Autowired
    private WayBillIndexRepository wayBillIndexRepository;

    @Override
    public void save(String transitInfoId, SignInfo signInfo) {
        // 保存出入库信息
        signInfoRepository.save(signInfo);
        // 保存运单信息
        TransitInfo transitInfo = transitRepository.findOne(Integer.parseInt(transitInfoId));
        transitInfo.setSignInfo(signInfo);
        // 修改状态
        if(signInfo.getSignType().equals("正常")){
            transitInfo.setStatus("正常签收");
            transitInfo.getWayBill().setSignStatus(3);
            wayBillIndexRepository.save(transitInfo.getWayBill());
        }else{
            transitInfo.setStatus("异常");
            transitInfo.getWayBill().setSignStatus(4);
            wayBillIndexRepository.save(transitInfo.getWayBill());
        }
    }
}
