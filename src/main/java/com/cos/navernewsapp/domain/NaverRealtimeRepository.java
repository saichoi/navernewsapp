package com.cos.navernewsapp.domain;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;

import reactor.core.publisher.Flux;

public interface NaverRealtimeRepository extends ReactiveMongoRepository<NaverRealtime, String>{

@Tailable
@Query("{}")
Flux<NaverRealtime> mFindAll();

}

