package top.kylewang.bos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

/**
 * @author Kyle.Wang
 * 2018/1/15 0015 11:59
 */

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
public class BosSmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BosSmsApplication.class, args);
	}
}
