//package com.rms.assignment.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//
//
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//
//import org.springframework.security.web.SecurityFilterChain;
//
//@EnableWebSecurity
//
//public class SecurityConfig  {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests(authorizeRequests ->
//                        authorizeRequests
//                                .antMatchers("/home/**").permitAll()
//                                .anyRequest().authenticated()
//                )
//                .formLogin().loginPage("/login")
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll();;
//
//        return http.build();
//    }
//
//}
