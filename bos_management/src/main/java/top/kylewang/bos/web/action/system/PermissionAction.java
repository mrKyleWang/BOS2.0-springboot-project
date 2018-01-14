package top.kylewang.bos.web.action.system;

import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import top.kylewang.bos.domain.system.Permission;
import top.kylewang.bos.service.system.PermissionService;
import top.kylewang.bos.web.action.common.BaseAction;

import java.util.List;

/**
 * @author Kyle.Wang
 * 2018/1/10 0010 19:56
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("json-default")
public class PermissionAction extends BaseAction<Permission>{

    @Autowired
    private PermissionService permissionService;

    @Action(value = "permission_list",
            results = {@Result(name = "success",type = "json")})
    public String pageQuery(){
        List<Permission> list = permissionService.findAll();
        ActionContext.getContext().getValueStack().push(list);
        return SUCCESS;
    }

    @Action(value = "permission_save",
            results = {@Result(name = "success",location = "./pages/system/permission.html",type = "redirect")})
    public String save(){
        permissionService.save(model);
        return SUCCESS;
    }


}
