package tp.tacs.api.security;

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

        this.jwtTokenService
                .extractToken(httpServletRequest)
                .flatMap(this.jwtTokenService::validateToken)
                .ifPresent(SecurityContextHolder.getContext()::setAuthentication);

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}
