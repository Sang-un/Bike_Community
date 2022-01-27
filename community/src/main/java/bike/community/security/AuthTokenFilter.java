package bike.community.security;

import bike.community.model.RespDto;
import bike.community.model.network.Header;
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

import static bike.community.security.jwt.JwtProperties.AUTH_HEADER;
import static bike.community.security.jwt.JwtProperties.TOKEN_TYPE;

//TODO /join일 때 시큐리티 필터 타는 문제 해결하기 SecurityConfig수정하고, FilterConfig 추가해놈. 이거 확인하기
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
                filterChain.doFilter(request, response);
            }
            else tokenError(response);

        } catch (Exception e) {
            tokenError(response, "오류가 발생했습니다. 다시 로그인 해주세요");
        }
    }


    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader(AUTH_HEADER);
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(TOKEN_TYPE + " "))
            return headerAuth.substring(7);
        return null;
    }

    private void tokenError(HttpServletResponse response) throws IOException {
        Header<Object> error = Header.ERROR("세션 시간이 경과 되었습니다. 다시 로그인 해주세요");
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
