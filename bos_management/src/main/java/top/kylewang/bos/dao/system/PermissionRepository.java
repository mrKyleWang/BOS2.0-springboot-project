package top.kylewang.bos.dao.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import top.kylewang.bos.domain.system.Permission;

import java.util.List;

/**
 * @author Kyle.Wang
 * 2018/1/10 0010 18:04
 */
public interface PermissionRepository extends JpaRepository<Permission,Integer> {


    /**
     * 根据用户id查询权限列表
     * @param id
     * @return
     */
    @Query("select p from Permission p inner join fetch p.roles r inner join fetch r.users u where u.id = ?1")
    List<Permission> findByUser(Integer id);
}
