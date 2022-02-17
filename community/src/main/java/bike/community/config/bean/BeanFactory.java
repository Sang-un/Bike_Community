package bike.community.config.bean;

import bike.community.component.aop.log.LogTrace;
import bike.community.component.aop.log.LogTraceAspect;
import bike.community.component.aop.log.ThreadLocalLogTrace;
import bike.community.security.HeaderFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class BeanFactory {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();}

    @Bean
    public LogTrace logTrace(){return new ThreadLocalLogTrace();}

    @Bean
    public LogTraceAspect logTraceAspect(LogTrace logTrace){return new LogTraceAspect(logTrace);}

    @Bean
    public HeaderFilter headerFilter(){
        return new HeaderFilter();
    }
}
