package top.kylewang.bos.mq;

import org.springframework.stereotype.Service;
import top.kylewang.bos.utils.SmsUtils;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * @author Kyle.Wang
 * 2018/1/4 0004 15:34
 */
@Service
public class SmsConsumer implements MessageListener{
    @Override
    public void onMessage(Message message) {
        MapMessage mapMessage = (MapMessage) message;
        try {
            String mobile = mapMessage.getString("telephone");
            String code = mapMessage.getString("msg");

            //调用SMS服务发送短信 TODO 替换为SMS服务
//            String responseCode = SmsUtils.sendCode(mobile, code);
            String responseCode = "000000";
            if(responseCode.equals(SmsUtils.SEND_SUCCESS)){
                //发送成功
                System.out.println("短信发送成功,手机号:"+mobile+",验证码:"+code);

            }else{
                throw new RuntimeException("短信发送失败,信息码:"+responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
