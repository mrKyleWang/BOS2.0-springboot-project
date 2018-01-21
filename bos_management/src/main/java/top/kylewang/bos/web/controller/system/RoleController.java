package top.kylewang.bos.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.kylewang.bos.domain.system.Role;
import top.kylewang.bos.service.system.RoleService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Kyle.Wang
 * 2018/1/10 0010 22:12
 */
@Controller
public class RoleController{

    @Autowired
    private RoleService roleService;


    /**
     * 查询角色列表
     * @return
     */
    @RequestMapping("/role_list.action")
    @ResponseBody
    public List<Role> list(){
        List<Role> list = roleService.findAll();
        return list;
    }

    /**
     * 添加角色
     * @param role
     * @param permissionIds
     * @param menuIds
     * @param response
     * @throws IOException
     */
    @RequestMapping("/role_save.action")
    public void save(Role role, String[] permissionIds, String menuIds, HttpServletResponse response) throws IOException {
        roleService.save(role,permissionIds,menuIds);
        response.sendRedirect("./pages/system/role.html");
    }
}
