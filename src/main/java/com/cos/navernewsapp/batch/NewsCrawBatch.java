package com.cos.navernewsapp.batch;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cos.navernewsapp.domain.NaverRealtime;
import com.cos.navernewsapp.domain.NaverRealtimeRepository;
import com.cos.navernewsapp.util.NaverRealtimeCraw;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class NewsCrawBatch {
	
	private final NaverRealtimeRepository naverRealtimeRepository;
	private final NaverRealtimeCraw naverRealtimeCraw;

	// 초 분 시 일 월 주
		//@Scheduled(fixedDelay = 1000 * 60 * 1, zone = "Asia/Seoul")
		@Scheduled(cron = "* 31 21 * * *", zone = "Asia/Seoul")
		public void newsCrawAndSave() {
			List<NaverRealtime> news = naverRealtimeCraw.collectOneDay();
			naverRealtimeRepository.saveAll(news); 
		}
		
}// end of class 
