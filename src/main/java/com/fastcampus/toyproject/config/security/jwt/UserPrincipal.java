package com.fastcampus.toyproject.config.security.jwt;

import com.fastcampus.toyproject.domain.user.dto.UserResponseDTO;
import com.fastcampus.toyproject.domain.user.entity.Authority;
import com.fastcampus.toyproject.domain.user.entity.User;
import io.jsonwebtoken.Claims;
import java.util.Collection;
import javax.security.auth.Subject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

@Getter
public class UserPrincipal extends AbstractAuthenticationToken {

    private Long userId;
    private String email;
    private String name;
    private Authority authority;

    /**
     * Creates a token with the supplied array of authorities.
     *
     * @param authorities the collection of <tt>GrantedAuthority</tt>s for the principal represented by
     *                    this authentication object.
     */
    public UserPrincipal(User user) {
        super(null);
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.authority = user.getAuthority();
        setAuthenticated(true);
    }

    public UserPrincipal(Claims claims, Collection<? extends GrantedAuthority> authorities){
        super(authorities);
        this.userId = ((Number) claims.get("userId")).longValue();
        this.email = String.valueOf(claims.get("email"));
        this.name = String.valueOf(claims.get("name"));
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return "";
    }

    @Override
    public Object getPrincipal() {
        return this.email;
    }

    @Override
    public boolean implies(Subject subject) {
        return super.implies(subject);
    }
}
