package top.kylewang.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import top.kylewang.bos.domain.take_delivery.WorkBill;

/**
 * @author Kyle.Wang
 * 2018/1/7 0007 22:15
 */
public interface WorkBillRepository extends JpaRepository<WorkBill,Integer> {
}
