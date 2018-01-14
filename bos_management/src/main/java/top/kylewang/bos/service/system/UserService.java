package top.kylewang.bos.service.system;

import top.kylewang.bos.domain.system.User;

import java.util.List;

/**
 * @author Kyle.Wang
 * 2018/1/10 0010 16:23
 */
public interface UserService {


    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 查询用户列表
     * @return
     */
    List<User> findAll();

    /**
     * 保存用户
     * @param user
     * @param roleIds
     */
    void save(User user, String[] roleIds);

}
