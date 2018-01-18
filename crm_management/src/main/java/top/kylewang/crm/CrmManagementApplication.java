package top.kylewang.crm;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author Kyle.Wang
 * 2018/1/15 0015 11:59
 */

@SpringBootApplication
public class CrmManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrmManagementApplication.class, args);
	}

	@Bean
	public JacksonJaxbJsonProvider jacksonJaxbJsonProvider() {
		return new JacksonJaxbJsonProvider();
	}
}
