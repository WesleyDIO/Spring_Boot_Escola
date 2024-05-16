package br.org.sesisenai.ava;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public enum Autorizacao implements GrantedAuthority {
    GET("Get"),
    POST("Post"),
    PUT("Put"),
    DELETE("Delete");

    private final String nome;

//    public static Autorizacao getAutorizacao(String nome){
//        return valueOf(nome.toUpperCase());
//    }

    @Override
    public String getAuthority() {
        return nome;
    }
}
