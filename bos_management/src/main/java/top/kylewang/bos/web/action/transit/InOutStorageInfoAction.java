package top.kylewang.bos.web.action.transit;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import top.kylewang.bos.domain.transit.InOutStorageInfo;
import top.kylewang.bos.service.transit.InOutStorageInfoService;
import top.kylewang.bos.web.action.common.BaseAction;

/**
 * @author Kyle.Wang
 * 2018/1/13 0013 16:20
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("json-default")
public class InOutStorageInfoAction extends BaseAction<InOutStorageInfo>{

    @Autowired
    private InOutStorageInfoService inOutStorageInfoService;

    private String transitInfoId;
    public void setTransitInfoId(String transitInfoId) {
        this.transitInfoId = transitInfoId;
    }

    /**
     * 对运单开启中转配送
     * @return
     */
    @Action(value = "inoutstore_save",
            results = {@Result(name = "success",location = "./pages/transit/transitinfo.html",type = "redirect")})
    public String create(){
        inOutStorageInfoService.save(transitInfoId,model);
        return SUCCESS;
    }
}
