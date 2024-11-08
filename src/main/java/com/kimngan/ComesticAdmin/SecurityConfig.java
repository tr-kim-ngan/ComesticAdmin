package com.kimngan.ComesticAdmin;

import com.kimngan.ComesticAdmin.services.NguoiDungDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private NguoiDungDetailsService nguoiDungDetailsService;

    // Cấu hình bảo mật cho admin
    @Bean
    @Order(1)
    public SecurityFilterChain adminSecurityFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/admin/**", "/login") // Áp dụng cho các URL của admin và login admin
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((requests) -> requests
                    .requestMatchers("/", "/index", "/product/**", "/category/**").permitAll() // Cho phép tự do các URL công khai
                    .requestMatchers("/admin/**").hasAuthority("ADMIN") // Cần quyền "ADMIN" cho admin
                    .anyRequest().authenticated())
                .formLogin((form) -> form
                    .loginPage("/login") // Trang đăng nhập cho admin
                    .loginProcessingUrl("/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/admin", true) // Điều hướng đến trang admin sau đăng nhập thành công
                    .failureUrl("/login?error=true")
                    .permitAll())
                .logout((logout) -> logout
                    .logoutUrl("/admin/logout") // URL xử lý logout dành cho admin
                    .logoutSuccessUrl("/login?logout") // Điều hướng về trang login sau khi logout
                    .permitAll());

        return http.build();
    }

//    @Bean
//    @Order(2)
//    public SecurityFilterChain customerSecurityFilterChain(HttpSecurity http) throws Exception {
//        http.securityMatcher("/**") // Áp dụng bảo mật cho tất cả các URL
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests((requests) -> requests
//                    // Các URL công khai mà mọi người đều có thể truy cập mà không cần đăng nhập
//                    .requestMatchers("/", "/index", "/product/**", "/category/**", "/customer/register", "/customer/login")
//                    .permitAll()
//                    
//                    // Các URL yêu cầu quyền CUSTOMER (cần đăng nhập để sử dụng các tính năng giỏ hàng, yêu thích, đặt hàng)
//                    .requestMatchers("/customer/cart/**", "/customer/favorites/**", "/customer/orders/**").hasAuthority("CUSTOMER")
//                    
//                    // Các yêu cầu khác phải đăng nhập mới có thể truy cập
//                    .anyRequest().authenticated())
//                    
//                // Cấu hình form đăng nhập cho khách hàng
//                .formLogin((form) -> form
//                    .loginPage("/customer/login") // Trang đăng nhập cho khách hàng
//                    .loginProcessingUrl("/customer/login")
//                    .usernameParameter("username")
//                    .passwordParameter("password")
//                    .defaultSuccessUrl("/", true) // Điều hướng về trang chủ sau khi đăng nhập thành công
//                    .failureUrl("/customer/login?error=true")
//                    .permitAll())
//
//                // Cấu hình logout cho khách hàng
//                .logout((logout) -> logout
//                    .logoutUrl("/customer/logout") // URL xử lý logout dành cho khách hàng
//                    .logoutSuccessUrl("/") // Điều hướng về trang chủ sau khi logout
//                    .permitAll());
//
//        return http.build();
//    }
    
    @Bean
    @Order(2)
    public SecurityFilterChain customerSecurityFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/**")
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests((requests) -> requests
                // Các URL công khai mà tất cả mọi người có thể truy cập mà không cần đăng nhập
//                .requestMatchers("/", "/index", "/products/**", "/category/**", "/customer/register", "/customer/login")
//                .permitAll()
//                
//                // Các URL yêu cầu quyền "CUSTOMER" (cần đăng nhập để sử dụng các tính năng giỏ hàng, yêu thích, đặt hàng)
//                .requestMatchers("/customer/cart/**", "/customer/favorites/**", "/customer/orders/**")
//                .hasAuthority("CUSTOMER")
//            		.requestMatchers("/customer/register", "/customer/login", "/product/**").permitAll() // Cho phép truy cập tự do cho đăng ký, đăng nhập, và chi tiết sản phẩm
                    .requestMatchers("/", "/index", "/customer/register", "/customer/login", "/product/**", "/category/**", "/search/**").permitAll() // Cho phép truy cập tự do cho đăng ký, đăng nhập, chi tiết sản phẩm, danh mục và tìm kiếm

            		.requestMatchers("/customer/**").hasAuthority("CUSTOMER") 
            		 .requestMatchers("/customer/favorites/**").hasAuthority("CUSTOMER") 
            		

                // Các yêu cầu khác phải đăng nhập mới được truy cập
                .anyRequest().authenticated())
            .formLogin((form) -> form
                .loginPage("/customer/login")
                .loginProcessingUrl("/customer/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/", true)
                .failureUrl("/customer/login?error=true")
                .permitAll())
            .logout((logout) -> logout
                .logoutUrl("/customer/logout")
                .logoutSuccessUrl("/")
                .permitAll());

        return http.build();
    }

    
    


    // Cấu hình WebSecurity để bỏ qua một số đường dẫn static resources
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/fe/**", "/static/**", "/assets/**", "/upload/**");
    }

    // Password encoder để mã hóa mật khẩu
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
