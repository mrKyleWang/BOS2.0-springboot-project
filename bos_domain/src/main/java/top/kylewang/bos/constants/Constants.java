package top.kylewang.bos.constants;

/**
 * 系统自定义常量类
 * @author Kyle.Wang
 * 2018/1/6 0006 14:49
 */
public class Constants {
    public static final String BOS_MANAGEMENT_HOST = "http://localhost:9001";
    public static final String CRM_MANAGEMENT_HOST = "http://localhost:9002";
    public static final String BOS_FORE_HOST = "http://localhost:9003";
    public static final String BOS_SMS_HOST = "http://localhost:9004";
    public static final String BOS_MAIL_HOST = "http://localhost:9005";

    private static final String BOS_MANAGEMENT_CONTEXT = "/bos";
    private static final String CRM_MANAGEMENT_CONTEXT = "/crm";
    private static final String BOS_FORE_CONTEXT = "/fore";
    private static final String BOS_SMS_CONTEXT = "/sms";
    private static final String BOS_MAIL_CONTEXT = "/mail";

    public static final String BOS_MANAGEMENT_URL = BOS_MANAGEMENT_HOST + BOS_MANAGEMENT_CONTEXT;
    public static final String CRM_MANAGEMENT_URL = CRM_MANAGEMENT_HOST + CRM_MANAGEMENT_CONTEXT;
    public static final String BOS_FORE_URL = BOS_FORE_HOST + BOS_FORE_CONTEXT;
    public static final String BOS_SMS_URL = BOS_SMS_HOST + BOS_SMS_CONTEXT;
    public static final String BOS_MAIL_URL = BOS_MAIL_HOST + BOS_MAIL_CONTEXT;
}
