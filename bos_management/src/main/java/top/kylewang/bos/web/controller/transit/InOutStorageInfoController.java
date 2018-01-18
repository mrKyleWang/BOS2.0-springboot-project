package top.kylewang.bos.web.controller.transit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import top.kylewang.bos.domain.transit.InOutStorageInfo;
import top.kylewang.bos.service.transit.InOutStorageInfoService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Kyle.Wang
 * 2018/1/13 0013 16:20
 */
@Controller
public class InOutStorageInfoController{

    @Autowired
    private InOutStorageInfoService inOutStorageInfoService;

    /**
     * 对运单开启中转配送
     * @param inOutStorageInfo
     * @param transitInfoId
     * @param response
     * @throws IOException
     */
    @RequestMapping("inoutstore_save.action")
    public void create(InOutStorageInfo inOutStorageInfo, String transitInfoId, HttpServletResponse response) throws IOException {
        inOutStorageInfoService.save(transitInfoId,inOutStorageInfo);
        response.sendRedirect("./pages/transit/transitinfo.html");
    }
}
