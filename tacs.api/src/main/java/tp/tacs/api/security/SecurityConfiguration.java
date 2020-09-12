package tp.tacs.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${security.rutas.permitidas}")
    private String[] rutasPermitidas;

    @Value("${security.rutas.administrativas}")
    private String[] rutasAdministrativas;

    private final JWTTokenService jwtTokenService;

    @Autowired
    public SecurityConfiguration(JWTTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // Don't do this in production, use a proper list  of allowed origins
        config.setAllowedOrigins(Collections.singletonList("*"));
        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests().antMatchers(rutasPermitidas).permitAll()
                .antMatchers("/**/*.{js,html,css}", "/", "/app", "/login").permitAll()
                .and().authorizeRequests().antMatchers(rutasAdministrativas).hasRole("ADMIN")
                .and().authorizeRequests()
                .anyRequest().authenticated()
                .and().addFilterBefore(
                    new JWTAuthenticationFilter(this.jwtTokenService),
                    UsernamePasswordAuthenticationFilter.class);
    }

}
