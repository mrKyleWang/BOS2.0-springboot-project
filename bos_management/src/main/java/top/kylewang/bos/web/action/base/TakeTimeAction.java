package top.kylewang.bos.web.action.base;

import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import top.kylewang.bos.domain.base.TakeTime;
import top.kylewang.bos.service.base.TakeTimeService;

import java.util.List;

import static com.opensymphony.xwork2.Action.SUCCESS;

/**
 * @author Kyle.Wang
 * 2018/1/2 0002 15:22
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("json-default")
public class TakeTimeAction {

    @Autowired
    private TakeTimeService takeTimeService;

    /**
     * 查询收派时间列表
     * @return
     */
    @Action(value = "takeTime_findAll",
            results = {@Result(name = "success",type = "json")})
    public String findAll(){
        List<TakeTime> takeTimes =  takeTimeService.findAll();
        ActionContext.getContext().getValueStack().push(takeTimes);
        return SUCCESS;
    }
}
