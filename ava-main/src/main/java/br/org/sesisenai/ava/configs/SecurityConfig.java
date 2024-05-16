package br.org.sesisenai.ava.configs;

import br.org.sesisenai.ava.authorizations.User;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final SecurityContextRepository securityContextRepository;
    private User user;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.authorizeHttpRequests(authorizeRequests ->
                authorizeRequests.requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/cursos/").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/cursos/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/instrutor").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/usuarios/{id}").access(user)
                        .requestMatchers(HttpMethod.PUT, "/api/usuarios/{id}").access(user)
                        .requestMatchers(HttpMethod.DELETE, "/api/usuarios/{id}").access(user)
                        .requestMatchers(HttpMethod.PATCH, "/api/usuarios/{id}/senha").access(user)
                        .anyRequest().authenticated()
                );
        httpSecurity.securityContext((context) -> context .securityContextRepository(securityContextRepository));
        httpSecurity.formLogin(Customizer.withDefaults());
        httpSecurity.logout(Customizer.withDefaults());
        httpSecurity.sessionManagement(Customizer.withDefaults());
        return httpSecurity.build();
    }
}
