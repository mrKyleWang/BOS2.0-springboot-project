package top.kylewang.bos.web.controller.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.kylewang.bos.domain.base.Standard;
import top.kylewang.bos.service.base.StandardService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kyle.Wang
 * 2017/12/29 0029 11:46
 */
@Controller
public class StandardController{

    @Autowired
    private StandardService standardService;

    /**
     * 保存收派标准
     * @param standard
     * @param response
     * @throws IOException
     */
    @RequestMapping("/standard_save.action")
    public void save(Standard standard,HttpServletResponse response) throws IOException {
        standardService.save(standard);
        response.sendRedirect("./pages/base/standard.html");
    }

    /**
     * 分页查询
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/standard_pageQuery.action")
    @ResponseBody
    public Map<String,Object> pageQuery(int page,int rows){
        //封装参数(注意,PageRequest的page从0开始,而页面从1开始)
        Pageable pageable= new PageRequest(page - 1, rows);
        Page<Standard> pageData = standardService.pageQuery(pageable);
        Map<String,Object> result = new HashMap<String,Object>(4);
        //返回页面所需数据格式
        result.put("total",pageData.getNumberOfElements());
        result.put("rows",pageData.getContent());
        //压入栈顶,通过json-plugin返回json数据
        return result;
    }

    /**
     * 查询所有
     * @return
     */
    @RequestMapping("/standard_findAll.action")
    @ResponseBody
    public List<Standard> findAll(){
        List<Standard> list = standardService.findAll();
        return list;
    }

}
