package tp.tacs.api.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JWTTokenService jwtTokenService;

    public JWTAuthenticationFilter(JWTTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain) throws ServletException, IOException {

        var maybeTokenString = this.jwtTokenService.extractToken(httpServletRequest);
        var maybeAuthentication = maybeTokenString.flatMap(this.jwtTokenService::validateToken);

        if(maybeTokenString.isPresent() && maybeAuthentication.isEmpty()) {
            // El request tiene un token inválido o expirado
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        maybeAuthentication.ifPresent(authentication ->
                SecurityContextHolder.getContext().setAuthentication(authentication)
        );

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}
