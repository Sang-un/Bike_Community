package bike.community.security;

import bike.community.model.network.Header;
import bike.community.model.network.request.user.JoinUserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper om;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper){
        super.setAuthenticationManager(authenticationManager);
        this.om = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        JoinUserRequest userInfo = getUserInfo(request, response);
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userInfo.getEmail(), userInfo.getPassword());
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private JoinUserRequest getUserInfo(HttpServletRequest request, HttpServletResponse response){
        JoinUserRequest joinUser = JoinUserRequest.builder().build();
        try {
            ServletInputStream ins = request.getInputStream();
            String json = StreamUtils.copyToString(ins, StandardCharsets.UTF_8);
            joinUser = om.readValue(json, JoinUserRequest.class);
        } catch (IOException e) {
            responseError(response);
        }
        return joinUser;
    }

    private void responseError(HttpServletResponse response) {
        log.error("[심각] ObjectMapper 작동 ERROR");
        Header<Object> error = Header.ERROR();
        String errorStr = "";
        PrintWriter out = null;
        try {
            errorStr = om.writeValueAsString(error);
            out = response.getWriter();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        out.print(errorStr);
        out.flush();
    }
}
