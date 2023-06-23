package com.akimatBot.web.configuration;

import com.akimatBot.web.security.jwt.JwtConfigurer;
import com.akimatBot.web.security.jwt.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
        import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

/**
 * Security configuration class for JWT based Spring Security application.
 *
 * @author Eugene Suleimanov
 * @version 1.0
 */

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity

public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    private static final String ADMIN_ENDPOINT = "/api/v1/admin/**";
    private static final String LOGIN_ENDPOINT = "/api/auth/login";
    private static final String PRINTER_ENDPOINT = "/api/websocket/**";
    private static final String MOBILE_WAITER_ENDPOINT = "/api/waiter/**";
    private static final String MOBILE_DESK_ENDPOINT = "/api/desk/**";
    private static final String MOBILE_HALL_ENDPOINT = "/api/hall/**";
    private static final String MOBILE_MAIN_ENDPOINT = "/api/main/**";

    private static final String PRINTER_CONNECTION_ENDPOINT = "/api/printer/**";


    @Autowired
    public SecurityConfiguration(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .configurationSource(request ->
                        new CorsConfiguration().applyPermitDefaultValues());

        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(LOGIN_ENDPOINT).permitAll()
                .antMatchers(MOBILE_WAITER_ENDPOINT).permitAll()
                .antMatchers(MOBILE_HALL_ENDPOINT).permitAll()
                .antMatchers(MOBILE_DESK_ENDPOINT).permitAll()
                .antMatchers(MOBILE_MAIN_ENDPOINT).permitAll()
                .antMatchers(PRINTER_ENDPOINT).permitAll()
                .antMatchers(PRINTER_CONNECTION_ENDPOINT).permitAll()
                .antMatchers(ADMIN_ENDPOINT).hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));





    }

}
