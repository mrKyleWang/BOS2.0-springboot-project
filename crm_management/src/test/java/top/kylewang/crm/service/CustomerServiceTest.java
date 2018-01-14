package top.kylewang.crm.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.kylewang.crm.domain.Customer;

import java.util.List;

/**
 * @author Kyle.Wang
 * 2017/12/31 0031 17:02
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Test
    public void findNoAssociationCustomers() {
        List<Customer> noAssociationCustomers = customerService.findNoAssociationCustomers();
        System.out.println(noAssociationCustomers);
    }

    @Test
    public void findAssociationFixedAreaCustomers() {
    }

    @Test
    public void associationCustomersToFixedArea() {
    }
}