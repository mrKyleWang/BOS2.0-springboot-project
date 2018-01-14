package top.kylewang.bos.service.take_delivery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import top.kylewang.bos.domain.page.PageBean;
import top.kylewang.bos.domain.take_delivery.Promotion;

import javax.ws.rs.*;
import java.util.Date;

/**
 * @author Kyle.Wang
 * 2018/1/4 0004 20:59
 */
public interface PromotionService {

    /**
     * 保存
     * @param model
     */
    void save(Promotion model);

    /**
     * 分页查询
     * @param pageable
     * @return
     */
    Page<Promotion> findPageData(Pageable pageable);

    /**
     * 根据page和rows 返回分页数据
     * @param page
     * @param rows
     * @return
     */
    @Path("/pageQuery")
    @GET
    @Produces({"application/xml","application/json"})
    PageBean<Promotion> findPageData(@QueryParam("page") Integer page,
                                     @QueryParam("rows") Integer rows);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Path("/promotion/{id}")
    @GET
    @Produces({"application/xml","application/json"})
    Promotion findById(@PathParam("id") Integer id);

    /**
     * 根据当前时间修改活动状态
     * @param now
     */
    void updateStatus(Date now);
}
