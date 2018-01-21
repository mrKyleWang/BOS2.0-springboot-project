package top.kylewang.bos.web.controller.base;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.kylewang.bos.domain.base.Courier;
import top.kylewang.bos.service.base.CourierService;

import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Kyle.Wang
 * 2017/12/30 0030 10:03
 */
@Controller
public class CourierController{

    @Autowired
    private CourierService courierService;

    /**
     * 保存快递员
     * @param courier
     * @param response
     * @throws IOException
     */
    @RequestMapping("courier_save.action")
    public void save(Courier courier, HttpServletResponse response) throws IOException {
        courierService.save(courier);
        response.sendRedirect("./pages/base/courier.html");
    }

    /**
     * 分页条件查询
     * @param courier
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/courier_pageQuery.action")
    @ResponseBody
    public Map<String,Object> pageQuery(Courier courier,int page,int rows){
        //分页查询对象
        Pageable pageable = new PageRequest(page-1,rows);
        //条件查询对象
        Specification<Courier> specification = new Specification<Courier>() {
            @Override
            public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                if(StringUtils.isNotBlank(courier.getCourierNum())){
                    //快递员编号等值查询
                    Predicate predicate1 = criteriaBuilder.equal(root.get("courierNum").as(String.class), courier.getCourierNum());
                    list.add(predicate1);
                }
                if(StringUtils.isNotBlank(courier.getType())){
                    //快递员类型等值查询
                    Predicate predicate2 = criteriaBuilder.equal(root.get("type").as(String.class), courier.getType());
                    list.add(predicate2);
                }
                if(StringUtils.isNotBlank(courier.getCompany())){
                    //所属单位模糊查询
                    Predicate predicate3 = criteriaBuilder.like(root.get("company").as(String.class), "%"+courier.getCompany()+"%");
                    list.add(predicate3);
                }
                //多表查询关联对象
                Join standard = root.join("standard", JoinType.INNER);
                if(courier.getStandard()!=null && StringUtils.isNotBlank(courier.getStandard().getName())){
                    //收派标准模糊查询
                    Predicate predicate4 = criteriaBuilder.like(standard.get("name").as(String.class), "%"+courier.getStandard().getName()+"%");
                    list.add(predicate4);
                }
                //转为参数数组
                return criteriaBuilder.and(list.toArray(new Predicate[0]));
            }
        };
        Page<Courier> pageData =  courierService.findPageData(specification,pageable);
        Map<String,Object> result = new HashMap<>(4);
        result.put("total",pageData.getNumberOfElements());
        result.put("rows",pageData.getContent());
        return result;
    }

    /**
     * 批量作废快递员
     * @param ids
     * @param response
     * @throws IOException
     */
    @RequestMapping("/courier_delBatch.action")
    public void delBatch(String ids, HttpServletResponse response) throws IOException {

        String[] idArray = ids.split(",");
        response.sendRedirect("./pages/base/courier.html");
        courierService.delBatch(idArray);
    }

    /**
     * 查询未关联定区快递员
     * @return
     */
    @RequestMapping("/courier_findnoassociation.action")
    @ResponseBody
    public List<Courier> findNoAssociation(){
        List<Courier> couriers = courierService.findNoAssociation();
        return couriers;
    }


}
