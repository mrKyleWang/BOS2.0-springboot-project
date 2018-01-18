package top.kylewang.bos.utils;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * cxf json转换provider
 *
 * @author Kyle.Wang
 * 2018/1/18 0018 12:47
 */
@Component
public class JacksonJaxbJsonProviderList extends ArrayList<JacksonJaxbJsonProvider> {

    public JacksonJaxbJsonProviderList(){
        this.add(new JacksonJaxbJsonProvider());
    }
}
