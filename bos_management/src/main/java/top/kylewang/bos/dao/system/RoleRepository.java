package top.kylewang.bos.dao.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import top.kylewang.bos.domain.system.Role;

import java.util.List;

/**
 * @author Kyle.Wang
 * 2018/1/10 0010 18:03
 */
public interface RoleRepository extends JpaRepository<Role,Integer> {

    /**
     * 根据用户id查询角色列表
     * @param id
     * @return
     */
    @Query("select r from Role r inner join fetch r.users u where u.id = ?1")
    List<Role> findByUser(Integer id);
}
