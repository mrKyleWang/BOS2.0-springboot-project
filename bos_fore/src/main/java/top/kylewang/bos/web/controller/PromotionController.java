package top.kylewang.bos.web.controller;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.kylewang.bos.constants.Constants;
import top.kylewang.bos.domain.page.PageBean;
import top.kylewang.bos.domain.take_delivery.Promotion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;

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
    public PageBean pageQuery(int page,int rows){
        //基于WebService获取bos_management 的活动列表数据信息
        PageBean pageBean = WebClient
                .create(Constants.BOS_MANAGEMENT_URL+"/services/promotionService/pageQuery?page=" + page + "&rows=" + rows, jsonProvider)
                .accept(MediaType.APPLICATION_JSON).get(PageBean.class);
        return pageBean;
    }

    @RequestMapping("/promotion_showDetail.action")
    public void showDetail(Promotion promotion, HttpServletRequest request, HttpServletResponse response) throws Exception{
        String htmlRealPath = request.getServletContext().getRealPath("/freemarker");
        File htmlFile = new File(htmlRealPath+"/"+promotion.getId()+".html");
        if(!htmlFile.exists()){
            // 配置模板路径
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_22);
            String realPath = request.getServletContext().getRealPath("/WEB-INF/freemarker_templates");
            configuration.setDefaultEncoding("utf-8");
            configuration.setDirectoryForTemplateLoading(new File(realPath));
            // 获取模板对象
            Template template = configuration.getTemplate("promotion_detail.ftl");
            // 动态数据
            Promotion existPromotion = WebClient
                    .create(Constants.BOS_MANAGEMENT_URL+"/services/promotionService/promotion/"+promotion.getId(), jsonProvider)
                    .accept(MediaType.APPLICATION_JSON).get(Promotion.class);
            HashMap<String, Object> parameterMap = new HashMap<>(4);
            parameterMap.put("promotion",existPromotion);
            // 合并输出
            template.process(parameterMap,new OutputStreamWriter(new FileOutputStream(htmlFile),"utf-8"));
        }
        // html文件存在, 直接返回
        response.setContentType("text/html;charset=utf-8");
        response.getOutputStream().write(FileCopyUtils.copyToByteArray(htmlFile));
    }
}
