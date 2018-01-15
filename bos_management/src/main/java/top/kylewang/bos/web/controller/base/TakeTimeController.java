package top.kylewang.bos.web.controller.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.kylewang.bos.domain.base.TakeTime;
import top.kylewang.bos.service.base.TakeTimeService;

import java.util.List;

/**
 * @author Kyle.Wang
 * 2018/1/2 0002 15:22
 */
@Controller
public class TakeTimeController {

    @Autowired
    private TakeTimeService takeTimeService;

    /**
     * 查询收派时间列表
     * @return
     */
    @RequestMapping("/takeTime_findAll.action")
    @ResponseBody
    public List<TakeTime> findAll(){
        List<TakeTime> takeTimes =  takeTimeService.findAll();
        return takeTimes;
    }
}
