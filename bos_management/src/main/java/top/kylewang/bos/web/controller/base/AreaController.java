package top.kylewang.bos.web.controller.base;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import top.kylewang.bos.domain.base.Area;
import top.kylewang.bos.service.base.AreaService;
import top.kylewang.bos.utils.PinYin4jUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Kyle.Wang
 * 2017/12/30 0030 16:53
 */
@Controller
public class AreaController {

    @Autowired
    private AreaService areaService;


    /**
     * 批量导入
     * @param file
     * @throws IOException
     */
    @RequestMapping("area_batchImport.action")
    public void batchImport(MultipartFile file) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
        HSSFSheet sheet = workbook.getSheetAt(0);
        List<Area> list = new ArrayList<>();
        for (Row cells : sheet) {
            if (cells.getRowNum() == 0) {
                continue;
            }
            if (cells.getCell(0) == null || StringUtils.isBlank(cells.getCell(0).getStringCellValue())) {
                continue;
            }
            Area area = new Area();
            area.setId(cells.getCell(0).getStringCellValue());
            area.setProvince(cells.getCell(1).getStringCellValue());
            area.setCity(cells.getCell(2).getStringCellValue());
            area.setDistrict(cells.getCell(3).getStringCellValue());
            area.setPostcode(cells.getCell(4).getStringCellValue());

            //去掉"省","市","区" 后缀
            String province = area.getProvince().substring(0, area.getProvince().length() - 1);
            String city = area.getCity().substring(0, area.getCity().length() - 1);
            String district = area.getDistrict().substring(0, area.getDistrict().length() - 1);

            //简码
            StringBuffer stringBuffer = new StringBuffer();
            String[] headArray = PinYin4jUtils.getHeadByString(province + city + district);
            for (String head : headArray) {
                stringBuffer.append(head);
            }
            String shortCode = stringBuffer.toString();
            area.setShortcode(shortCode);

            //城市编码
            String cityCode = PinYin4jUtils.hanziToPinyin(city, "");
            area.setCitycode(cityCode);
            list.add(area);
        }
        areaService.saveBatch(list);
    }

    /**
     * 条件分页查询
     * @return
     */
    @RequestMapping("/area_pageQuery.action")
    @ResponseBody
    public Map<String,Object> pageQuery(Area area,int page,int rows){
        Pageable pageable = new PageRequest(page - 1, rows);
        Specification<Area> specification = new Specification<Area>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                if (StringUtils.isNotBlank(area.getProvince())) {
                    Predicate predicate1 = criteriaBuilder.like(root.get("province").as(String.class), "%" + area.getProvince() + "%");
                    list.add(predicate1);
                }
                if (StringUtils.isNotBlank(area.getCity())) {
                    Predicate predicate2 = criteriaBuilder.like(root.get("city").as(String.class), "%" + area.getCity() + "%");
                    list.add(predicate2);
                }
                if (StringUtils.isNotBlank(area.getDistrict())) {
                    Predicate predicate3 = criteriaBuilder.like(root.get("district").as(String.class), "%" + area.getDistrict() + "%");
                    list.add(predicate3);
                }
                return criteriaBuilder.and(list.toArray(new Predicate[0]));
            }
        };
        Page<Area> pageData = areaService.findPageData(specification,pageable);
        Map<String,Object> result = new HashMap<>(4);
        result.put("total",pageData.getNumberOfElements());
        result.put("rows",pageData.getContent());
        return result;
    }

}
