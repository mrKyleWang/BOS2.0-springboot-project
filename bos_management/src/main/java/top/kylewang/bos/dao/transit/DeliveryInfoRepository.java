package top.kylewang.bos.dao.transit;

import org.springframework.data.jpa.repository.JpaRepository;
import top.kylewang.bos.domain.transit.DeliveryInfo;

/**
 * @author Kyle.Wang
 * 2018/1/13 0013 17:01
 */
public interface DeliveryInfoRepository extends JpaRepository<DeliveryInfo,Integer>{
}
