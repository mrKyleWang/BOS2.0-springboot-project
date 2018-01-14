package top.kylewang.bos.web.action.base;

import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import top.kylewang.bos.domain.base.Standard;
import top.kylewang.bos.service.base.StandardService;
import top.kylewang.bos.web.action.common.BaseAction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kyle.Wang
 * 2017/12/29 0029 11:46
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("json-default")
public class StandardAction extends BaseAction<Standard>{

    @Autowired
    private StandardService standardService;

    /**
     * 保存收派标准
     * @return
     */
    @Action(value = "standard_save",
            results = {@Result(name = "success",location = "./pages/base/standard.html", type = "redirect")})
    public String sava(){
        standardService.save(model);
        return SUCCESS;
    }

    /**
     * 分页查询
     * @return
     */
    @Action(value = "standard_pageQuery",
            results = {@Result(name = "success",type = "json")})
    public String pageQuery(){
        //封装参数(注意,PageRequest的page从0开始,而页面从1开始)
        Pageable pageable= new PageRequest(page - 1, rows);
        Page<Standard> pageData = standardService.pageQuery(pageable);
        Map<String,Object> result = new HashMap<String,Object>(4);
        //返回页面所需数据格式
        result.put("total",pageData.getNumberOfElements());
        result.put("rows",pageData.getContent());
        //压入栈顶,通过json-plugin返回json数据
        ActionContext.getContext().getValueStack().push(result);
        return SUCCESS;
    }

    /**
     * 查询所有
     * @return
     */
    @Action(value = "standard_findAll",
            results = {@Result(name = "success",type = "json")})
    public String findAll(){
        List<Standard> list = standardService.findAll();
        //压入栈顶,通过json-plugin返回json数据
        ActionContext.getContext().getValueStack().push(list);
        return SUCCESS;
    }

}
