package top.kylewang.crm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import top.kylewang.crm.domain.Customer;

import java.util.List;

/**
 * @author Kyle.Wang
 * 2017/12/31 0031 16:51
 */
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    /**
     * 查询所有未关联客户列表
     * @return
     */
    List<Customer> findByFixedAreaIdIsNull();

    /**
     * 查询已关联到指定定区的客户列表
     * @param fixedAreaId
     * @return
     */
    List<Customer> findByFixedAreaId(String fixedAreaId);

    /**
     * 批量关联客户到指定定区
     * @param fixedAreaId
     * @param id
     */
    @Query("update Customer set fixedAreaId = ?1 where id = ?2")
    @Modifying
    void updateFixedAreaId(String fixedAreaId, Integer id);

    /**
     * 解除客户关联定区
     * @param fixedAreaId
     */
    @Query("update Customer set fixedAreaId = null where fixedAreaId=?1")
    @Modifying
    void clearFixedAreaId(String fixedAreaId);

    /**
     * 根据电话查询客户
     * @param telephone
     * @return
     */
    Customer findByTelephone(String telephone);

    /**
     * 更新激活状态
     * @param telephone
     */
    @Query("update Customer set type = 1 where telephone=?1")
    @Modifying
    void updateType(String telephone);

    /**
     * 根据电话和密码查询客户
     * @param telephone
     * @param password
     * @return
     */
    Customer findByTelephoneAndPassword(String telephone,String password);


    /**
     * 根据地址查询定区id
     * @param address
     * @return
     */
    @Query("select fixedAreaId from Customer where address = ?1")
    String findFixedAreaIdByAddress(String address);

}
