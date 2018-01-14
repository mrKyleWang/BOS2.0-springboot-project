package top.kylewang.crm.service;

import top.kylewang.crm.domain.Customer;

import javax.ws.rs.*;
import java.util.List;

/**
 * @author Kyle.Wang
 * 2017/12/31 0031 16:12
 */
    public interface CustomerService {

    /**
     * 查询所有未关联客户列表
     * @return
     */
    @Path("/noassociationcustomers")
    @GET
    @Produces({"application/xml","application/json"})
    List<Customer> findNoAssociationCustomers();

    /**
     * 查询已关联到指定定区的客户列表
     * @param fixedAreaId
     * @return
     */
    @Path("associationfixedareacustomers/{fixedareaid}")
    @GET
    @Produces({"application/xml","application/json"})
    List<Customer> findAssociationFixedAreaCustomers(@PathParam("fixedareaid") String fixedAreaId);

    /**
     * 批量关联客户到指定定区
     * @param customerIdStr
     * @param fixedAreaId
     */
    @Path("associationcustomerstofixedarea")
    @PUT
    @Produces({"application/xml","application/json"})
    void associationCustomersToFixedArea(
            @QueryParam("customerIdStr") String customerIdStr,
            @QueryParam("fixedAreaId") String fixedAreaId);


    /**
     * 注册客户
     * @param customer
     */
    @Path("/customer")
    @POST
    @Consumes({"application/xml","application/json"})
    void register(Customer customer);


    /**
     * 根据电话查询客户
     * @param telephone
     * @return
     */
    @Path("/customer/telephone/{telephone}")
    @GET
    @Consumes({"application/xml","application/json"})
    Customer findByTelephone(@PathParam("telephone") String telephone);


    /**
     * 更新激活状态
     * @param telephone
     */
    @Path("/customer/updatetype/{telephone}")
    @PUT
    @Consumes({"application/xml","application/json"})
    void updateType(@PathParam("telephone") String telephone);


    /**
     * 客户登录
     * @param telephone
     * @param password
     * @return
     */
    @Path("/customer/login")
    @GET
    @Consumes({"application/xml","application/json"})
    Customer login(@QueryParam("telephone") String telephone,
                   @QueryParam("password") String password);


    /**
     * 根据地址查询定区id
     * @param address
     * @return
     */
    @Path("/customer/findFixedAreaIdByAddress")
    @GET
    @Consumes({"application/xml","application/json"})
    String findFixedAreaIdByAddress(@QueryParam("address") String address);

}
