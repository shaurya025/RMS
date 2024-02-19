//package com.rms.assignment.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//
//@Configuration
//
//public class AppConfig {
//
//
//        @Bean
//        public UserDetailsService userDetailsService() {
//            UserDetails adminDetails = User.builder().
//                    username("shaurya")
//                    .password(passwordEncoder().encode("shaurya")).roles("ADMIN").
//                    build();
//            return new InMemoryUserDetailsManager(adminDetails);
//        }
//
//        @Bean
//        public PasswordEncoder passwordEncoder() {
//            return new BCryptPasswordEncoder();
//        }
//
//
//    }
//
//
//
