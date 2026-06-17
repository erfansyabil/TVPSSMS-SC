package config;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String role = authentication.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .findFirst()
                .orElse("");

        String redirectUrl = "/";
        if (role.equals("ROLE_ADMIN")) {
            redirectUrl = "/users/admin";
        } else if (role.equals("ROLE_TEACHER")) {
            redirectUrl = "/users/teacher";
        } else if (role.equals("ROLE_STUDENT")) {
            redirectUrl = "/users/student";
        }

        response.sendRedirect(redirectUrl);
    }
}
