package top.kylewang.bos.dao.take_delivery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import top.kylewang.bos.domain.take_delivery.Promotion;

import java.util.Date;

/**
 * @author Kyle.Wang
 * 2018/1/4 0004 20:58
 */
public interface PromotionRepository extends JpaRepository<Promotion,Integer>{

    /**
     * 根据当前时间修改活动状态
     * @param now
     */
    @Query("update Promotion set status='2' where endDate<?1 and status='1'")
    @Modifying
    void updateStatus(Date now);
}
