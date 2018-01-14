package top.kylewang.bos.web.action;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import top.kylewang.bos.constants.Constants;
import top.kylewang.bos.utils.MailUtils;
import top.kylewang.bos.web.action.common.BaseAction;
import top.kylewang.crm.domain.Customer;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author Kyle.Wang
 * 2018/1/2 0002 21:24
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("json-default")
public class CustomerAction extends BaseAction<Customer>{

    @Autowired
    @Qualifier("jmsQueueTemplate")
    private JmsTemplate jmsTemplate;

    /**
     * 发送短信验证码
     * @return
     * @throws Exception
     */
    @Action(value = "customer_sendSms")
    public String sendSms() throws Exception {
        // 手机号存在Customer对象
        String mobile = model.getTelephone();
        // 生成短信验证码
        String randomCode = RandomStringUtils.randomNumeric(6);
        // 将短信验证码保存到session
        ServletActionContext.getRequest().getSession().setAttribute(mobile,randomCode);
        System.out.println("短信验证码:"+randomCode);
        // 调用MQ服务, 发送条消息
        jmsTemplate.send("bos_sms", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage mapMessage = session.createMapMessage();
                mapMessage.setString("telephone",model.getTelephone());
                mapMessage.setString("msg",randomCode);
                return mapMessage;
            }
        });
        return NONE;
    }

    private String checkCode;
    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 客户注册
     * @return
     */
    @Action(value = "customer_register",
            results = {@Result(name = "success",location = "signup-success.html",type = "redirect"),
                    @Result(name = "input",location = "signup.html",type = "redirect")})
    public String register(){
        //校验短信验证码
        String sessionCheckCode = (String) ServletActionContext.getRequest().getSession().getAttribute(model.getTelephone());
        System.out.println("input:"+checkCode);
        System.out.println("session:"+sessionCheckCode);
        if(sessionCheckCode == null || ! sessionCheckCode.equals(checkCode)){
            //校验失败
            return INPUT;
        }
        //调用crm服务保存customer对象
        WebClient.create(Constants.CRM_MANAGEMENT_URL+"/services/customerService/customer")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .post(model);
        //生成激活码
        String activeCode = RandomStringUtils.randomNumeric(32);
        //将激活码保存到redis
        redisTemplate.opsForValue().set(model.getTelephone(),activeCode,24, TimeUnit.HOURS);
        //发送激活邮件
        String content = "尊敬的客户您好, 请于24小时内, 进行邮箱账户的绑定, 点击下面地址完成绑定:" +
                "<br/><a href='"+ MailUtils.activeUrl+"?telephone="+model.getTelephone()+"&activeCode="+activeCode+"'>速运快递邮箱绑定地址</a>";
        MailUtils.sendMail("速运快递激活邮件",content,model.getEmail());
        return SUCCESS;
    }


    private String activeCode;

    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
    }

    /**
     * 邮箱激活
     * @return
     */
    @Action(value = "customer_activeMail")
    public String activeMail() throws IOException {
        //判断激活码是否有效
        String redisActiveCode = redisTemplate.opsForValue().get(model.getTelephone());
        ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
        if(redisActiveCode==null||!redisActiveCode.equals(activeCode)){
            //激活码无效
            ServletActionContext.getResponse().getWriter().println("激活码无效,请登录系统,重新绑定邮箱!");
        }else{
            //激活码有效
            Customer customer = WebClient.create(Constants.CRM_MANAGEMENT_URL+"/services/customerService/customer/telephone/" + model.getTelephone()).
                    accept(MediaType.APPLICATION_JSON).get(Customer.class);
            if(customer.getType()==null||customer.getType()!=1){
                //未绑定
                //调用crm-webservice更新绑定状态
                WebClient.create(Constants.CRM_MANAGEMENT_URL+"/services/customerService/customer/updatetype/" + model.getTelephone()).put(null);
                ServletActionContext.getResponse().getWriter().println("邮箱绑定成功!");
            }else{
                //已绑定
                ServletActionContext.getResponse().getWriter().println("邮箱已绑定无需重复绑定!");
            }
            //删除redis中的激活码
            redisTemplate.delete(model.getTelephone());
        }
        return NONE;
    }


    @Action(value = "customer_login",
            results = {@Result(name = "success",location = "index.html#/myhome",type = "redirect"),
                    @Result(name = "login",location = "login.html",type = "redirect")})
    public String login(){
        Customer customer = WebClient
                .create(Constants.CRM_MANAGEMENT_URL+"/services/customerService/customer/login?telephone="+model.getTelephone()+"&password="+model.getPassword())
                .accept(MediaType.APPLICATION_JSON).get(Customer.class);
        if(customer==null){
            return LOGIN;
        }else {
            ServletActionContext.getRequest().getSession().setAttribute("customer",customer);
            return SUCCESS;
        }
    }
}
