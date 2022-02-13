package bike.community.security;

import bike.community.model.network.Header;
import bike.community.security.jwt.TokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static bike.community.security.jwt.JwtProperties.AUTH_HEADER;
import static bike.community.security.jwt.JwtProperties.TOKEN_TYPE;

@Slf4j
public class AuthTokenFilter extends BasicAuthenticationFilter {
    private final ObjectMapper objectMapper;
    private final TokenUtils tokenUtils;
    private final UserDetailsService userDetailsService;
    private static final String[] NO_SECURITY_PATH = {"/api/guest*", "/api/join*", "/api/logout*"};

    public AuthTokenFilter(AuthenticationManager authenticationManager,
                           ObjectMapper objectMapper,
                           TokenUtils tokenUtils,
                           UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.objectMapper = objectMapper;
        this.tokenUtils = tokenUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            if(PatternMatchUtils.simpleMatch(NO_SECURITY_PATH, request.getRequestURI())) chain.doFilter(request, response);
            else{
                String jwt = parseJwt(request);
                if (jwt != null && tokenUtils.isValidToken(jwt)) {
                    String email = tokenUtils.getEmailFromJwt(jwt);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    chain.doFilter(request, response);
                }
                else tokenError(response);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader(AUTH_HEADER);
        String substring = headerAuth.substring(7);
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(TOKEN_TYPE)) return headerAuth.substring(7);
        return null;
    }

    private void tokenError(HttpServletResponse response) throws IOException {
        Header<Object> error = Header.ERROR("This user have no token. login please.");
        String errorStr = objectMapper.writeValueAsString(error);
        PrintWriter out = response.getWriter();
        out.print(errorStr);
        out.flush();
    }

    private void tokenError(HttpServletResponse response, String message) throws IOException {
        Header<Object> error = Header.ERROR(message);
        String errorStr = objectMapper.writeValueAsString(error);
        PrintWriter out = response.getWriter();
        out.print(errorStr);
        out.flush();
    }
}
