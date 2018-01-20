package top.kylewang.bos.web.controller.take_delivery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import top.kylewang.bos.domain.take_delivery.Promotion;
import top.kylewang.bos.service.take_delivery.PromotionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Kyle.Wang
 * 2018/1/4 0004 21:00
 */
@Controller
public class PromotionController{

    @Autowired
    private PromotionService promotionService;

    private final ResourceLoader resourceLoader;

    @Autowired
    public PromotionController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * 宣传活动保存
     * @param promotion
     * @param titleImgFile
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("promotion_save.action")
    public void save(Promotion promotion, MultipartFile titleImgFile, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 用户访问路径(相对路径)
        String saveUrl = request.getContextPath()+"/upload";
        // 生成随机文件名
        UUID uuid = UUID.randomUUID();
        String ext = titleImgFile.getOriginalFilename().substring(titleImgFile.getOriginalFilename().lastIndexOf("."));
        String saveFileName = uuid+ext;
        // 保存文件
        File absoluteFile = new File("upload/"+ saveFileName).getAbsoluteFile();
        titleImgFile.transferTo(absoluteFile);
        // 补全属性
        promotion.setTitleImg(saveUrl+"/"+saveFileName);
        // 调用Service保存Promotion对象
        promotionService.save(promotion);
        response.sendRedirect("./pages/take_delivery/promotion.html");
    }

    /**
     * 分页查询
     * @return
     */
    @RequestMapping("promotion_pageQuery.action")
    @ResponseBody
    public Map<String,Object> pageQuery(int page,int rows){
        Pageable pageable = new PageRequest(page - 1, rows);
        Page<Promotion> pageData =  promotionService.findPageData(pageable);
        Map<String,Object> result = new HashMap<>(4);
        result.put("total",pageData.getNumberOfElements());
        result.put("rows",pageData.getContent());
        return result;
    }
}
