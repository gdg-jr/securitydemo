package com.example.securitydemo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests -> requests.anyRequest().authenticated());

         //Form-based login (the /login page). Uncomment to enable it again, for testing.
         //http.formLogin(Customizer.withDefaults());

        http.httpBasic(Customizer.withDefaults());                 // HTTP Basic authentication (active)
        ///this is to hide the cookies
        http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // optional
        http.csrf(csrf -> csrf.disable());                         // optional, for Postman testing
        return http.build();
    }

    ///This is to create in-memory Authentication
    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user1 = User.withUsername("user1").password("{noop}password1").roles("USER").build();
        UserDetails user2 = User.withUsername("admin1").password("{noop}password2").roles("ADMIN").build();
        return new InMemoryUserDetailsManager(user1,user2);
    }
}
