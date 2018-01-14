package top.kylewang.bos.service.system;

import top.kylewang.bos.domain.system.Permission;
import top.kylewang.bos.domain.system.User;

import java.util.List;

/**
 * @author Kyle.Wang
 * 2018/1/10 0010 18:02
 */
public interface PermissionService {

    /**
     * 根据用户查询权限列表
     * @param user
     * @return
     */
    List<Permission> findByUser(User user);

    /**
     * 查询权限列表
     * @return
     */
    List<Permission> findAll();

    /**
     * 保存权限
     * @param permission
     */
    void save(Permission permission);



}
