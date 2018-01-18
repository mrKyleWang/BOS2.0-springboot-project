package top.kylewang.bos.service.take_delivery;

import top.kylewang.bos.domain.take_delivery.Order;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * @author Kyle.Wang
 * 2018/1/7 0007 19:42
 */
public interface OrderService {


    /**
     * 保存Order
     * @param order
     */
    @Path("/order")
    @POST
    @Consumes({"application/xml","application/json"})
    void saveOrder(Order order);

    /**
     * 根据订单号查询订单
     * @param orderNum
     * @return
     */
    Order findByOrderNum(String orderNum);
}
