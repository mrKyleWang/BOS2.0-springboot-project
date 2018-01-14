package top.kylewang.bos.web.action.take_delivery;

import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import top.kylewang.bos.web.action.common.BaseAction;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Kyle.Wang
 * 2018/1/4 0004 19:32
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("json-default")
public class ImageAction extends BaseAction<Object>{

    private File imgFile;
    private String imgFileFileName;
    private String imgFileContentType;

    public void setImgFile(File imgFile) {
        this.imgFile = imgFile;
    }
    public void setImgFileFileName(String imgFileFileName) {
        this.imgFileFileName = imgFileFileName;
    }
    public void setImgFileContentType(String imgFileContentType) {
        this.imgFileContentType = imgFileContentType;
    }


    @Action(value = "image_upload",
            results = {@Result(name = "success",type = "json")})
    public String upload() throws IOException {
        System.out.println("文件:"+imgFile);
        System.out.println("文件名:"+imgFileFileName);
        System.out.println("文件类型:"+imgFileContentType);

        // 服务器保存路径(绝对路径)
        String savePath = ServletActionContext.getServletContext().getRealPath("/upload");
        // 用户访问路径(相对路径)
        String saveUrl = ServletActionContext.getRequest().getContextPath()+"/upload";
        System.out.println(saveUrl);
        // 生成随机图片名
        UUID uuid = UUID.randomUUID();
        // 文件扩展名
        String ext = imgFileFileName.substring(imgFileFileName.lastIndexOf("."));
        // 保存图片名
        String saveFileName = uuid+ext;
        // 保存图片
        FileUtils.copyFile(imgFile, new File(savePath +"/"+ saveFileName));
        // 向浏览器响应响应数据
        Map<String,Object> result  = new HashMap<>(4);
        result.put("error",0);
        result.put("url",saveUrl+"/"+saveFileName);
        ActionContext.getContext().getValueStack().push(result);
        return SUCCESS;
    }

    @Action(value = "image_manage",
            results = {@Result(name = "success",type = "json")})
    public String manage() {
        // 图片根目录路径
        String rootPath = ServletActionContext.getServletContext().getRealPath("/upload/");
        File directory = new File(rootPath);
        // 根目录url
        String rootUrl = ServletActionContext.getRequest().getContextPath()+"/upload/";
        // 图片扩展名
        String[] fileTypes = new String[] { "gif", "jpg", "jpeg", "png", "bmp" };
        // 遍历目录下的文件
        List<Map<String, Object>> fileList = new ArrayList<>();
        if (directory.listFiles() != null) {
            for (File file : directory.listFiles()) {
                Map<String, Object> hash = new HashMap<>();
                String fileName = file.getName();
                if (file.isDirectory()) {
                    hash.put("is_dir", true);
                    hash.put("has_file", (file.listFiles() != null));
                    hash.put("filesize", 0L);
                    hash.put("is_photo", false);
                    hash.put("filetype", "");
                } else {
                    String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                    hash.put("is_dir", false);
                    hash.put("has_file", false);
                    hash.put("filesize", file.length());
                    hash.put("is_photo", Arrays.asList(fileTypes).contains(fileExt));
                    hash.put("filetype", fileExt);
                }
                hash.put("filename", fileName);
                hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
                fileList.add(hash);
            }
        }
        // 返回页面所需数据
        Map<String, Object> result = new HashMap<>();
        result.put("moveup_dir_path", "");
        result.put("current_dir_path", rootPath);
        result.put("current_url", rootUrl);
        result.put("total_count", fileList.size());
        result.put("file_list", fileList);
        ActionContext.getContext().getValueStack().push(result);
        return SUCCESS;
    }
}



