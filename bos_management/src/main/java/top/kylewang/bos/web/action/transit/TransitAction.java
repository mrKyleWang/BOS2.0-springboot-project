package top.kylewang.bos.web.action.transit;

import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import top.kylewang.bos.domain.transit.TransitInfo;
import top.kylewang.bos.service.transit.TransitService;
import top.kylewang.bos.web.action.common.BaseAction;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kyle.Wang
 * 2018/1/13 0013 9:45
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("json-default")
public class TransitAction extends BaseAction<TransitInfo>{

    @Autowired
    private TransitService transitService;

    private String wayBillIds;
    public void setWayBillIds(String wayBillIds) {
        this.wayBillIds = wayBillIds;
    }

    /**
     * 对运单开启中转配送
     * @return
     */
    @Action(value = "transit_create",results = {@Result(name = "success",type = "json")})
    public String create(){
        Map<String, Object> result = new HashMap<>(4);
        try {
            transitService.createTransits(wayBillIds);
            result.put("success",true);
            result.put("msg","开启中转配送成功!");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success",false);
            result.put("msg","开启中转配送失败!异常:"+e.getMessage());
        }
        ActionContext.getContext().getValueStack().push(result);
        return SUCCESS;
    }

    /**
     * 分页查询
     * @return
     */
    @Action(value = "transit_pageQuery", results = {@Result(name = "success",type = "json")})
    public String pageQuery(){
        Pageable pageable = new PageRequest(page - 1, rows);
        Page<TransitInfo> pageData = transitService.findPageData(pageable);
        pushPageDataToValueStack(pageData);
        return SUCCESS;
    }
}
