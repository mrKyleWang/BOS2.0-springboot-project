package top.kylewang.bos.service.transit;

import top.kylewang.bos.domain.transit.SignInfo; /**
 * @author Kyle.Wang
 * 2018/1/13 0013 17:08
 */
public interface SignInfoService {

    /**
     * 签收信息保存
     * @param transitInfoId
     * @param signInfo
     */
    void save(String transitInfoId, SignInfo signInfo);
}
