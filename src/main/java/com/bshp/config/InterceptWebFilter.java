package com.bshp.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

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
			
			// 로그인페이지 & 리소스
			if(path.contains("/login") || path.contains("/static") || path.contains("favicon.ico")) {
				
				return chain.filter(exchange);
				
			// 세션이 없는경우
			}else if(session.getAttribute("BSHP_SESSION") == null) {
				
				session.getAttributes().put("error", "error");
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
