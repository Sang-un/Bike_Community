package bike.community.config.security;

import bike.community.config.cors.CorsConfig;
import bike.community.model.security.*;
import bike.community.model.security.jwt.TokenUtils;
import bike.community.component.redis.RedisService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

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

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .addFilter(corsConfig.corsFilter())
//                .addFilterBefore(headerFilter, corsFilter)
                .csrf().disable()
                .formLogin()
                .disable()
                .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/guest/**", "/api/join/**", "/api/logout", "/swagger").permitAll()
                .antMatchers("/api/user/**").access("hasRole('ROLE_USER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()

                .and()
                .apply(securityConfigurerAdapter());

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
        return new AuthTokenFilter(authenticationManager(), tokenUtils, userDetailsService);
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) {
        authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider());
    }


    private AuthTokenFilterConfig securityConfigurerAdapter() throws Exception {
        return new AuthTokenFilterConfig(authenticationManager(), objectMapper,tokenUtils,userDetailsService);
    }
}
