package com.example.securitydemo;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    DataSource dataSource;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        ///this is to remove the authentication for the h2 database login poge
        http.authorizeHttpRequests(requests -> requests.requestMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated());

         //Form-based login (the /login page). Uncomment to enable it again, for testing.
         //http.formLogin(Customizer.withDefaults());

        http.httpBasic(Customizer.withDefaults());                 // HTTP Basic authentication (active)
        ///this is to hide the cookies
        http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // optional
        http.csrf(csrf -> csrf.disable());                         // optional, for Postman testing
        /// For getting the h2 database frame come up.
        http.headers(headers->
                headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));
        return http.build();
    }

    ///This is to create in-memory Authentication
    @Bean
    public UserDetailsService userDetailsService(){

        UserDetails user1 = User.withUsername("user1")
                .password(encoder().encode("password1"))
                .roles("USER")
                .build();
        UserDetails user2 = User.withUsername("admin1")
                .password(encoder().encode("password2"))
                .roles("ADMIN")
                .build();

        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
        userDetailsManager.createUser(user1);
        userDetailsManager.createUser(user2);
        return userDetailsManager;
        //return new InMemoryUserDetailsManager(user1,user2);
    }

    @Bean
    public PasswordEncoder encoder (){
        return new BCryptPasswordEncoder();
    }
}
