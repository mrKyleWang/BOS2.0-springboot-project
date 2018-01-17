package top.kylewang.bos.web.controller;

import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import top.kylewang.bos.constants.Constants;
import top.kylewang.bos.domain.base.Area;
import top.kylewang.bos.domain.take_delivery.Order;
import top.kylewang.crm.domain.Customer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * @author Kyle.Wang
 * 2018/1/7 0007 19:29
 */
@Controller
public class OrderController{

    /**
     * 新增订单
     * @param order
     * @param sendAreaInfo
     * @param recAreaInfo
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/order_add.action")
    public void add(Order order, String sendAreaInfo,String recAreaInfo,HttpServletRequest request, HttpServletResponse response) throws IOException {

        // 封装Area对象
        Area sendArea = new Area();
        String[] sendAreaInfoArray = sendAreaInfo.split("/");
        sendArea.setProvince(sendAreaInfoArray[0]);
        sendArea.setCity(sendAreaInfoArray[1]);
        sendArea.setDistrict(sendAreaInfoArray[2]);

        Area recArea = new Area();
        String[] recAreaInfoArray = recAreaInfo.split("/");
        recArea.setProvince(recAreaInfoArray[0]);
        recArea.setCity(recAreaInfoArray[1]);
        recArea.setDistrict(recAreaInfoArray[2]);
        // 关联Area对象
        order.setSendArea(sendArea);
        order.setRecArea(recArea);

        // 关联Customer
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        order.setCustomer_id(customer.getId());

        // 调用WebService保存Order
        WebClient.create(Constants.BOS_MANAGEMENT_URL+"/services/orderService/order")
                .type(MediaType.APPLICATION_JSON).post(order);
        response.sendRedirect("index.html#/express_manage");
    }
}
