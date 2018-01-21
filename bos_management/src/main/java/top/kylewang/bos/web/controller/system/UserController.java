package top.kylewang.bos.web.controller.system;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.kylewang.bos.domain.system.User;
import top.kylewang.bos.service.system.UserService;
import top.kylewang.bos.utils.CaptchaUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Kyle.Wang
 * 2018/1/10 0010 15:54
 */
@Controller
public class UserController{

    @Autowired
    private UserService userService;

    /**
     * 登录
     * @param user
     * @param response
     * @throws IOException
     */
    @RequestMapping("/user_login.action")
    public void login(User user, HttpServletResponse response) throws IOException {
        System.out.println("登录!!");
        // 基于shiro实现登录
        Subject subject = SecurityUtils.getSubject();
        // 使用用户名和密码登录
        AuthenticationToken token = new UsernamePasswordToken(user.getUsername(),user.getPassword());
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            response.sendRedirect("login.html");
        }
        response.sendRedirect( "index.html");
    }

    /**
     * 用户注销
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/user_logout.action")
    public void logout(HttpServletResponse response) throws IOException {
        System.out.println("注销!!");
        // 基于shiro实现注销
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        response.sendRedirect( "login.html");
    }

    /**
     * 用户列表查询
     * @return
     */
    @RequestMapping("/user_list.action")
    @ResponseBody
    public List<User> list(){
        List<User> list = userService.findAll();
        return list;
    }

    /**
     * 用户保存
     * @param user
     * @param roleIds
     * @param response
     * @throws IOException
     */
    @RequestMapping("/user_save.action")
    public void save(User user, String[] roleIds,HttpServletResponse response) throws IOException {
        userService.save(user,roleIds);
        response.sendRedirect( "./pages/system/userlist.html");
    }


    /**
     * 验证码显示
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "/checkCode.action", method = RequestMethod.GET)
    @ResponseBody
    public void captcha(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        CaptchaUtil.outputCaptcha(request, response);
    }
}
