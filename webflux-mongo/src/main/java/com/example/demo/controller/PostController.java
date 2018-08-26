package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.demo.model.Post;
import com.example.demo.repository.PostRepository;

import reactor.core.publisher.Mono;
@RestController()
@RequestMapping(value = "/posts")
public class PostController {

	private final PostRepository posts;

	public PostController(PostRepository posts) {
        this.posts = posts;
    }

	public Mono<ServerResponse> all(ServerRequest req) {
		return ServerResponse.ok().body(this.posts.findAll(), Post.class);
	}

	public Mono<ServerResponse> create(ServerRequest req) {
		return req.bodyToMono(Post.class).flatMap(post -> this.posts.save(post))
				// .flatMap(p -> ServerResponse.created(URI.create("/posts/" +
				// p.getId())).build());
				.flatMap(post -> ServerResponse.ok().body(Mono.just(post), Post.class))
				.switchIfEmpty(ServerResponse.notFound().build());
	}

	public Mono<ServerResponse> get(ServerRequest req) {
		return this.posts.findById(req.pathVariable("id"))
				.flatMap(post -> ServerResponse.ok().body(Mono.just(post), Post.class))
				.switchIfEmpty(ServerResponse.notFound().build());
	}

	public Mono<ServerResponse> update(ServerRequest req) {

		return Mono.zip((data) -> {
			Post p = (Post) data[0];
			Post p2 = (Post) data[1];
			p.setTitle(p2.getTitle());
			p.setContent(p2.getContent());
			return p;
		}, this.posts.findById(req.pathVariable("id")), req.bodyToMono(Post.class)).cast(Post.class)
				.flatMap(post -> this.posts.save(post)).flatMap(post -> ServerResponse.noContent().build());

	}

	public Mono<ServerResponse> delete(ServerRequest req) {
		return ServerResponse.noContent().build(this.posts.deleteById(req.pathVariable("id")));
	}

}
