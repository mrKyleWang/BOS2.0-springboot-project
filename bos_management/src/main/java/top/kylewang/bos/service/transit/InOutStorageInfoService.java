package top.kylewang.bos.service.transit;

import top.kylewang.bos.domain.transit.InOutStorageInfo; /**
 * @author Kyle.Wang
 * 2018/1/13 0013 16:21
 */
public interface InOutStorageInfoService {

    /**
     * 保存出入库信息
     * @param transitInfoId
     * @param model
     */
    void save(String transitInfoId, InOutStorageInfo model);
}
