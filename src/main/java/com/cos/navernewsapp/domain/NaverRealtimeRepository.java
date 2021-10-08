package com.cos.navernewsapp.domain;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;

import reactor.core.publisher.Flux;

public interface NaverRealtimeRepository extends ReactiveMongoRepository<NaverRealtime, String>{
	@Tailable // MongoDB와 연동, 커서를 계속 열어두는 어노테이션 
	@Query("{}")
	Flux<NaverRealtime> mFindAll();

}
