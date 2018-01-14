package top.kylewang.bos.service.system;

import top.kylewang.bos.domain.system.Role;
import top.kylewang.bos.domain.system.User;

import java.util.List;

/**
 * @author Kyle.Wang
 * 2018/1/10 0010 17:29
 */
public interface RoleService {

    /**
     * 根据用户查询角色列表
     * @param user
     * @return
     */
    List<Role> findByUser(User user);

    /**
     * 角色列表查询
     * @return
     */
    List<Role> findAll();

    /**
     * 添加角色
     * @param role
     * @param permissionIds
     * @param menuIds
     */
    void save(Role role, String[] permissionIds, String menuIds);
}
