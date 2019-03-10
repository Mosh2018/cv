package com.netum.cv.backend.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.netum.cv.backend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class AppUser implements UserDetails {

    private String username;

    @JsonIgnore
    private String password;

    private String email;

    @JsonIgnore
    private String securityKey;

    @JsonIgnore
    private Collection<? extends GrantedAuthority> authorities;

    public static AppUser create(User user) {
        Collection<GrantedAuthority> authorities = user
                .getRoles()
                .stream()
                .map( role ->
                        new SimpleGrantedAuthority(role.getRoleName().name()))
                .collect(Collectors.toList());

        return new AppUser(
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getSecurityKey(),
                authorities);
    }

    public List<String> getRoles() {
                        return this.authorities.stream().map(
                                role -> role.getAuthority()
                        ).collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUser appUser = (AppUser) o;
        return Objects.equals(username, appUser.username) &&
                Objects.equals(password, appUser.password) &&
                Objects.equals(email, appUser.email) &&
                Objects.equals(securityKey, appUser.securityKey) &&
                Objects.equals(authorities, appUser.authorities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, email, securityKey, authorities);
    }
}
