package top.kylewang.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import top.kylewang.bos.domain.base.TakeTime;

/**
 * @author Kyle.Wang
 * 2018/1/2 0002 15:26
 */
public interface TakeTimeRepository extends JpaRepository<TakeTime,Integer>{
}
