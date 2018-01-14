package top.kylewang.bos.dao.transit;

import org.springframework.data.jpa.repository.JpaRepository;
import top.kylewang.bos.domain.transit.SignInfo;

/**
 * @author Kyle.Wang
 * 2018/1/13 0013 17:10
 */
public interface SignInfoRepository extends JpaRepository<SignInfo,Integer> {
}
