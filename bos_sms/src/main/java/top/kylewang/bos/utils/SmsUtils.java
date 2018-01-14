package top.kylewang.bos.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * 云之讯sms短信服务工具类
 * @author Kyle.Wang
 * 2018/1/3 0003 9:26
 */
public class SmsUtils {

    public static final String SEND_SUCCESS = "000000";

    /**
     * 请求路径
     */
    private static final String url = "https://open.ucpaas.com/ol/sms/sendsms";
    /**
     * 账户sid
     */
    private final String sid = "2bfd07a0104c089f794169961394aa7a";

    /**
     * 用户密钥AuthToken
     */
    private final String token = "8b9487095057a57ccb6e28bd33caa4d5";

    /**
     * 应用唯一标示appid
     */
    private final String appid = "a2f7797aa0eb4dbc93bc78f04d90a8f0";

    /**
     * 短信模板id
     */
    private final String templateid="261510";

    /**
     * 模板中的可替换参数
     */
    private String param;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 发送短信验证码的方法
     * @param mobile 传入手机号
     * @param param 传入验证码
     * @return 返回code "000000"代表成功
     * @throws Exception
     */
    public static String sendCode(String mobile,String param) throws Exception {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        SmsUtils smsUtils = new SmsUtils();
        smsUtils.setMobile(mobile);
        smsUtils.setParam(param);
        try {
            StringEntity stringEntity = new StringEntity(JSONObject.toJSONString(smsUtils),"utf-8");
            System.out.println(JSONObject.toJSONString(smsUtils));
            httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
            httpPost.setEntity(stringEntity);
            HttpResponse res = httpClient.execute(httpPost);
            if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                String resJsonString = EntityUtils.toString(res.getEntity(), "utf-8");
                System.out.println(resJsonString);
                SmsResponseBean responseBean = JSONObject.parseObject(resJsonString, SmsResponseBean.class);
                return responseBean.getCode();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getSid() {
        return sid;
    }

    public String getToken() {
        return token;
    }

    public String getAppid() {
        return appid;
    }

    public String getTemplateid() {
        return templateid;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}

/**
 * 云之讯sms响应结果bean
 */
class SmsResponseBean{

    private String code;
    private String count;
    private String create_date;
    private String mobile;
    private String msg;
    private String smsid;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSmsid() {
        return smsid;
    }

    public void setSmsid(String smsid) {
        this.smsid = smsid;
    }
}