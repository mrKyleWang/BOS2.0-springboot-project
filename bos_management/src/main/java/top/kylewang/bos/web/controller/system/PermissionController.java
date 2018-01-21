package top.kylewang.bos.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.kylewang.bos.domain.system.Permission;
import top.kylewang.bos.service.system.PermissionService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Kyle.Wang
 * 2018/1/10 0010 19:56
 */
@Controller
public class PermissionController{

    @Autowired
    private PermissionService permissionService;

    /**
     * 权限列表查询
     * @return
     */
    @RequestMapping("/permission_list.action")
    @ResponseBody
    public List<Permission> pageQuery(){
        List<Permission> list = permissionService.findAll();
        return list;
    }

    /**
     * 新增权限
     * @param permission
     * @param response
     * @throws IOException
     */
    @RequestMapping("/permission_save.action")
    public void save(Permission permission, HttpServletResponse response) throws IOException {
        permissionService.save(permission);
        response.sendRedirect("./pages/system/permission.html");
    }


}
