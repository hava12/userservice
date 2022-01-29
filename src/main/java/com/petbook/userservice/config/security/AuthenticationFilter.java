package com.petbook.userservice.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petbook.userservice.v1.model.dto.UserRequest;
import com.petbook.userservice.v1.model.dto.UserResponse;
import com.petbook.userservice.v1.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * UsernamePasswordAuthenticationFilter란 ?
 *
 * LoginForm인증 절차
 *
 * -------------------------------------------------------------------------------------------------------------
 *
 *   usernamePasswordAuthenticationFilter <---------------------------------------------------------------------ㄱ
 *                  |                                                                                            |
 *                  |                           No                                                               |
 *        AntPathRequestMatcher(/**)            -->    chain.doFilter                                            |
 *                  |                                                                                            |
 *                  | Yes                                                                                        |
 *   Authentication(Username + Password)                                                                         |
 *                  |                                                                                            |
 *                  |                           위임                                        인증실패               |
 *         AuthenticationManager                -->        AuthenticationProvider           ----->    AuthenticationException
 *                  |                           <--
 *                  |                           인증 성공
 *   Authentication(User + Authorities)
 *                  |
 *                  |
 *         SecurityContext에 저장
 *                  |
 *                  |
 *            SuccessHandler
 *
 */


@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final UserService userService;
	private final Environment env;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
		throws AuthenticationException {
		try {
			UserRequest cred = new ObjectMapper().readValue(request.getInputStream(), UserRequest.class);

			// 인증 토큰 생성
			UsernamePasswordAuthenticationToken token =
				new UsernamePasswordAuthenticationToken(
					cred.getUserId(),
					cred.getPassword(),
					new ArrayList<>()
				);

			return getAuthenticationManager().authenticate(token);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request,
		HttpServletResponse response,
		FilterChain chain,
		Authentication authResult) throws IOException, ServletException {
		String userId = ((User)authResult.getPrincipal()).getUsername();
		UserResponse userResponse = userService.selectUserById(userId);

		String token = Jwts.builder()
			.setSubject(userResponse.getUserId())
			.setExpiration(
				new Date(System.currentTimeMillis() + Long.parseLong(env.getProperty("token.expiration_time"))))
			.signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret"))
			.compact();

		response.addHeader("token", token);
		response.addHeader("userId", userResponse.getUserId());
	}
}
