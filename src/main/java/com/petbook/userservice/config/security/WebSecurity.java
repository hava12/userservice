package com.petbook.userservice.config.security;

import com.petbook.userservice.v1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity extends WebSecurityConfigurerAdapter {

	private final UserService userService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final Environment env;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();

		http.authorizeRequests().antMatchers("/**").permitAll();

		// formLogin 예시 ( React 사용 시에는 어떻게 ? )
		// http.formLogin()
		// 	.loginPage("./login.html")            // 사용자 정의 로그인 페이지
		// 	.defaultSuccessUrl("/home")           // 로그인 성공 후 이동 페이지
		// 	.failureUrl("/login.html?error=true") // 로그인 실패 후 이동 페이지
		// 	.usernameParameter("username")        // 아이디 파라미터명 설정
		// 	.passwordParameter("password")        // 패스워드 파라미터명 설정
		// 	.loginProcessingUrl("/login")         // 로그인 Form Action Url
		// 	.successHandler(null)                 // 로그인 성공 후 핸들러
		// 	.failureHandler(null);                // 로그인 실패 후 핸들러
	}

	private AuthenticationFilter getAuthenticationFilter() throws Exception {
		AuthenticationFilter authenticationFilter = new AuthenticationFilter( userService, env);
		authenticationFilter.setAuthenticationManager(authenticationManager());

		return authenticationFilter;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
	}
}
