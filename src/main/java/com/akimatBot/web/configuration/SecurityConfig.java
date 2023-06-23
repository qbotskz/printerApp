//package com.akimatBot.web.configuration;
//
//
//import com.akimatBot.web.helpers.JWTTokenGenerator;
//import lombok.AllArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
////import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//
//import javax.sql.DataSource;
//import java.util.List;
//
//@Configuration
//@AllArgsConstructor
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    private final DataSource _db;
//    private final JWTTokenGenerator tokenGenerator;
////    private final BCryptPasswordEncoder passwordEncoder;
//
//
//    @Bean
//    public PasswordEncoder encoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//        String fetchUsersQuery = "select email, password, active " +
//                "as enabled from users " +
//                "where email = ?";
//        String fetchRolesQuery = "select email, role " +
//                "from users " +
//                "where email = ?";
//
//        auth.jdbcAuthentication()
//                .dataSource(_db)
//                .usersByUsernameQuery(fetchUsersQuery)
//                .authoritiesByUsernameQuery(fetchRolesQuery);
//    }
//
//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        com.example.demo.filters.AuthenticationFilter authenticationFilter = new com.example.demo.filters.AuthenticationFilter(tokenGenerator,
//                authenticationManagerBean());
//        authenticationFilter.setFilterProcessesUrl("api/users/login");
////        authenticationFilter.setFilterProcessesUrl("api/users/login");
//        httpSecurity
//                .csrf()
//                .disable()
//                .cors()
//                .configurationSource(request -> {
//                    CorsConfiguration configuration = new CorsConfiguration();
//                    configuration.setAllowedMethods(List.of("GET","POST", "PUT", "DELETE", "OPTIONS"));
//                    configuration.setAllowedHeaders(List.of("*"));
//                    return configuration;
//                });
//
//        httpSecurity
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        httpSecurity.addFilter(authenticationFilter);
//        httpSecurity.addFilterAfter(new com.example.demo.filters.AuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
//    }
//
//}
//
//
//
