package top.kylewang.bos.dao.transit;

import org.springframework.data.jpa.repository.JpaRepository;
import top.kylewang.bos.domain.transit.InOutStorageInfo;

/**
 * @author Kyle.Wang
 * 2018/1/13 0013 16:26
 */
public interface InOutStorageInfoRepository extends JpaRepository<InOutStorageInfo,Integer> {
}
