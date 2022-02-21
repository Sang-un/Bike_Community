package bike.community.model.security;

import bike.community.model.entity.user.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@RequiredArgsConstructor
@Getter
public class UserDetailsImpl implements UserDetails {
    private final User user;
    private final Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.getIsEnable();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getIsEnable();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getIsEnable();
    }

    @Override
    public boolean isEnabled() {
        return user.getIsEnable();
    }

    public String getNickname() {
        return user.getNickname();
    }

    public String getRole() {
        return user.getRole().toString();
    }

    public String getAddress() {
        if(user.getAddress()!=null) return user.getAddress().getAddress();
        else return "주소";

    }

    public String getDetailAddress() {
        if(user.getAddress()!=null) return user.getAddress().getDetailAddress();
        else return "주소";
    }

    public String getZipcode() {
        if(user.getAddress()!=null) return user.getAddress().getZipcode();
        else return "우편번호";
    }

    public String getSex() {
        return user.getSex();
    }

    public String getBirthday() {
        return user.getBirthday();
    }

    public String getRealUsername() {
        return user.getUsername();
    }

    public String getPhone() {
        return user.getPhone();
    }
}
