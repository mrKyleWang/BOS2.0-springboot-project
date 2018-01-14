package top.kylewang.bos.domain.page;

import top.kylewang.bos.domain.take_delivery.Promotion;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.List;

/**
 * 自定义分页数据封装对象
 * @author Kyle.Wang
 * 2018/1/6 0006 10:51
 */
@XmlRootElement(name = "pageBean")
@XmlSeeAlso({Promotion.class})
public class PageBean<T> {

    /**
     * 总记录数
     */
    private Long totalCount;
    /**
     * 当前页数据
     */
    private List<T> pageData;

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getPageData() {
        return pageData;
    }

    public void setPageData(List<T> pageData) {
        this.pageData = pageData;
    }
}
