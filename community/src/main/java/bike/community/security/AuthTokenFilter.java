package bike.community.security;

import bike.community.security.jwt.TokenUtils;
import bike.community.security.jwt.JwtProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class AuthTokenFilter extends BasicAuthenticationFilter {
    private final TokenUtils tokenUtils;
    private final UserDetailsService userDetailsService;

    public AuthTokenFilter(AuthenticationManager authenticationManager,
                           TokenUtils tokenUtils,
                           UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.tokenUtils = tokenUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
                String jwt = resolveToken(request);
                String requestURI = request.getRequestURI();

                if (StringUtils.hasText(jwt) && tokenUtils.isValidToken(jwt)) {
                    String email = tokenUtils.getEmailFromJwt(request);
                    UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(email);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.debug("YES TOKEN requestUri = {}", requestURI);
                }
                else log.debug("NO TOKEN requestUri = {}", requestURI);
                chain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(JwtProperties.AUTH_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtProperties.TOKEN_TYPE)) return bearerToken.substring(7);
        return null;
    }
}
