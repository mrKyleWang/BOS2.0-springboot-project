import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.kylewang.bos.utils.SmsUtils;

/**
 * @author Kyle.Wang
 * 2018/1/3 0003 9:37
 */
@SpringBootTest
public class SmsTest {


    @Test
    public void sendSmsTest() throws Exception {

        //生成短信验证码
        String randomCode= (int)((Math.random()*9+1)*100000)+"";
        System.out.println(randomCode);
        String mobile = "13657249231";
        String responseCode = SmsUtils.sendCode(mobile, randomCode);
        System.out.println(responseCode);
    }
}
