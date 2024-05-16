package br.org.sesisenai.ava;

import br.org.sesisenai.ava.entity.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jdk.jfr.Name;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class UserDetailsEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(mappedBy = "usuarioDetailsEntity")
    @NonNull
    @ToString.Exclude
    private Usuario usuario;
    private boolean enabled;
    @Column(unique = true, nullable = false, updatable = false)
    private String username;
    @Column(nullable = false)
    @Length(min = 6)
    private String password;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private Collection<Autorizacao> authorities;

}
