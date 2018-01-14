package top.kylewang.bos.service.system.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.kylewang.bos.dao.system.MenuRepository;
import top.kylewang.bos.dao.system.PermissionRepository;
import top.kylewang.bos.dao.system.RoleRepository;
import top.kylewang.bos.domain.system.Menu;
import top.kylewang.bos.domain.system.Permission;
import top.kylewang.bos.domain.system.Role;
import top.kylewang.bos.domain.system.User;
import top.kylewang.bos.service.system.RoleService;

import java.util.List;

/**
 * @author Kyle.Wang
 * 2018/1/10 0010 18:02
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Override
    public List<Role> findByUser(User user) {
        // 判断:如果是管理员则获得所有角色
        if("admin".equals(user.getUsername())){
            return roleRepository.findAll();
        }else{
            return roleRepository.findByUser(user.getId());
        }
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public void save(Role role, String[] permissionIds, String menuIds) {
        // 保存, 转换为持久态
        roleRepository.save(role);
        // 关联权限
        if(permissionIds!=null){
            for (String permissionId : permissionIds) {
                Permission permission = permissionRepository.findOne(Integer.parseInt(permissionId));
                role.getPermissions().add(permission);
            }
        }
        // 关联菜单
        if(StringUtils.isNotBlank(menuIds)){
            String[] menuIdsArray = menuIds.split(",");
            for (String menuId : menuIdsArray) {
                Menu menu = menuRepository.findOne(Integer.parseInt(menuId));
                role.getMenus().add(menu);
            }
        }
    }
}
