package com.bshp.main;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.result.view.Rendering;

import reactor.core.publisher.Mono;

@Controller
public class MainController {

	/**
	 * 메인 페이지
	 * @return
	 */
	@GetMapping({"/", "/main"})
	public Mono<Rendering> main(){
		
		Rendering render = Rendering.view("view/main/main")
			.status(HttpStatus.OK)
			.build();
		
		return Mono.just(render);
	}
}
