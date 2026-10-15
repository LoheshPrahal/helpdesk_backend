package com.springboot.helpdesk.config;

import com.springboot.helpdesk.service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
//    @Bean
//    public UserDetailsService users() {
//        UserDetails customer1 = User.builder()
//                .username("customer1")
//                .password("{noop}Customer#1")
//                .authorities("CUSTOMER")
//                .build();
//        UserDetails executive1 = User.builder()
//                .username("executive1")
//                .password("{noop}Executive#1")
//                .authorities("EXECUTIVE")
//                .build();
//        UserDetails manager1 = User.builder()
//                .username("manager1")
//                .password("{noop}Manager#1")
//                .authorities("MANAGER")
//                .build();
//        return new InMemoryUserDetailsManager(customer1, executive1, manager1);
//    }
    private final MyUserDetailsService myUserDetailsService ;

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain defaultFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST,"/api/customer/add").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/auth/login").authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/customer/{id}").hasAuthority("CUSTOMER")
                        .requestMatchers(HttpMethod.POST,"/api/ticket/add").hasAuthority("CUSTOMER")

                        // Executive can be onboarded by manager after they login
                        .requestMatchers(HttpMethod.POST,"/api/executive/add").hasAuthority("MANAGER")
                        // Admin can onboard manager
                        .requestMatchers(HttpMethod.POST,"/api/manager/add").hasAuthority("ADMIN")
                        // Admin onboard API (permitAll to add) (After addition - denyAll)
                        .requestMatchers(HttpMethod.POST,"/api/admin/add").permitAll()
                        // Manager assigning the ticket to executive
                        .requestMatchers(HttpMethod.PUT,"/api/ticket/assign/{executiveId}/{ticketId}").hasAuthority("MANAGER")
                        .anyRequest().permitAll()
                );
        http.addFilterBefore(jwtFilter , UsernamePasswordAuthenticationFilter.class);


        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public AuthenticationProvider getProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(myUserDetailsService);
        authenticationProvider.setPasswordEncoder(getEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder getEncoder(){
        return new BCryptPasswordEncoder();
    }
}
