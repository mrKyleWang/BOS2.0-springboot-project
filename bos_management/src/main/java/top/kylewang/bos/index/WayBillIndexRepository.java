package top.kylewang.bos.index;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import top.kylewang.bos.domain.take_delivery.WayBill;

/**
 * @author Kyle.Wang
 * 2018/1/10 0010 9:48
 */
public interface WayBillIndexRepository extends ElasticsearchRepository<WayBill,Integer> {
}
