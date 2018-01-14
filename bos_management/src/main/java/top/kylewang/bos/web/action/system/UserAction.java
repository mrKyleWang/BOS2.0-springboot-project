package top.kylewang.bos.web.action.system;

import com.opensymphony.xwork2.ActionContext;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import top.kylewang.bos.domain.system.User;
import top.kylewang.bos.service.system.UserService;
import top.kylewang.bos.web.action.common.BaseAction;

import java.util.List;

/**
 * @author Kyle.Wang
 * 2018/1/10 0010 15:54
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("json-default")
public class UserAction extends BaseAction<User>{

    @Autowired
    private UserService userService;

    /**
     * 登录
     * @return
     */
    @Action(value = "user_login",
            results = {@Result(name = "login",location = "login.html",type = "redirect"),
                    @Result(name = "success",location = "index.html",type = "redirect")})
    public String login(){
        System.out.println("登录!!");
        // 基于shiro实现登录
        Subject subject = SecurityUtils.getSubject();
        // 使用用户名和密码登录
        AuthenticationToken token = new UsernamePasswordToken(model.getUsername(),model.getPassword());
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return LOGIN;
        }
        return SUCCESS;
    }

    /**
     * 用户注销
     * @return
     */
    @Action(value = "user_logout",
            results = {@Result(name = "success",location = "login.html",type = "redirect")})
    public String logout(){
        System.out.println("注销!!");
        // 基于shiro实现注销
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return SUCCESS;
    }

    /**
     * 用户列表查询
     * @return
     */
    @Action(value = "user_list",
            results = {@Result(name = "success",type = "json")})
    public String list(){
        List<User> list = userService.findAll();
        ActionContext.getContext().getValueStack().push(list);
        return SUCCESS;
    }

    private String[] roleIds;
    public void setRoleIds(String[] roleIds) {
        this.roleIds = roleIds;
    }

    /**
     * 用户保存
     * @return
     */
    @Action(value = "user_save",
            results = {@Result(name = "success",location = "./pages/system/userlist.html",type = "redirect")})
    public String save(){
        userService.save(model,roleIds);
        return SUCCESS;
    }
}
