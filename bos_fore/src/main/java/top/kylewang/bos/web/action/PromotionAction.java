package top.kylewang.bos.web.action;

import com.opensymphony.xwork2.ActionContext;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.FileUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import top.kylewang.bos.constants.Constants;
import top.kylewang.bos.domain.page.PageBean;
import top.kylewang.bos.domain.take_delivery.Promotion;
import top.kylewang.bos.web.action.common.BaseAction;

import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;

/**
 * @author Kyle.Wang
 * 2018/1/6 0006 10:46
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("json-default")
public class PromotionAction extends BaseAction<Promotion>{


    @Action(value = "promotion_pageQuery",
            results = {@Result(name = "success",type = "json")})
    public String pageQuery(){
        //基于WebService获取bos_management 的活动列表数据信息
        PageBean pageBean = WebClient
                .create(Constants.BOS_MANAGEMENT_URL+"/bos/services/promotionService/pageQuery?page=" + page + "&rows=" + rows)
                .accept(MediaType.APPLICATION_JSON).get(PageBean.class);
        ActionContext.getContext().getValueStack().push(pageBean);
        return SUCCESS;
    }

    @Action(value = "promotion_showDetail")
    public String showDetail() throws Exception{
        String htmlRealPath = ServletActionContext.getServletContext().getRealPath("/freemarker");
        File htmlFile = new File(htmlRealPath+"/"+model.getId()+".html");
        if(!htmlFile.exists()){
            // 配置模板路径
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_22);
            String realPath = ServletActionContext.getServletContext().getRealPath("/WEB-INF/freemarker_templates");
            configuration.setDefaultEncoding("utf-8");
            configuration.setDirectoryForTemplateLoading(new File(realPath));
            // 获取模板对象
            Template template = configuration.getTemplate("promotion_detail.ftl");
            // 动态数据
            Promotion promotion = WebClient
                    .create(Constants.BOS_MANAGEMENT_URL+"/bos/services/promotionService/promotion/"+model.getId())
                    .accept(MediaType.APPLICATION_JSON).get(Promotion.class);
            HashMap<String, Object> parameterMap = new HashMap<>(4);
            parameterMap.put("promotion",promotion);
            // 合并输出
            template.process(parameterMap,new OutputStreamWriter(new FileOutputStream(htmlFile),"utf-8"));
        }
        // html文件存在, 直接返回
        ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
        FileUtils.copyFile(htmlFile,ServletActionContext.getResponse().getOutputStream());
        return NONE;
    }
}
