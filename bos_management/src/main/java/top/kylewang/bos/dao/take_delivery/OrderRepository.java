package top.kylewang.bos.dao.take_delivery;

import org.springframework.data.jpa.repository.JpaRepository;
import top.kylewang.bos.domain.take_delivery.Order;

/**
 * @author Kyle.Wang
 * 2018/1/7 0007 19:44
 */
public interface OrderRepository extends JpaRepository<Order,Integer> {

    /**
     * 根据订单号查询订单
     * @param orderNum
     * @return
     */
    Order findByOrderNum(String orderNum);
}
