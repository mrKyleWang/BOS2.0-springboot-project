package top.kylewang.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import top.kylewang.bos.domain.base.Area;

/**
 * @author Kyle.Wang
 * 2017/12/30 0030 17:04
 */
public interface AreaRepository extends JpaRepository<Area,String> ,JpaSpecificationExecutor<Area>{

    /**
     * 根据省市区查询区域
     * @param province
     * @param city
     * @param district
     * @return
     */
    Area findByProvinceAndCityAndDistrict(String province,String city,String district);
}
