package top.kylewang.bos.web.action.base;

import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import top.kylewang.bos.domain.base.FixedArea;
import top.kylewang.bos.service.base.FixedAreaService;
import top.kylewang.bos.web.action.common.BaseAction;
import top.kylewang.crm.domain.Customer;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Kyle.Wang
 * 2017/12/30 0030 21:55
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("json-default")
public class FixedAreaAction extends BaseAction<FixedArea> {

    @Autowired
    private FixedAreaService fixedAreaService;

    /**
     * 保存定区
     * @return
     */
    @Action(value = "fixedArea_save",
            results = {@Result(name = "success", location = "./pages/base/fixed_area.html", type = "redirect")})
    public String save() {
        fixedAreaService.save(model);
        return SUCCESS;
    }

    /**
     * 条件分页查询
     * @return
     */
    @Action(value = "fixedArea_pageQuery",
            results = {@Result(name = "success", type = "json")})
    public String pageQuery() {
        Pageable pageable = new PageRequest(page - 1, rows);
        Specification<FixedArea> specification = new Specification<FixedArea>() {
            @Override
            public Predicate toPredicate(Root<FixedArea> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                if (StringUtils.isNotBlank(model.getId())) {
                    Predicate predicate1 = criteriaBuilder.equal(root.get("id").as(String.class), model.getId());
                    list.add(predicate1);
                }
                if (StringUtils.isNotBlank(model.getCompany())) {
                    Predicate predicate2 = criteriaBuilder.like(root.get("company").as(String.class), model.getCompany());
                    list.add(predicate2);
                }
                return criteriaBuilder.and(list.toArray(new Predicate[0]));
            }
        };
        Page<FixedArea> pageData = fixedAreaService.findPageData(specification, pageable);
        pushPageDataToValueStack(pageData);
        return SUCCESS;
    }

    /**
     * 查询所有未关联定区的客户
     * @return
     */
    @Action(value = "fixedArea_findNoAssociationCustomers", results = {@Result(name = "success", type = "json")})
    public String findNoAssociationCustomers() {

        Collection<? extends Customer> customers = WebClient
                .create("http://localhost:9002/crm/services/customerService/noassociationcustomers")
                .accept(MediaType.APPLICATION_JSON)
                .getCollection(Customer.class);
        ActionContext.getContext().getValueStack().push(customers);
        return SUCCESS;
    }

    /**
     * 查询所有已关联定区的客户
     * @return
     */
    @Action(value = "fixedArea_findHasAssociationFixedAreaCustomers", results = {@Result(name = "success", type = "json")})
    public String findHasAssociationFixedAreaCustomers() {
        Collection<? extends Customer> customers = WebClient
                .create("http://localhost:9002/crm/services/customerService/associationfixedareacustomers/" + model.getId())
                .accept(MediaType.APPLICATION_JSON)
                .getCollection(Customer.class);
        ActionContext.getContext().getValueStack().push(customers);
        return SUCCESS;
    }

    private String[] customerIds;
    public void setCustomerIds(String[] customerIds) {
        this.customerIds = customerIds;
    }

    /**
     * 关联客户
     * @return
     */
    @Action(value = "fixedArea_associationCustomersToFixedArea",
            results = {@Result(name = "success", location = "./pages/base/fixed_area.html", type = "redirect")})
    public String associationCustomersToFixedArea() {
        String customerIdStr = StringUtils.join(customerIds, ",");
        WebClient
                .create("http://localhost:9002/crm/services/customerService/associationcustomerstofixedarea?customerIdStr="+customerIdStr+"&fixedAreaId="+model.getId())
                .put(null);
        return SUCCESS;
    }


    private Integer courierId;
    private Integer takeTimeId;
    public void setCourierId(Integer courierId) {
        this.courierId = courierId;
    }
    public void setTakeTimeId(Integer takeTimeId) {
        this.takeTimeId = takeTimeId;
    }

    /**
     * 关联快递员
     * @return
     */
    @Action(value = "fixedArea_associationCourierToFixedArea",
            results = {@Result(name = "success",location = "./pages/base/fixed_area.html",type = "redirect")})
    public String associationCourierToFixedArea(){
        fixedAreaService.associationCourierToFixedArea(model,courierId,takeTimeId);
        return SUCCESS;
    }
}
