package top.kylewang.bos.web.controller.transit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import top.kylewang.bos.domain.transit.DeliveryInfo;
import top.kylewang.bos.service.transit.DeliveryInfoService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Kyle.Wang
 * 2018/1/13 0013 17:01
 */
@Controller
public class DeliveryInfoController {

    @Autowired
    private DeliveryInfoService deliveryInfoService;

    /**
     * 开始派送
     * @param deliveryInfo
     * @param transitInfoId
     * @param response
     * @throws IOException
     */
    @RequestMapping("delivery_save.action")
    public void create(DeliveryInfo deliveryInfo,String transitInfoId, HttpServletResponse response) throws IOException {
        deliveryInfoService.save(transitInfoId,deliveryInfo);
        response.sendRedirect("./pages/transit/transitinfo.html");
    }
}
