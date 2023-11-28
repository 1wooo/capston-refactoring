package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/loginPage/**", "/joinPage/**").permitAll()
                .antMatchers("/mainPage/**").access("hasRole('ROLE_ADMIN')")
                .and()
                .formLogin()
                .loginPage("/HomeLogin")
                .loginProcessingUrl("/loginProc")
                .defaultSuccessUrl("/mainPage/index")
                .and().build();
    }
}
