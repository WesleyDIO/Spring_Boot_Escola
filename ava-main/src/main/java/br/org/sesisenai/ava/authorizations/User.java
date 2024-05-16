package br.org.sesisenai.ava.authorizations;

import br.org.sesisenai.ava.Autorizacao;
import br.org.sesisenai.ava.UserDetailsEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Component
@AllArgsConstructor
public class User implements AuthorizationManager<RequestAuthorizationContext> {
    @Override
    public void verify(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        AuthorizationManager.super.verify(authentication, object);
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        Map<String, String> variables = object.getVariables();
        Long userID = Long.parseLong(variables.get("id"));
        String request = object.getRequest().getMethod();
        boolean decision = false;

        UserDetailsEntity userDetailsEntity = (UserDetailsEntity) authentication.get().getPrincipal();
        if(userDetailsEntity.getUsuario().getId() == userID && contemAutorizacao(request, userDetailsEntity)){ //&& userDetailsEntity.getAuthorities().contains(Autorizacao.GET)
            decision = true;
        }
        return new AuthorizationDecision(decision);
    }

    private boolean contemAutorizacao(String request, UserDetailsEntity userDetailsEntity){
        Map<String, Boolean> allRequest = new HashMap<>();
        allRequest.put("GET", userDetailsEntity.getAuthorities().contains(Autorizacao.GET));
        allRequest.put("PUT", userDetailsEntity.getAuthorities().contains(Autorizacao.PUT));

        return  allRequest.get(request);

    }
}
