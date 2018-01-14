package top.kylewang.bos.service.base;

import top.kylewang.bos.domain.base.TakeTime;

import java.util.List;

/**
 * @author Kyle.Wang
 * 2018/1/2 0002 15:25
 */
public interface TakeTimeService {

    /**
     * 查询收派时间列表
     * @return
     */
    List<TakeTime> findAll();

}
