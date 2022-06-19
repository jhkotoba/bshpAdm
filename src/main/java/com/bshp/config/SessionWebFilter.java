package com.bshp.config;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.bshp.common.InitConstruct;
import com.bshp.common.util.CommonUtil;
import com.bshp.system.vo.MenuVo;
import com.bshp.user.vo.PublicAdminVo;

import reactor.core.publisher.Mono;

@Component
public class SessionWebFilter implements WebFilter {

	/**
	 * 필터설정
	 */
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
			
		return exchange.getSession().map(session -> {
			
			// 응답
			ServerHttpResponse response = exchange.getResponse();
			// 요청
			ServerHttpRequest request = exchange.getRequest();
			
			// 메소드
			String method = request.getMethod().name();

			// URL PATH
			String path = request.getPath().value();
			PublicAdminVo user = (PublicAdminVo) session.getAttributes().get("user");
			
			// 로그인페이지 & 리소스
			if(path.contains("/static") || path.contains("favicon.ico") ) {
				
				return chain.filter(exchange);
			// 로그인 및 가입신청 페이지
			}else if(path.contains("/login") || path.contains("/join")) {
				// 세션정보가 없을경우
				if(user == null){
					return chain.filter(exchange);
				// 세션이 있는데 로그인페이지 접근시
				}else {
					response.setStatusCode(HttpStatus.SEE_OTHER);
					response.getHeaders().add(HttpHeaders.LOCATION, "/");
					return response.setComplete();
				}
			// 세션이 없는경우 로그인 페이지 이동
			}else if(user == null) {
				response.setStatusCode(HttpStatus.SEE_OTHER);
				response.getHeaders().add(HttpHeaders.LOCATION, "/login");
				return response.setComplete();
				
			// 세션이 있는 경우
			}else {
				
				// post는 비동기 통신
				if("POST".equals(method)) {
					return chain.filter(exchange);
					
				// 페이지 이동에서만 페이지 권한체크
				}else {
					
					if("/".equals(path) == false) {
						// 권한 체크
						@SuppressWarnings({ "unchecked", "rawtypes" })
						List<Integer> authList = (List) session.getAttributes().get("auth");
						List<MenuVo> menulist = InitConstruct.menuList;
						
						int menuNo = menulist.stream()
							.filter(menu -> menu.getMenuUrl().equals(path))
							.collect(Collectors.toList()).get(0).getMenuNo();
						
						int authSize = authList.stream()
							.filter(auth -> auth == menuNo).collect(Collectors.toList()).size();
						
						// 이동하려는 화면의 권한이 없을 경우
						if(authSize < 1) {
							response.setStatusCode(HttpStatus.SEE_OTHER);
							response.getHeaders().add(HttpHeaders.LOCATION, "/");
							return response.setComplete();
						}
					}
					
					// 이전 CSRF 토큰 가져오기
					String csrfToken = (String) session.getAttributes().get("CSRF_TOKEN");
					String reqCsrfToken = request.getCookies().getFirst("CSRF_TOKEN") == null ? null : request.getCookies().getFirst("CSRF_TOKEN").getValue();
					
					// 신규 CSRF 토큰 생성
					String newCsrfToken = CommonUtil.getRandomString(20);
					// 신규 CSRF 토큰 발행
					session.getAttributes().put("CSRF_TOKEN", newCsrfToken);
					response.addCookie(ResponseCookie.from("CSRF_TOKEN", newCsrfToken).maxAge(Duration.ofMinutes(30)).build());
					
					// CSRF 체크
					if(csrfToken == null || reqCsrfToken == null || !csrfToken.equals(reqCsrfToken)) {
						response.setStatusCode(HttpStatus.SEE_OTHER);
						response.getHeaders().add(HttpHeaders.LOCATION, "/");
						return response.setComplete();
					}else {
						return chain.filter(exchange);
					}
				}
			}
			
		}).flatMap(argument-> Mono.from(argument));
	}
}
