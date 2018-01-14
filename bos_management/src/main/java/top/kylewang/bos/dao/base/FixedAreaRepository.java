package top.kylewang.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import top.kylewang.bos.domain.base.FixedArea;

/**
 * @author Kyle.Wang
 * 2017/12/30 0030 21:59
 */
public interface FixedAreaRepository extends JpaRepository<FixedArea,String>,JpaSpecificationExecutor<FixedArea>{
}
