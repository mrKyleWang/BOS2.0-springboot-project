package top.kylewang.bos.web.controller;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import top.kylewang.bos.constants.Constants;
import top.kylewang.bos.utils.MailUtils;
import top.kylewang.crm.domain.Customer;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Kyle.Wang
 * 2018/1/2 0002 21:24
 */
@Controller
public class CustomerController{

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private List<JacksonJaxbJsonProvider> jsonProvider;

    /**
     * 发送短信验证码
     * @param telephone
     * @param request
     * @throws Exception
     */
    @RequestMapping("/customer_sendSms.action")
    public ResponseEntity sendSms(String telephone, HttpServletRequest request){
        try {
            // 生成短信验证码
            String randomCode = RandomStringUtils.randomNumeric(6);
            // 将短信验证码保存到session
            request.getSession().setAttribute(telephone,randomCode);
            System.out.println("短信验证码:"+randomCode);
            // 调用MQ服务, 发送条消息
            jmsTemplate.send("bos_sms", new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    MapMessage mapMessage = session.createMapMessage();
                    mapMessage.setString("telephone",telephone);
                    mapMessage.setString("msg",randomCode);
                    return mapMessage;
                }
            });
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * 客户注册
     * @param customer
     * @param checkCode
     * @param request
     * @param response
     * @throws IOException
     */

    @RequestMapping("/customer_register.action")
    public void register(Customer customer,String checkCode, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //校验短信验证码
        String sessionCheckCode = (String) request.getSession().getAttribute(customer.getTelephone());
        System.out.println("input:"+checkCode);
        System.out.println("session:"+sessionCheckCode);
        if(sessionCheckCode == null || ! sessionCheckCode.equals(checkCode)){
            //校验失败
            response.sendRedirect("signup-success.html");
        }
        //调用crm服务保存customer对象
        WebClient.create(Constants.CRM_MANAGEMENT_URL+"/services/customerService/customer",jsonProvider)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .post(customer);
        //生成激活码
        String activeCode = RandomStringUtils.randomNumeric(32);
        //将激活码保存到redis
        redisTemplate.opsForValue().set(customer.getTelephone(),activeCode,24, TimeUnit.HOURS);
        //发送激活邮件
        String content = "尊敬的客户您好, 请于24小时内, 进行邮箱账户的绑定, 点击下面地址完成绑定:" +
                "<br/><a href='"+ MailUtils.activeUrl+"?telephone="+customer.getTelephone()+"&activeCode="+activeCode+"'>速运快递邮箱绑定地址</a>";
        MailUtils.sendMail("速运快递激活邮件",content,customer.getEmail());
        response.sendRedirect("signup.html");
    }

    /**
     * 邮箱激活
     * @param telephone
     * @param activeCode
     * @param response
     * @throws IOException
     */
    @RequestMapping("/customer_activeMail.action")
    public void activeMail(String telephone,String activeCode,HttpServletResponse response) throws IOException {
        //判断激活码是否有效
        String redisActiveCode = redisTemplate.opsForValue().get(telephone);
        response.setContentType("text/html;charset=utf-8");
        if(redisActiveCode==null||!redisActiveCode.equals(activeCode)){
            //激活码无效
            response.getWriter().println("激活码无效,请登录系统,重新绑定邮箱!");
        }else{
            //激活码有效
            Customer customer = WebClient.create(Constants.CRM_MANAGEMENT_URL+"/services/customerService/customer/telephone/" + telephone,jsonProvider).
                    accept(MediaType.APPLICATION_JSON).get(Customer.class);
            if(customer.getType()==null||customer.getType()!=1){
                //未绑定
                //调用crm-webservice更新绑定状态
                WebClient.create(Constants.CRM_MANAGEMENT_URL+"/services/customerService/customer/updatetype/" + telephone,jsonProvider).put(null);
                response.getWriter().println("邮箱绑定成功!");
            }else{
                //已绑定
                response.getWriter().println("邮箱已绑定无需重复绑定!");
            }
            //删除redis中的激活码
            redisTemplate.delete(telephone);
        }
    }

    /**
     * 登录
     * @param customer
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/customer_login.action")
    public void login(Customer customer,HttpServletRequest request,HttpServletResponse response) throws IOException {
        Customer existCustomer = WebClient
                .create(Constants.CRM_MANAGEMENT_URL+"/services/customerService/customer/login?telephone="+customer.getTelephone()+"&password="+customer.getPassword(),jsonProvider)
                .accept(MediaType.APPLICATION_JSON).get(Customer.class);
        if(existCustomer==null){
            response.sendRedirect("login.html");
        }else {
            request.getSession().setAttribute("customer",existCustomer);
            response.sendRedirect("index.html#/myhome");
        }
    }
}
