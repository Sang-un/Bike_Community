package bike.community.model.security;

import bike.community.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("UserDetailsServiceImpl.loadUserByUsername");
        UserDetailsImpl userDetails = userRepository.findOptionalByEmail(email)
                .map(u -> new UserDetailsImpl(u, Collections.singleton(new SimpleGrantedAuthority(u.getRole().toString()))))
                .orElseThrow(() -> new UsernameNotFoundException(""));
        System.out.println(userDetails.getAuthorities());
        return userDetails;
    }
}
