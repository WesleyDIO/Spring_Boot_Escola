package br.org.sesisenai.ava.entity;

import br.org.sesisenai.ava.Autorizacao;
import br.org.sesisenai.ava.UserDetailsEntity;
import br.org.sesisenai.ava.dto.abstraction.ResponseConversorDTO;
import br.org.sesisenai.ava.dto.implementation.usuario.UsuarioResponseDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements ResponseConversorDTO<UsuarioResponseDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String senha;

    public void setUsuarioDetailsEntity(UserDetailsEntity usuarioDetailsEntity) {
        this.usuarioDetailsEntity = UserDetailsEntity
                .builder()
                .usuario(this)
                .accountNonExpired(true)
                .password(this.senha)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .username(this.email)
                .enabled(true)
                .authorities(List.of(Autorizacao.GET,Autorizacao.PUT,Autorizacao.DELETE,Autorizacao.POST))
                .build() ;
    }

    public void setSenha(String senha) {
       this.senha = new BCryptPasswordEncoder().encode(senha);
    }

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private Set<Certificado> certificados;

    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private Set<Inscricao> inscricoes;
    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private UserDetailsEntity usuarioDetailsEntity;
    public UsuarioResponseDTO toDTO() {
        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();
        usuarioResponseDTO.setId(this.id);
        usuarioResponseDTO.setNome(this.nome);
        usuarioResponseDTO.setEmail(this.email);
        usuarioResponseDTO.setDataCadastro(this.dataCadastro);
        return usuarioResponseDTO;
    }

    public Usuario(Long id) {
        this.id = id;
    }
}










