package top.kylewang.bos.service.transit;

import top.kylewang.bos.domain.transit.DeliveryInfo; /**
 * @author Kyle.Wang
 * 2018/1/13 0013 17:00
 */
public interface DeliveryInfoService {

    /**
     * 保存配送信息
     * @param transitInfoId
     * @param deliveryInfo
     */
    void save(String transitInfoId, DeliveryInfo deliveryInfo);
}
