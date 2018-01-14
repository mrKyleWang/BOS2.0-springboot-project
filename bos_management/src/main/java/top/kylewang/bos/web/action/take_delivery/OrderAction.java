package top.kylewang.bos.web.action.take_delivery;

import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import top.kylewang.bos.domain.take_delivery.Order;
import top.kylewang.bos.service.take_delivery.OrderService;
import top.kylewang.bos.web.action.common.BaseAction;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kyle.Wang
 * 2018/1/8 0008 16:04
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("json-default")
public class OrderAction extends BaseAction<Order>{

    @Autowired
    private OrderService orderService;

    /**
     * 根据订单号查询订单
     * @return
     */
    @Action(value = "order_findByOrderNum",results = {@Result(name = "success",type = "json")})
    public String findByOrderNum(){
        Map<String, Object> result = new HashMap<>(4);
        Order order = orderService.findByOrderNum(model.getOrderNum());
        if(order!=null){
            result.put("success",true);
            result.put("orderData",order);
        }else{
            result.put("success",false);
        }
        ActionContext.getContext().getValueStack().push(result);
        return SUCCESS;
    }
}
