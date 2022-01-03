package com.bshp.config;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.web.reactive.result.view.MustacheViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.CacheControl;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.ViewResolverRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@EnableWebFlux
public class WebConfig implements WebFluxConfigurer {
	
	/**
	 * Mustache 리졸브
	 */
	private final MustacheViewResolver resolver;
	
	/**
	 * WebConfig 생성
	 * @param resolver
	 */
	public WebConfig(MustacheViewResolver resolver) {
		this.resolver = resolver;
	}
	
	/**
	 * 뷰 리졸브 설정
	 */
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.viewResolver(resolver);
	}
	
	/**
	 * 정적 자원(Static Resources) 설정
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**")
			.addResourceLocations("/", "classpath:/static/")
			.setCacheControl(CacheControl.maxAge(30, TimeUnit.DAYS));
    }
	
	/**
	 * 레디스 설정
	 * @param factory
	 * @return
	 */
	@Bean
	public ReactiveRedisOperations<String, String> redisOperations(ReactiveRedisConnectionFactory factory){
		Jackson2JsonRedisSerializer<String> serializer = new Jackson2JsonRedisSerializer<>(String.class);
		
		RedisSerializationContext.RedisSerializationContextBuilder<String, String> builder = 
				RedisSerializationContext.newSerializationContext(new StringRedisSerializer());
		
		RedisSerializationContext<String, String> context = builder.value(serializer).build();
		
		return new ReactiveRedisTemplate<>(factory, context);
	}
}
