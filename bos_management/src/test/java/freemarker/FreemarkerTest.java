package freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kyle.Wang
 * 2018/1/6 0006 15:50
 */

public class FreemarkerTest {


    @Test
    public void testOutput() throws Exception {
        // 配置对象, 配置模板位置
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_22);
        configuration.setDirectoryForTemplateLoading(new File("src/main/webapp/WEB-INF/templates"));

        // 获取模板对象
        Template template = configuration.getTemplate("hello.ftl");

        // 动态数据对象
        Map<String,Object> parameterMap = new HashMap<>();
        parameterMap.put("title","我的模板");
        parameterMap.put("msg","你好,这是我的第一个Freemarker案例");

        // 合并输出
        template.process(parameterMap,new FileWriter(new File("src/main/webapp/WEB-INF/templates/out/hello.html")));
    }
}
