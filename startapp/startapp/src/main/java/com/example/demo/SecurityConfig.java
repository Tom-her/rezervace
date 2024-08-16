package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/login", "/register").permitAll() // Umožní přístup k přihlašovací a registrační stránce
                                .anyRequest().authenticated() // Ostatní stránky vyžadují autentizaci
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login") // Nastaví vlastní přihlašovací stránku
                                .defaultSuccessUrl("/table", true) // Po úspěšném přihlášení přesměruje na /table
                                .failureHandler((request, response, exception) -> {
                                    // Nastaví chybovou zprávu do session, pokud přihlášení selže
                                    request.getSession().setAttribute("loginError", "Špatný login nebo heslo");
                                    response.sendRedirect("/login");
                                })
                                .permitAll() // Umožní přístup na přihlašovací stránku
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/logout") // URL pro odhlášení
                                .logoutSuccessUrl("/logout-success") // URL pro přesměrování po úspěšném odhlášení
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .permitAll() // Umožní přístup na URL pro odhlášení
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user1 = User.withUsername("user")
                .password(passwordEncoder.encode("password"))
                .roles("USER")
                .build();

        UserDetails user2 = User.withUsername("Tomas")
                .password(passwordEncoder.encode("tom"))
                .roles("USER")
                .build();

        UserDetails user3 = User.withUsername("Heran")
                .password(passwordEncoder.encode("heran"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user1, user2, user3); // Přidání uživatele do paměti
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
