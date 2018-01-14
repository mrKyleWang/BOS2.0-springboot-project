package top.kylewang.bos.dao.transit;

import org.springframework.data.jpa.repository.JpaRepository;
import top.kylewang.bos.domain.transit.TransitInfo;

/**
 * @author Kyle.Wang
 * 2018/1/13 0013 9:48
 */
public interface TransitRepository extends JpaRepository<TransitInfo,Integer> {
}
