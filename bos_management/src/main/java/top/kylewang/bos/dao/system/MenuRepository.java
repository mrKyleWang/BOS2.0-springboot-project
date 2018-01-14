package top.kylewang.bos.dao.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import top.kylewang.bos.domain.system.Menu;

import java.util.List;

/**
 * @author Kyle.Wang
 * 2018/1/10 0010 21:21
 */
public interface MenuRepository extends JpaRepository<Menu,Integer> {
    /**
     * 根据用户id查询菜单
     * @param id
     * @return
     */
    @Query("select m from Menu m inner join fetch m.roles r inner join fetch r.users u where u.id = ?1 order by m.priority")
    List<Menu> findByUser(Integer id);
}
