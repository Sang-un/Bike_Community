package bike.community.config.security;

import bike.community.config.cors.CorsConfig;
import bike.community.security.AuthTokenFilter;
import bike.community.security.CustomAuthenticationFilter;
import bike.community.security.CustomAuthenticationProvider;
import bike.community.security.CustomLoginSuccessHandler;
import bike.community.security.jwt.TokenUtils;
import bike.community.component.redis.RedisService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final RedisService redisService;
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;
    private final TokenUtils tokenUtils;
    private final CorsConfig corsConfig;

//            http
//                    .addFilter(corsConfig.corsFilter())
//                    .csrf().disable()
//    // 세션 사용 안 함
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .formLogin().disable()
//    // Basic: Authorization에 id, pw 넣어서 보냄
//    // Bearer: Authorization에 token 넣어서 보냄
//                .httpBasic().disable()
//
//                .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtTokenProvider, redisProvider))
//            .addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository, jwtTokenProvider, redisProvider))
//            .authorizeRequests()
//                .antMatchers("/api/auth/**")
//                .access("hasRole('ROLE_GENERAL') or hasRole('ROLE_STUDENT') or hasRole('ROLE_COUNCIL') or hasRole('ROLE_ADMIN') or hasRole('ROLE_DEVELOPER')")
//                .antMatchers("/api/auth-student/**")
//                .access("hasRole('ROLE_STUDENT') or hasRole('ROLE_COUNCIL') or hasRole('ROLE_ADMIN') or hasRole('ROLE_DEVELOPER')")
//                .antMatchers("/api/auth-council/**")
//                .access("hasRole('ROLE_COUNCIL') or hasRole('ROLE_ADMIN') or hasRole('ROLE_DEVELOPER')")
//                .antMatchers("/api/auth-admin/**")
//                .access("hasRole('ROLE_ADMIN') or hasRole('ROLE_DEVELOPER')")
//                .anyRequest().permitAll();
//        http.addFilterBefore(exceptionHandlerFilter, JwtAuthorizationFilter.class);

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .addFilter(corsConfig.corsFilter())
                .csrf().disable()
                .formLogin()
                .disable()
                .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/user/**")
                .access("hasRole('ROLE_USER')")
                .antMatchers("/admin/**")
                .access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll();


//                .and()
//                    .authorizeRequests()
//                    .anyRequest().permitAll();
    }

    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager(), objectMapper);
        customAuthenticationFilter.setFilterProcessesUrl("/login");
        customAuthenticationFilter.setAuthenticationSuccessHandler(customLoginSuccessHandler());
        customAuthenticationFilter.afterPropertiesSet();
        return customAuthenticationFilter;
    }

    @Bean
    public CustomLoginSuccessHandler customLoginSuccessHandler() {
        return new CustomLoginSuccessHandler(redisService, objectMapper);
    }

    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider(userDetailsService, passwordEncoder);
    }

    @Bean
    public AuthTokenFilter authTokenFilter() throws Exception {
        return new AuthTokenFilter(authenticationManager(), objectMapper, tokenUtils, userDetailsService);
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) {
        authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider());
    }
}
