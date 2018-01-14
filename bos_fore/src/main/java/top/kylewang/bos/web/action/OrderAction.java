package top.kylewang.bos.web.action;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import top.kylewang.bos.constants.Constants;
import top.kylewang.bos.domain.base.Area;
import top.kylewang.bos.domain.take_delivery.Order;
import top.kylewang.bos.web.action.common.BaseAction;
import top.kylewang.crm.domain.Customer;

import javax.ws.rs.core.MediaType;

/**
 * @author Kyle.Wang
 * 2018/1/7 0007 19:29
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("json-default")
public class OrderAction extends BaseAction<Order> {

    private String sendAreaInfo;
    private String recAreaInfo;

    public void setSendAreaInfo(String sendAreaInfo) {
        this.sendAreaInfo = sendAreaInfo;
    }
    public void setRecAreaInfo(String recAreaInfo) {
        this.recAreaInfo = recAreaInfo;
    }

    /**
     * 新增订单
     * @return
     */
    @Action(value = "order_add",
            results = {@Result(name = "success",location = "index.html#/express_manage",type = "redirect")})
    public String add(){

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
        model.setSendArea(sendArea);
        model.setRecArea(recArea);

        // 关联Customer
        Customer customer = (Customer) ServletActionContext.getRequest().getSession().getAttribute("customer");
        model.setCustomer_id(customer.getId());

        // 调用WebService保存Order
        WebClient.create(Constants.BOS_MANAGEMENT_URL+"/services/orderService/order")
                .type(MediaType.APPLICATION_JSON).post(model);

        return SUCCESS;
    }
}
