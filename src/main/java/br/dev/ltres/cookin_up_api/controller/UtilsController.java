package br.dev.ltres.cookin_up_api.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/utils")
public class UtilsController {

    @GetMapping("usuario")
    public String getUsuario(Authentication authentication) {
        return authentication.getName();
    }

    @GetMapping("token")
    public String getConteudoToken(Authentication authentication) {
        if (authentication == null || !(authentication instanceof JwtAuthenticationToken)) {
            return "Principal não é um token JWT";
        }

        var jwt = ((JwtAuthenticationToken) authentication).getToken();
        var strBuild = new StringBuilder();

        strBuild.append("Nome: ").append(jwt.getClaimAsString("name"))
                .append("\ne-mail: ").append(jwt.getClaimAsString("email"))
                .append("\noid: ").append(jwt.getClaimAsString("oid"))
                .append("\nexpire at: ").append(jwt.getExpiresAt());

        return strBuild.toString();
    }
}
