package top.kylewang.bos;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Kyle.Wang
 * 2018/1/15 0015 11:59
 */

@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = "top.kylewang.bos.index")
@EnableJpaRepositories(basePackages = "top.kylewang.bos.dao")
public class BosManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(BosManagementApplication.class, args);
	}

	@Bean
	public JacksonJaxbJsonProvider jacksonJaxbJsonProvider() {
		return new JacksonJaxbJsonProvider();
	}
}
