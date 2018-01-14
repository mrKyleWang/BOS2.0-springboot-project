package top.kylewang.bos.service.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.kylewang.bos.dao.system.PermissionRepository;
import top.kylewang.bos.domain.system.Permission;
import top.kylewang.bos.domain.system.User;
import top.kylewang.bos.service.system.PermissionService;

import java.util.List;

/**
 * @author Kyle.Wang
 * 2018/1/10 0010 18:03
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PermissionServiceImpl implements PermissionService{

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public List<Permission> findByUser(User user) {
        // 判断:如果是管理员则获得所有权限
        if("admin".equals(user.getUsername())){
            return permissionRepository.findAll();
        }else{
            return permissionRepository.findByUser(user.getId());
        }
    }

    @Override
    public List<Permission> findAll() {
        return permissionRepository.findAll();
    }

    @Override
    public void save(Permission permission) {
        permissionRepository.save(permission);
    }
}
