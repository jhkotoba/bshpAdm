package com.bshp.config;

import java.util.Map;

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
			Map<String, Object> user = (Map<String, Object>) session.getAttributes().get("user");
			
			// 로그인페이지 & 리소스
			if(path.contains("/static") || path.contains("favicon.ico") ) {
				
				return chain.filter(exchange);
				
			}else if(path.contains("/login")) {
				
				if(user == null){
			
					return chain.filter(exchange);
					
				}else {
					
	                response.setStatusCode(HttpStatus.OK);
	                response.getHeaders().add(HttpHeaders.LOCATION, "/");                
	                return response.setComplete();
				}				
				
			// 세션이 없는경우
			}else if(user == null) {
				
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
