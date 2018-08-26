package com.example.demo;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.demo.controller.PostController;

@SpringBootApplication
@EnableMongoAuditing
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public RouterFunction<ServerResponse> routes(PostController postController) {
		return route(GET("/posts"), postController::all).andRoute(POST("/posts"), postController::create)
				.andRoute(GET("/posts/{id}"), postController::get).andRoute(PUT("/posts/{id}"), postController::update)
				.andRoute(DELETE("/posts/{id}"), postController::delete);
	}

}
