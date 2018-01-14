package top.kylewang.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import top.kylewang.bos.domain.base.Courier;

/**
 * @author Kyle.Wang
 * 2017/12/30 0030 10:08
 */
public interface CourierRepository extends JpaRepository<Courier, Integer>, JpaSpecificationExecutor<Courier> {


    /**
     * 更新作废标志
     * @param id
     */
    @Query(value = "update Courier set deltag ='1' where id = ?1")
    @Modifying
    void updateDelTag(Integer id);
}
