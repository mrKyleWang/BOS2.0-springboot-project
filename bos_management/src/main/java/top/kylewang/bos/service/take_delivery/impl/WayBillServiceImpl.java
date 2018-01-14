package top.kylewang.bos.service.take_delivery.impl;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.kylewang.bos.dao.take_delivery.WayBillRepository;
import top.kylewang.bos.domain.take_delivery.WayBill;
import top.kylewang.bos.index.WayBillIndexRepository;
import top.kylewang.bos.service.take_delivery.WayBillService;

import java.util.List;

/**
 * @author Kyle.Wang
 * 2018/1/8 0008 15:13
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WayBillServiceImpl implements WayBillService {

    @Autowired
    private WayBillRepository wayBillRepository;

    @Autowired
    private WayBillIndexRepository wayBillIndexRepository;

    @Override
    public void save(WayBill wayBill) {
        // 去除无效Order属性
        if(wayBill.getOrder()!=null && (wayBill.getOrder().getId() == null || wayBill.getOrder().getId()==0)){
            wayBill.setOrder(null);
        }
        // 判断是更新还是新增
        WayBill persistentWayBill = wayBillRepository.findByWayBillNum(wayBill.getWayBillNum());
        if(persistentWayBill!=null && persistentWayBill.getId()!=null){
            // 更新操作, 判断运单状态是否为待发货:1
                if(persistentWayBill.getSignStatus()==1){
                    Integer id = persistentWayBill.getId();
                    BeanUtils.copyProperties(wayBill,persistentWayBill);
                    persistentWayBill.setId(id);
                    persistentWayBill.setSignStatus(1);
                    wayBillRepository.save(persistentWayBill);
                    // 保存索引
                    wayBillIndexRepository.save(persistentWayBill);
                }else{
                    throw new RuntimeException("运单已经发出,无法修改保存!!");
                }
        }else{
            // 新增操作
            wayBill.setSignStatus(1);
            wayBillRepository.save(wayBill);
            // 保存索引
            wayBillIndexRepository.save(wayBill);
        }
    }

    @Override
    public Page<WayBill> findPageData(WayBill wayBill, Pageable pageable) {
        // 判断条件是否存在
        /*
        wayBillNum:
        sendAddress:
        recAddress:
        sendProNum:
        signStatus:0
         */
        if(StringUtils.isBlank(wayBill.getWayBillNum())
                &&StringUtils.isBlank(wayBill.getSendAddress())
                &&StringUtils.isBlank(wayBill.getRecAddress())
                &&StringUtils.isBlank(wayBill.getSendProNum())
                &&(wayBill.getSignStatus()==null||wayBill.getSignStatus()==0)){
            // 无条件查询
            return wayBillRepository.findAll(pageable);
        }else{
            /*
            * 查询条件
            *    BoolQuery:多条件组合查询
            *       must : 条件必须成立(and)
            *       must not : 条件必须不成立(not)
            *       should : 条件可以成立(or)
            *    TermQuery:完全等值匹配
            *    WildCardQuery:通配符匹配
            */
            BoolQueryBuilder queryBuilder = new BoolQueryBuilder();
            // 运单号等值查询
            if(StringUtils.isNotBlank(wayBill.getWayBillNum())){
                TermQueryBuilder wayBillNumQuery = new TermQueryBuilder("wayBillNum", wayBill.getWayBillNum());
                queryBuilder.must(wayBillNumQuery);
            }
            // 发货地模糊查询
            if(StringUtils.isNotBlank(wayBill.getSendAddress())){
                // 情况1 : 条件本身是词条一部分, 直接进行模糊查询
                WildcardQueryBuilder sendAddressWildcardQuery = new WildcardQueryBuilder("sendAddress", "*"+wayBill.getSendAddress()+"*");
                // 情况2 : 条件需要分词后再进行词条匹配, 取交集(AND)
                QueryStringQueryBuilder sendAddressQueryStringQuery = new QueryStringQueryBuilder(wayBill.getSendAddress())
                        .field("sendAddress").defaultOperator(QueryStringQueryBuilder.Operator.AND);
                // 对两种情况下的查询取or 关系(should)
                BoolQueryBuilder sendAddressQuery = new BoolQueryBuilder();
                sendAddressQuery.should(sendAddressWildcardQuery);
                sendAddressQuery.should(sendAddressQueryStringQuery);
                queryBuilder.must(sendAddressQuery);
            }
            // 收货地模糊查询
            if(StringUtils.isNotBlank(wayBill.getRecAddress())){
                // 情况1 : 条件本身是词条一部分, 直接进行模糊查询
                WildcardQueryBuilder recAddressWildcardQuery = new WildcardQueryBuilder("recAddress", "*"+wayBill.getRecAddress()+"*");
                // 情况2 : 条件需要分词后再进行词条匹配, 取交集(AND)
                QueryStringQueryBuilder recAddressQueryStringQuery = new QueryStringQueryBuilder(wayBill.getRecAddress())
                        .field("recAddress").defaultOperator(QueryStringQueryBuilder.Operator.AND);
                // 对两种情况下的查询取or 关系(should)
                BoolQueryBuilder recAddressQuery = new BoolQueryBuilder();
                recAddressQuery.should(recAddressWildcardQuery);
                recAddressQuery.should(recAddressQueryStringQuery);
                queryBuilder.must(recAddressQuery);
            }
            // 快递产品类型等值查询
            if(StringUtils.isNotBlank(wayBill.getSendProNum())){
                TermQueryBuilder sendProNumQuery = new TermQueryBuilder("sendProNum", wayBill.getSendProNum());
                queryBuilder.must(sendProNumQuery);
            }
            // 运单状态等值查询
            if(wayBill.getSignStatus()!=null && wayBill.getSignStatus()!=0){
                TermQueryBuilder signStatusQuery = new TermQueryBuilder("signStatus", wayBill.getSignStatus());
                queryBuilder.must(signStatusQuery);
            }
            SearchQuery searchQuery = new NativeSearchQuery(queryBuilder);
            searchQuery.setPageable(pageable);
            return wayBillIndexRepository.search(searchQuery);
        }
    }

    @Override
    public WayBill findByWayBillNum(String wayBillNum) {
        return wayBillRepository.findByWayBillNum(wayBillNum);
    }

    @Override
    public void syncIndex() {
        List<WayBill> wayBillList = wayBillRepository.findAll();
        for (WayBill wayBill : wayBillList) {
            wayBillIndexRepository.save(wayBill);
        }
    }

    @Override
    public List<WayBill> findWayBills(WayBill wayBill) {
        if (StringUtils.isBlank(wayBill.getWayBillNum()) && StringUtils.isBlank(wayBill.getSendAddress()) && StringUtils.isBlank(wayBill.getRecAddress()) && StringUtils.isBlank(wayBill.getSendProNum()) && (wayBill.getSignStatus() == null || wayBill.getSignStatus() == 0)) {
            // 无条件查询
            return wayBillRepository.findAll();
        } else {
            BoolQueryBuilder queryBuilder = new BoolQueryBuilder();
            // 运单号等值查询
            if (StringUtils.isNotBlank(wayBill.getWayBillNum())) {
                TermQueryBuilder wayBillNumQuery = new TermQueryBuilder("wayBillNum", wayBill.getWayBillNum());
                queryBuilder.must(wayBillNumQuery);
            }
            // 发货地模糊查询
            if (StringUtils.isNotBlank(wayBill.getSendAddress())) {
                // 情况1 : 条件本身是词条一部分, 直接进行模糊查询
                WildcardQueryBuilder sendAddressWildcardQuery = new WildcardQueryBuilder("sendAddress", "*" + wayBill.getSendAddress() + "*");
                // 情况2 : 条件需要分词后再进行词条匹配, 取交集(AND)
                QueryStringQueryBuilder sendAddressQueryStringQuery = new QueryStringQueryBuilder(wayBill.getSendAddress()).field("sendAddress").defaultOperator(QueryStringQueryBuilder.Operator.AND);
                // 对两种情况下的查询取or 关系(should)
                BoolQueryBuilder sendAddressQuery = new BoolQueryBuilder();
                sendAddressQuery.should(sendAddressWildcardQuery);
                sendAddressQuery.should(sendAddressQueryStringQuery);
                queryBuilder.must(sendAddressQuery);
            }
            // 收货地模糊查询
            if (StringUtils.isNotBlank(wayBill.getRecAddress())) {
                // 情况1 : 条件本身是词条一部分, 直接进行模糊查询
                WildcardQueryBuilder recAddressWildcardQuery = new WildcardQueryBuilder("recAddress", "*" + wayBill.getRecAddress() + "*");
                // 情况2 : 条件需要分词后再进行词条匹配, 取交集(AND)
                QueryStringQueryBuilder recAddressQueryStringQuery = new QueryStringQueryBuilder(wayBill.getRecAddress()).field("recAddress").defaultOperator(QueryStringQueryBuilder.Operator.AND);
                // 对两种情况下的查询取or 关系(should)
                BoolQueryBuilder recAddressQuery = new BoolQueryBuilder();
                recAddressQuery.should(recAddressWildcardQuery);
                recAddressQuery.should(recAddressQueryStringQuery);
                queryBuilder.must(recAddressQuery);
            }
            // 快递产品类型等值查询
            if (StringUtils.isNotBlank(wayBill.getSendProNum())) {
                TermQueryBuilder sendProNumQuery = new TermQueryBuilder("sendProNum", wayBill.getSendProNum());
                queryBuilder.must(sendProNumQuery);
            }
            // 运单状态等值查询
            if (wayBill.getSignStatus() != null && wayBill.getSignStatus() != 0) {
                TermQueryBuilder signStatusQuery = new TermQueryBuilder("signStatus", wayBill.getSignStatus());
                queryBuilder.must(signStatusQuery);
            }
            SearchQuery searchQuery = new NativeSearchQuery(queryBuilder);
            Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);
            searchQuery.setPageable(pageable);
            return wayBillIndexRepository.search(searchQuery).getContent();
        }
    }
}
