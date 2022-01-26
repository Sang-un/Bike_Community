package bike.community.security;

import bike.community.model.RespDto;
import bike.community.security.jwt.AuthConstants;
import bike.community.security.jwt.TokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@RequiredArgsConstructor
@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;
    private final TokenUtils tokenUtils;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null && tokenUtils.isValidToken(jwt)) {
                String email = tokenUtils.get(jwt, "email");
                System.out.println(email);
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            else tokenError(response);

        } catch (Exception e) {
            log.error("Cannot set user authentication: {}", e);
        }
        filterChain.doFilter(request, response);
    }


    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader(AuthConstants.AUTH_HEADER);
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(AuthConstants.TOKEN_TYPE + " "))
            return headerAuth.substring(7, headerAuth.length());
        return null;
    }

    private void tokenError(HttpServletResponse response) throws IOException {
        RespDto<String> cmRespDto = new RespDto<>(401, "login plz~", null);
        String cmRespDtoJson = objectMapper.writeValueAsString(cmRespDto);
        PrintWriter out = response.getWriter();
        out.print(cmRespDtoJson);
        out.flush();
    }
}
