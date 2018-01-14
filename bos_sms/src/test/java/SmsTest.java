import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.kylewang.bos.utils.SmsUtils;

/**
 * @author Kyle.Wang
 * 2018/1/3 0003 9:37
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-mq-consumer.xml")
public class SmsTest {


    @Test
    public void sendSmsTest() throws Exception {

        //生成短信验证码
        String randomCode = RandomStringUtils.randomNumeric(6);
        String mobile = "13177171249";
        System.out.println(randomCode);
        String responseCode = SmsUtils.sendCode(mobile, randomCode);
        System.out.println(responseCode);
    }
}
