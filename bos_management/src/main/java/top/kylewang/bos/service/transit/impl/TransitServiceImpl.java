package top.kylewang.bos.service.transit.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.kylewang.bos.dao.take_delivery.WayBillRepository;
import top.kylewang.bos.dao.transit.TransitRepository;
import top.kylewang.bos.domain.take_delivery.WayBill;
import top.kylewang.bos.domain.transit.TransitInfo;
import top.kylewang.bos.service.transit.TransitService;

/**
 * @author Kyle.Wang
 * 2018/1/13 0013 9:47
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TransitServiceImpl implements TransitService {

    @Autowired
    private TransitRepository transitRepository;

    @Autowired
    private WayBillRepository wayBillRepository;

    @Override
    public void createTransits(String wayBillIds) {
        if(StringUtils.isNotBlank(wayBillIds)){
            String[] wayBillIdArray = wayBillIds.split(",");
            for (String wayBillId : wayBillIdArray) {
                WayBill wayBill = wayBillRepository.findOne(Integer.parseInt(wayBillId));
                if(wayBill.getSignStatus()==1){
                    // 保存中转配送信息对象
                    TransitInfo transitInfo = new TransitInfo();
                    transitInfo.setWayBill(wayBill);
                    transitInfo.setStatus("出入库中转");
                    transitRepository.save(transitInfo);
                    // 修改运单状态
                    wayBill.setSignStatus(2);
                }else{
                    throw new RuntimeException("运单已经发出,请不要重复操作");
                }
            }
        }
    }

    @Override
    public Page<TransitInfo> findPageData(Pageable pageable) {
        return transitRepository.findAll(pageable);
    }

}
