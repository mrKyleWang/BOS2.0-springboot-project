package top.kylewang.bos.service.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.kylewang.bos.dao.system.RoleRepository;
import top.kylewang.bos.dao.system.UserRepository;
import top.kylewang.bos.domain.system.Role;
import top.kylewang.bos.domain.system.User;
import top.kylewang.bos.service.system.UserService;

import java.util.List;

/**
 * @author Kyle.Wang
 * 2018/1/10 0010 16:23
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void save(User user, String[] roleIds) {
        userRepository.save(user);
        // 关联角色
        if(roleIds!=null){
            for (String roleId : roleIds) {
                Role role = roleRepository.findOne(Integer.parseInt(roleId));
                user.getRoles().add(role);
            }
        }
    }
}
