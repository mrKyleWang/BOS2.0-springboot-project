package top.kylewang.bos.web.action.transit;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import top.kylewang.bos.domain.transit.DeliveryInfo;
import top.kylewang.bos.service.transit.DeliveryInfoService;
import top.kylewang.bos.web.action.common.BaseAction;

/**
 * @author Kyle.Wang
 * 2018/1/13 0013 17:01
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("json-default")
public class DeliveryInfoAction extends BaseAction<DeliveryInfo>{

    @Autowired
    private DeliveryInfoService deliveryInfoService;

    private String transitInfoId;
    public void setTransitInfoId(String transitInfoId) {
        this.transitInfoId = transitInfoId;
    }

    /**
     * 运单配送信息保存
     * @return
     */
    @Action(value = "delivery_save",
            results = {@Result(name = "success",location = "./pages/transit/transitinfo.html",type = "redirect")})
    public String create(){
        deliveryInfoService.save(transitInfoId,model);
        return SUCCESS;
    }
}
