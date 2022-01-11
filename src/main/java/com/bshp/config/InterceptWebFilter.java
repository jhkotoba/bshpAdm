package com.bshp.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.bshp.user.vo.PublicUserVo;

import reactor.core.publisher.Mono;

@Component
public class InterceptWebFilter implements WebFilter {

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
			
			// URL PATH
			String path = request.getPath().value();
			PublicUserVo user = (PublicUserVo) session.getAttributes().get("user");
			
			// 로그인페이지 & 리소스
			if(path.contains("/static") || path.contains("favicon.ico") ) {
				
				return chain.filter(exchange);
			// 로그인 페이지
			}else if(path.contains("/login")) {
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
				return chain.filter(exchange);
			}
			
		}).flatMap(argument-> Mono.from(argument));
	}
}
