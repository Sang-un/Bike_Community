package bike.community.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("CustomAuthenticationProvider.authenticate");
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(token.getName());

        // TODO 원하는 JSON ERROR 던지도록 수정하기
        if (userDetails == null) throw new UsernameNotFoundException(userDetails.getUsername()+"Invalid password");
        if (!passwordEncoder.matches((String) token.getCredentials(), userDetails.getPassword())) throw new BadCredentialsException(userDetails.getUsername()+"Invalid password");

        return new UsernamePasswordAuthenticationToken(userDetails, token.getCredentials(), userDetails.getAuthorities());
    }

    @Override public boolean supports(Class<?> authentication) {
        System.out.println("CustomAuthenticationProvider.supports");
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
