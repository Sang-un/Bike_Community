package bike.community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing /* 얘가 datebase를 작동시켜준다. 삭제 노노*/
@SpringBootApplication
public class CommunityApplication {

	private static final String PROPERTIES = "spring.config.location=classpath:/application.yml,classpath:/application-aws.yml";

	public static void main(String[] args) {
		new SpringApplicationBuilder(CommunityApplication.class)
				.properties(PROPERTIES)
				.run(args);
	}
}
