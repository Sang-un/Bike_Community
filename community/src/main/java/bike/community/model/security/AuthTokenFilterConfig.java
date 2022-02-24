package bike.community.model.security;

import bike.community.model.security.jwt.TokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class AuthTokenFilterConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final ObjectMapper objectMapper;
    private final TokenUtils tokenUtils;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    public AuthTokenFilterConfig(AuthenticationManager authenticationManager,
                           ObjectMapper objectMapper,
                           TokenUtils tokenUtils,
                           UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.objectMapper = objectMapper;
        this.tokenUtils = tokenUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void configure(HttpSecurity http) {
        AuthTokenFilter customFilter = new AuthTokenFilter(authenticationManager, objectMapper,tokenUtils,userDetailsService);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
