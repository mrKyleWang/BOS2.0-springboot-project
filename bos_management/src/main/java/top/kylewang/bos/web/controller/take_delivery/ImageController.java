package top.kylewang.bos.web.controller.take_delivery;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Kyle.Wang
 * 2018/1/4 0004 19:32
 */
@Controller
public class ImageController {


    /**
     * 图片上传
     * @param imgFile
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("/image_upload.action")
    @ResponseBody
    public Map<String,Object> upload(MultipartFile imgFile, HttpServletRequest request) throws IOException {
        System.out.println("文件:"+imgFile);
        System.out.println("文件名:"+imgFile.getOriginalFilename());
        System.out.println("文件类型:"+imgFile.getContentType());

        // 服务器保存路径(绝对路径)
        String savePath = request.getServletContext().getRealPath("/upload");
        // 用户访问路径(相对路径)
        String saveUrl = request.getContextPath()+"/upload";
        System.out.println(saveUrl);
        // 生成随机图片名
        UUID uuid = UUID.randomUUID();
        // 文件扩展名
        String ext = imgFile.getOriginalFilename().substring(imgFile.getOriginalFilename().lastIndexOf("."));
        // 保存图片名
        String saveFileName = uuid+ext;
        // 保存图片
        imgFile.transferTo(new File("/upload/"+ saveFileName));
        // 向浏览器响应响应数据
        Map<String,Object> result  = new HashMap<>(4);
        result.put("error",0);
        result.put("url",saveUrl+"/"+saveFileName);
        return result;
    }

    /**
     * 图片管理回显
     * @param request
     * @return
     */
    @RequestMapping("/image_manage.action")
    @ResponseBody
    public Map<String, Object> manage(HttpServletRequest request) {
        // 图片根目录路径
        String rootPath = request.getServletContext().getRealPath("/upload/");
        File directory = new File(rootPath);
        // 根目录url
        String rootUrl = request.getContextPath()+"/upload/";
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
        return result;
    }
}



