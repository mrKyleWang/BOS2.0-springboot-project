package top.kylewang.bos.service.system;

import top.kylewang.bos.domain.system.Menu;
import top.kylewang.bos.domain.system.User;

import java.util.List;

/**
 * @author Kyle.Wang
 * 2018/1/10 0010 21:21
 */
public interface MenuService {

    /**
     * 查询菜单列表
     * @return
     */
    List<Menu> findAll();

    /**
     * 保存
     * @param model
     */
    void save(Menu model);

    /**
     * 根据用户查询菜单
     * @param user
     * @return
     */
    List<Menu> findByUser(User user);
}
