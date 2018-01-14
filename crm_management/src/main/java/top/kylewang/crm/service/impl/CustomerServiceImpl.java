package top.kylewang.crm.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.kylewang.crm.dao.CustomerRepository;
import top.kylewang.crm.domain.Customer;
import top.kylewang.crm.service.CustomerService;

import java.util.List;

/**
 * @author Kyle.Wang
 * 2017/12/31 0031 16:58
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;


    @Override
    public List<Customer> findNoAssociationCustomers() {
        return customerRepository.findByFixedAreaIdIsNull();
    }

    @Override
    public List<Customer> findAssociationFixedAreaCustomers(String fixedAreaId) {
        return customerRepository.findByFixedAreaId(fixedAreaId);
    }

    @Override
    public void associationCustomersToFixedArea(String customerIdStr, String fixedAreaId) {
        //清空客户所关联定区
        customerRepository.clearFixedAreaId(fixedAreaId);
        if(StringUtils.isNotBlank(customerIdStr)){
            //切割客户id字符串
            String[] idsArray = customerIdStr.split(",");
            for (String ids : idsArray) {
                int id = Integer.parseInt(ids);
                customerRepository.updateFixedAreaId(fixedAreaId,id);
            }
        }else{
            return;
        }
    }

    @Override
    public void register(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public Customer findByTelephone(String telephone) {
        return customerRepository.findByTelephone(telephone);
    }

    @Override
    public void updateType(String telephone) {
        customerRepository.updateType(telephone);
    }

    @Override
    public Customer login(String telephone, String password) {
        return customerRepository.findByTelephoneAndPassword(telephone,password);
    }

    @Override
    public String findFixedAreaIdByAddress(String address) {
        return customerRepository.findFixedAreaIdByAddress(address);
    }
}
