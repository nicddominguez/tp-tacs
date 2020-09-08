package tp.tacs.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JWTTokenService jwtTokenService;

    @Autowired
    public SecurityConfiguration(JWTTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests().antMatchers("/auth/**").permitAll()
                .and().authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN")
                .and().authorizeRequests()
                .anyRequest().authenticated()
                .and().addFilterBefore(
                    new JWTAuthenticationFilter(this.jwtTokenService),
                    UsernamePasswordAuthenticationFilter.class);
    }

}
