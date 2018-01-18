package top.kylewang.bos.web.controller.transit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import top.kylewang.bos.domain.transit.SignInfo;
import top.kylewang.bos.service.transit.SignInfoService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Kyle.Wang
 * 2018/1/13 0013 17:07
 */
@Controller
public class SignInfoController {

    @Autowired
    private SignInfoService signInfoService;

    /**
     * 签收信息保存
     * @param signInfo
     * @param transitInfoId
     * @param response
     * @throws IOException
     */
    @RequestMapping("sign_save.action")
    public void create(SignInfo signInfo,String transitInfoId, HttpServletResponse response) throws IOException {
        signInfoService.save(transitInfoId,signInfo);
        response.sendRedirect("./pages/transit/transitinfo.html");
    }
}
