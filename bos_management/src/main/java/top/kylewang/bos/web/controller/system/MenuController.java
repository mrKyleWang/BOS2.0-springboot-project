package top.kylewang.bos.web.controller.system;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.kylewang.bos.domain.system.Menu;
import top.kylewang.bos.domain.system.User;
import top.kylewang.bos.service.system.MenuService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Kyle.Wang
 * 2018/1/10 0010 21:20
 */
@Controller
public class MenuController {

    @Autowired
    private MenuService menuService;


    /**
     * 菜单列表查询
     * @return
     */
    @RequestMapping("/menu_list.action")
    @ResponseBody
    public List<Menu> list(){
        List<Menu> list = menuService.findAll();
        return list;
    }

    /**
     * 菜单添加
     * @return
     */
    @RequestMapping("/menu_save.action")
    public void save(Menu menu,HttpServletResponse response) throws IOException {
        menuService.save(menu);
        response.sendRedirect("./pages/system/menu.html");
    }

    /**
     * 根据用户显示菜单
     * @return
     */
    @RequestMapping("/menu_showMenu.action")
    @ResponseBody
    public List<Menu> showMenu(){
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        List<Menu> menuList = menuService.findByUser(user);
        return menuList;
    }
}
