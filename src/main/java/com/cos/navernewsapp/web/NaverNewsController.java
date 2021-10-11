package com.cos.navernewsapp.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.navernewsapp.domain.NaverRealtime;
import com.cos.navernewsapp.domain.NaverRealtimeRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RequiredArgsConstructor
@RestController
public class NaverNewsController {
	private final NaverRealtimeRepository naverRealtimeRepository;

	 @CrossOrigin
	 @GetMapping(value = "/naverRealtime", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	  public Flux<NaverRealtime> findAll(){
	    return naverRealtimeRepository.mFindAll()
				.subscribeOn(Schedulers.boundedElastic());
	  }
	 
	 @PostMapping("/newRealtime") 
		public Mono<NaverRealtime> save(@RequestBody NaverRealtime naverNews){
			return naverRealtimeRepository.save(naverNews);
		}
	 
//	 @GetMapping("/naverRealtime")
//		public CMRespDto<?> findAll2() { 
//			return new CMRespDto<>(1, "성공", naverRealtimeRepository.findAll());
//		}
	 
}
