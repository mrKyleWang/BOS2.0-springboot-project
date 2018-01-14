package top.kylewang.bos.service.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.kylewang.bos.dao.system.MenuRepository;
import top.kylewang.bos.domain.system.Menu;
import top.kylewang.bos.domain.system.User;
import top.kylewang.bos.service.system.MenuService;

import java.util.List;

/**
 * @author Kyle.Wang
 * 2018/1/10 0010 21:21
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Override
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }

    @Override
    public void save(Menu menu) {
        if(menu.getParentMenu()!=null && menu.getParentMenu().getId()==0){
            menu.setParentMenu(null);
        }
        menuRepository.save(menu);
    }

    @Override
    public List<Menu> findByUser(User user) {
        // 判断:如果是管理员则获得所有权限
        if("admin".equals(user.getUsername())){
            return menuRepository.findAll();
        }else{
            return menuRepository.findByUser(user.getId());
        }
    }
}
