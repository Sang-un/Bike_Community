package bike.community;

import bike.community.aop.log.LogTrace;
import bike.community.aop.log.LogTraceAspect;
import bike.community.aop.log.ThreadLocalLogTrace;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class CommunityApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommunityApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public LogTrace logTrace(){
		return new ThreadLocalLogTrace();
	}

	@Bean
	public LogTraceAspect logTraceAspect(LogTrace logTrace){
		return new LogTraceAspect(logTrace);
	}
}
