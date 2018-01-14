package top.kylewang.bos.service.transit.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.kylewang.bos.dao.transit.InOutStorageInfoRepository;
import top.kylewang.bos.dao.transit.TransitRepository;
import top.kylewang.bos.domain.transit.InOutStorageInfo;
import top.kylewang.bos.domain.transit.TransitInfo;
import top.kylewang.bos.service.transit.InOutStorageInfoService;

/**
 * @author Kyle.Wang
 * 2018/1/13 0013 16:21
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class InOutStorageInfoServiceImpl implements InOutStorageInfoService {

    @Autowired
    private InOutStorageInfoRepository inOutStorageInfoRepository;

    @Autowired
    private TransitRepository transitRepository;

    @Override
    public void save(String transitInfoId, InOutStorageInfo inOutStorageInfo) {
        // 保存出入库信息
        inOutStorageInfoRepository.save(inOutStorageInfo);
        // 保存运单信息
        TransitInfo transitInfo = transitRepository.findOne(Integer.parseInt(transitInfoId));
        transitInfo.getInOutStorageInfos().add(inOutStorageInfo);

        // 修改状态
        if(inOutStorageInfo.getOperation().equals("到达网点")){
            // 更新网点地址
            transitInfo.setStatus("到达网点");
            transitInfo.setOutletAddress(inOutStorageInfo.getAddress());
        }

    }
}
