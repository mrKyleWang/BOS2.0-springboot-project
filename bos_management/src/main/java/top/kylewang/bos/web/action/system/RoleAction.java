package top.kylewang.bos.web.action.system;

import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import top.kylewang.bos.domain.system.Role;
import top.kylewang.bos.service.system.RoleService;
import top.kylewang.bos.web.action.common.BaseAction;

import java.util.List;

/**
 * @author Kyle.Wang
 * 2018/1/10 0010 22:12
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("json-default")
public class RoleAction extends BaseAction<Role> {

    @Autowired
    private RoleService roleService;


    @Action(value = "role_list",results = {@Result(name = "success",type = "json")})
    public String list(){
        List<Role> list = roleService.findAll();
        ActionContext.getContext().getValueStack().push(list);
        return SUCCESS;
    }

    private String[] permissionIds;
    private String menuIds;
    public void setPermissionIds(String[] permissionIds) {
        this.permissionIds = permissionIds;
    }
    public void setMenuIds(String menuIds) {
        this.menuIds = menuIds;
    }

    /**
     * 添加角色
     * @return
     */
    @Action(value = "role_save",
            results = {@Result(name = "success",location = "./pages/system/role.html",type = "redirect")})
    public String save(){
        roleService.save(model,permissionIds,menuIds);
        return SUCCESS;
    }
}
