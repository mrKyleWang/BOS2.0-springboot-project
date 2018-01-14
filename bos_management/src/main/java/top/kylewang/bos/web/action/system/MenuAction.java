package top.kylewang.bos.web.action.system;

import com.opensymphony.xwork2.ActionContext;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import top.kylewang.bos.domain.system.Menu;
import top.kylewang.bos.domain.system.User;
import top.kylewang.bos.service.system.MenuService;
import top.kylewang.bos.web.action.common.BaseAction;

import java.util.List;

/**
 * @author Kyle.Wang
 * 2018/1/10 0010 21:20
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("json-default")
public class MenuAction extends BaseAction<Menu>{

    @Autowired
    private MenuService menuService;


    /**
     * 菜单列表查询
     * @return
     */
    @Action(value = "menu_list",results = {@Result(name = "success",type = "json")})
    public String list(){
        List<Menu> list = menuService.findAll();
        ActionContext.getContext().getValueStack().push(list);
        return SUCCESS;
    }

    /**
     * 菜单添加
     * @return
     */
    @Action(value = "menu_save",
            results = {@Result(name = "success",location = "./pages/system/menu.html",type = "redirect")})
    public String save(){
        menuService.save(model);
        return SUCCESS;
    }

    /**
     * 根据用户显示菜单
     * @return
     */
    @Action(value = "menu_showMenu",
            results = {@Result(name = "success",type = "json")})
    public String showMenu(){
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        List<Menu> menuList = menuService.findByUser(user);
        ActionContext.getContext().getValueStack().push(menuList);
        return SUCCESS;
    }
}
