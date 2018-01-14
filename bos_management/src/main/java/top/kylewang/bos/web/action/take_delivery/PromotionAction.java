package top.kylewang.bos.web.action.take_delivery;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import top.kylewang.bos.domain.take_delivery.Promotion;
import top.kylewang.bos.service.take_delivery.PromotionService;
import top.kylewang.bos.web.action.common.BaseAction;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author Kyle.Wang
 * 2018/1/4 0004 21:00
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("json-default")
public class PromotionAction extends BaseAction<Promotion>{

    @Autowired
    private PromotionService promotionService;


    private File titleImgFile;
    private String titleImgFileFileName;

    public void setTitleImgFile(File titleImgFile) {
        this.titleImgFile = titleImgFile;
    }

    public void setTitleImgFileFileName(String titleImgFileFileName) {
        this.titleImgFileFileName = titleImgFileFileName;
    }

    @Action(value = "promotion_save",
            results = {@Result(name = "success",
                    location = "./pages/take_delivery/promotion.html",
                    type = "redirect")})
    public String save() throws IOException {
        // 服务器保存路径(绝对路径)
        String savePath = ServletActionContext.getServletContext().getRealPath("/upload");
        // 用户访问路径(相对路径)
        String saveUrl = ServletActionContext.getRequest().getContextPath()+"/upload";
        // 生成随机文件名
        UUID uuid = UUID.randomUUID();
        String ext = titleImgFileFileName.substring(titleImgFileFileName.lastIndexOf("."));
        String saveFileName = uuid+ext;
        // 保存文件
        FileUtils.copyFile(titleImgFile, new File(savePath + "/" + saveFileName));
        // 补全属性
        model.setTitleImg(saveUrl+"/"+saveFileName);
        // 调用Service保存Promotion对象
        promotionService.save(model);
        return SUCCESS;
    }

    @Action(value = "promotion_pageQuery",
            results = {@Result(name = "success",type = "json")})
    public String pageQuery(){
        Pageable pageable = new PageRequest(page - 1, rows);
        Page<Promotion> pageData =  promotionService.findPageData(pageable);
        pushPageDataToValueStack(pageData);
        return SUCCESS;
    }
}
