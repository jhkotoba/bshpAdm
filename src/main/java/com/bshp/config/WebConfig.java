package com.bshp.config;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.web.reactive.result.view.MustacheViewResolver;
import org.springframework.context.annotation.Configuration;
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
}
