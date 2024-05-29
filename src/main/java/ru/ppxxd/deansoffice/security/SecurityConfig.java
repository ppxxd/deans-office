package ru.ppxxd.deansoffice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers("/assessments/**").hasAnyRole("TEACHER", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/chairs/find/**").hasAnyRole("STUDENT", "TEACHER", "ADMIN")
                                .requestMatchers("/chairs").hasAnyRole("TEACHER", "ADMIN")
                                .requestMatchers("/chairs/**").hasRole("ADMIN")
                                .requestMatchers("/groups/find/**").hasAnyRole("STUDENT", "TEACHER", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/groups").hasAnyRole("STUDENT", "TEACHER", "ADMIN")
                                .requestMatchers("/groups/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/positions").hasAnyRole("TEACHER", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/students/find/**").hasAnyRole("STUDENT", "TEACHER", "ADMIN")
                                .requestMatchers("/students/**").hasRole("ADMIN")
                                .requestMatchers("/subjects/**").hasAnyRole("TEACHER", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/subjects").hasAnyRole("TEACHER", "ADMIN", "STUDENT")
                                .requestMatchers(HttpMethod.GET, "/teachers/find/**").hasAnyRole("STUDENT", "TEACHER", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/teachers").hasAnyRole("STUDENT", "TEACHER", "ADMIN")
                                .requestMatchers("/teachers/**").hasRole("ADMIN")
                                .requestMatchers("/users/**").permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .formLogin(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("admin").password("{noop}adminpass").roles("ADMIN").build());
        manager.createUser(User.withUsername("teacher").password("{noop}teacherpass").roles("TEACHER").build());
        manager.createUser(User.withUsername("student").password("{noop}studentpass").roles("STUDENT").build());
        return manager;
    }
}


