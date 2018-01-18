package top.kylewang.bos.web.controller.transit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.kylewang.bos.domain.transit.TransitInfo;
import top.kylewang.bos.service.transit.TransitService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kyle.Wang
 * 2018/1/13 0013 9:45
 */
@Controller
public class TransitController{

    @Autowired
    private TransitService transitService;

    /**
     * 对运单开启中转配送
     * @param wayBillIds
     * @return
     */
    @RequestMapping("transit_create.action")
    @ResponseBody
    public Map<String, Object> create(String wayBillIds){
        Map<String, Object> result = new HashMap<>(4);
        try {
            transitService.createTransits(wayBillIds);
            result.put("success",true);
            result.put("msg","开启中转配送成功!");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success",false);
            result.put("msg","开启中转配送失败!异常:"+e.getMessage());
        }
        return result;
    }

    /**
     * 分页查询
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("transit_pageQuery.action")
    @ResponseBody
    public Map<String,Object> pageQuery(int page,int rows){
        Pageable pageable = new PageRequest(page - 1, rows);
        Page<TransitInfo> pageData = transitService.findPageData(pageable);
        Map<String,Object> result = new HashMap<>(4);
        result.put("total",pageData.getNumberOfElements());
        result.put("rows",pageData.getContent());
        return result;
    }
}
