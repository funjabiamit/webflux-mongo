package com.example.demo.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.demo.model.Post;

public interface PostRepository extends ReactiveMongoRepository<Post, String> {
}

