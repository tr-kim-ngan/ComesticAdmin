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
	// Cấu hình bảo mật cho admin
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()) // Tắt CSRF để đơn giản hóa
				.authorizeHttpRequests((requests) -> requests
						.requestMatchers("/", "/index", "/product/**", "/category/**").permitAll()
						.requestMatchers("/*").permitAll()

						.requestMatchers("/admin/**").hasAuthority("ADMIN")

						// .requestMatchers("/admin/**").permitAll()
						.anyRequest().authenticated() // Các yêu cầu khác phải xác thực
				)
				
				 // Cấu hình đăng nhập cho admin
				.formLogin((form) -> form.loginPage("/login") // Trang đăng nhập
						.loginProcessingUrl("/login") // URL xử lý đăng nhập
						.usernameParameter("username") // Tham số username
						.passwordParameter("password") // Tham số password
						.defaultSuccessUrl("/admin", true) // Điều hướng sau khi đăng nhập thành công
						.failureUrl("/login?error=true")
						.permitAll())
			
				

				.logout((logout) -> logout
						.logoutUrl("/logout")
						.logoutSuccessUrl("/login?logout") // Điều hướng sau khi
																									// đăng xuất
						.permitAll()
				);

		return http.build();
	}
	// Cấu hình bảo mật cho khách hàng
    @Bean
    public SecurityFilterChain customerSecurityFilterChain(HttpSecurity http) throws Exception {
        http .securityMatcher("/**") 
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/", "/index", "/product/**", "/category/**", "/customer/register", "/customer/login").permitAll() // Các trang công khai
                .requestMatchers("/customer/**").hasAuthority("CUSTOMER") // Các trang dành cho khách hàng đã đăng nhập
                .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                .loginPage("/customer/login") // Trang đăng nhập cho khách hàng
                .loginProcessingUrl("/customer/login") // URL xử lý đăng nhập khách hàng
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/customer", true) // Điều hướng về trang khách hàng sau khi đăng nhập thành công
                .failureUrl("/customer/login?error=true")
                .permitAll()
            )
            .logout((logout) -> logout
                .logoutUrl("/customer/logout")
                .logoutSuccessUrl("/customer/login?logout") // Điều hướng sau khi khách hàng đăng xuất
                .permitAll()
            );

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
