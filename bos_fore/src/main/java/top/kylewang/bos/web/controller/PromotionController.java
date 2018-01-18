package top.kylewang.bos.web.controller;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.kylewang.bos.constants.Constants;
import top.kylewang.bos.domain.page.PageBean;
import top.kylewang.bos.domain.take_delivery.Promotion;

import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

/**
 * @author Kyle.Wang
 * 2018/1/6 0006 10:46
 */
@Controller
public class PromotionController {

    @Autowired
    private List<JacksonJaxbJsonProvider> jsonProvider;

    /**
     * 分页查询
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/promotion_pageQuery.action")
    @ResponseBody
    public PageBean pageQuery(int page, int rows) {
        //基于WebService获取bos_management 的活动列表数据信息
        PageBean pageBean = WebClient.create(Constants.BOS_MANAGEMENT_URL + "/services/promotionService/pageQuery?page=" + page + "&rows=" + rows, jsonProvider).accept(MediaType.APPLICATION_JSON).get(PageBean.class);
        return pageBean;
    }

    @RequestMapping("/promotion_showDetail.action")
    public String showDetail(Promotion promotion,Map<String,Object> map) {
        // 动态数据
        Promotion existPromotion = WebClient.create(Constants.BOS_MANAGEMENT_URL + "/services/promotionService/promotion/" + promotion.getId(), jsonProvider).accept(MediaType.APPLICATION_JSON).get(Promotion.class);
        map.put("promotion", existPromotion);
        return "promotion_detail";
    }
}
