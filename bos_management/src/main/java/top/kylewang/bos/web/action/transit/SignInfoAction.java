package top.kylewang.bos.web.action.transit;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import top.kylewang.bos.domain.transit.SignInfo;
import top.kylewang.bos.service.transit.SignInfoService;
import top.kylewang.bos.web.action.common.BaseAction;

/**
 * @author Kyle.Wang
 * 2018/1/13 0013 17:07
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("json-default")
public class SignInfoAction extends BaseAction<SignInfo>{

    @Autowired
    private SignInfoService signInfoService;

    private String transitInfoId;
    public void setTransitInfoId(String transitInfoId) {
        this.transitInfoId = transitInfoId;
    }

    /**
     * 签收信息保存
     * @return
     */
    @Action(value = "sign_save",
            results = {@Result(name = "success",location = "./pages/transit/transitinfo.html",type = "redirect")})
    public String create(){
        signInfoService.save(transitInfoId,model);
        return SUCCESS;
    }
}
