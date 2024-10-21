package com.kimngan.ComesticAdmin;

import com.kimngan.ComesticAdmin.services.NguoiDungDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private NguoiDungDetailsService nguoiDungDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf->csrf.disable()) // Tắt CSRF để đơn giản hóa
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/*").permitAll()
                       .requestMatchers("/admin/**").hasAuthority("ADMIN")

                       // .requestMatchers("/admin/**").permitAll()
                        .anyRequest().authenticated() // Các yêu cầu khác phải xác thực
                )
                .formLogin((form) -> form
                        .loginPage("/login") // Trang đăng nhập
                        .loginProcessingUrl("/login") // URL xử lý đăng nhập
                        .usernameParameter("username") // Tham số username
                        .passwordParameter("password") // Tham số password
                        .defaultSuccessUrl("/admin", true) // Điều hướng sau khi đăng nhập thành công
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout") // Điều hướng sau khi đăng xuất
                        .permitAll()
                );

        return http.build();
    }

    // Cấu hình WebSecurity để bỏ qua một số đường dẫn static resources
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/static/**", "/assets/**","/upload/**");
    }

    // Password encoder để mã hóa mật khẩu
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}




















