package top.kylewang.bos.web.controller.base;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.kylewang.bos.domain.base.FixedArea;
import top.kylewang.bos.service.base.FixedAreaService;
import top.kylewang.crm.domain.Customer;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.*;

/**
 * @author Kyle.Wang
 * 2017/12/30 0030 21:55
 */
@Controller
public class FixedAreaController{

    @Autowired
    private FixedAreaService fixedAreaService;

    /**
     * 保存定区
     * @param fixedArea
     * @param response
     * @throws IOException
     */
    @RequestMapping("fixedArea_save.action")
    public void save(FixedArea fixedArea,HttpServletResponse response) throws IOException {
        fixedAreaService.save(fixedArea);
        response.sendRedirect("./pages/base/fixed_area.html");
    }

    /**
     * 条件分页查询
     * @param fixedArea
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("fixedArea_pageQuery.action")
    @ResponseBody
    public Map<String,Object> pageQuery(FixedArea fixedArea,int page,int rows) {
        Pageable pageable = new PageRequest(page - 1, rows);
        Specification<FixedArea> specification = new Specification<FixedArea>() {
            @Override
            public Predicate toPredicate(Root<FixedArea> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                if (StringUtils.isNotBlank(fixedArea.getId())) {
                    Predicate predicate1 = criteriaBuilder.equal(root.get("id").as(String.class), fixedArea.getId());
                    list.add(predicate1);
                }
                if (StringUtils.isNotBlank(fixedArea.getCompany())) {
                    Predicate predicate2 = criteriaBuilder.like(root.get("company").as(String.class), fixedArea.getCompany());
                    list.add(predicate2);
                }
                return criteriaBuilder.and(list.toArray(new Predicate[0]));
            }
        };
        Page<FixedArea> pageData = fixedAreaService.findPageData(specification, pageable);
        Map<String,Object> result = new HashMap<>(4);
        result.put("total",pageData.getNumberOfElements());
        result.put("rows",pageData.getContent());
        return result;
    }

    /**
     * 查询所有未关联定区的客户
     * @return
     */
    @RequestMapping("fixedArea_findNoAssociationCustomers.action")
    @ResponseBody
    public Collection<? extends Customer> findNoAssociationCustomers() {

        Collection<? extends Customer> customers = WebClient
                .create("http://localhost:9002/crm/services/customerService/noassociationcustomers")
                .accept(MediaType.APPLICATION_JSON)
                .getCollection(Customer.class);
        return customers;
    }

    /**
     * 查询所有已关联定区的客户
     * @return
     */
    @RequestMapping("fixedArea_findHasAssociationFixedAreaCustomers.action")
    @ResponseBody
    public Collection<? extends Customer> findHasAssociationFixedAreaCustomers(FixedArea fixedArea) {
        Collection<? extends Customer> customers = WebClient
                .create("http://localhost:9002/crm/services/customerService/associationfixedareacustomers/" + fixedArea.getId())
                .accept(MediaType.APPLICATION_JSON)
                .getCollection(Customer.class);
        return customers;
    }

    /**
     * 关联客户
     * @param customerIds
     * @param response
     * @throws IOException
     */
    @RequestMapping("fixedArea_associationCustomersToFixedArea.action")
    @ResponseBody
    public void associationCustomersToFixedArea(FixedArea fixedArea,String[] customerIds,HttpServletResponse response) throws IOException {
        String customerIdStr = StringUtils.join(customerIds, ",");
        WebClient
                .create("http://localhost:9002/crm/services/customerService/associationcustomerstofixedarea?customerIdStr="+customerIdStr+"&fixedAreaId="+fixedArea.getId())
                .put(null);
        response.sendRedirect("./pages/base/fixed_area.html");
    }

    /**
     * 关联快递员
     * @param fixedArea
     * @param courierId
     * @param takeTimeId
     * @param response
     * @throws IOException
     */
    @RequestMapping("fixedArea_associationCourierToFixedArea.action")
    public void associationCourierToFixedArea(FixedArea fixedArea,Integer courierId,Integer takeTimeId,HttpServletResponse response) throws IOException {
        fixedAreaService.associationCourierToFixedArea(fixedArea,courierId,takeTimeId);
        response.sendRedirect("./pages/base/fixed_area.html");
    }
}
