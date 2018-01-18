package top.kylewang.bos.web.controller.take_delivery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.kylewang.bos.domain.take_delivery.Order;
import top.kylewang.bos.service.take_delivery.OrderService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kyle.Wang
 * 2018/1/8 0008 16:04
 */
@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 根据订单号查询订单
     * @param order
     * @return
     */
    @RequestMapping("/order_findByOrderNum.action")
    @ResponseBody
    public Map<String, Object> findByOrderNum(Order order){
        Map<String, Object> result = new HashMap<>(4);
        Order existOrder = orderService.findByOrderNum(order.getOrderNum());
        if(existOrder!=null){
            result.put("success",true);
            result.put("orderData",existOrder);
        }else{
            result.put("success",false);
        }
        return result;
    }
}
