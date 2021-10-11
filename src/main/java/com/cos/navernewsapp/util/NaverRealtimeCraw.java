package com.cos.navernewsapp.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.cos.navernewsapp.domain.NaverRealtime;

@Component
public class NaverRealtimeCraw {
		//int startNum = 277726; //7일
		//int aidNum = 277835; //8일
		int aidNum =277939; //9일
		//int aidNum = 277974; //10일

		public List<NaverRealtime> collectOneDay() {
			RestTemplate rt = new RestTemplate();
			List<NaverRealtime> realtimeList = new ArrayList<>();

			// 스크랩할 기사 기준 시간 (하루전)
			LocalDateTime date = LocalDateTime.now().minusDays(1).plusHours(9); //하루전 날짜와 시간
			Timestamp tsDate = Timestamp.valueOf(date); //시간을 타임스탬프 타입으로 변환 
			String strDate = tsDate.toString(); // 타임스탬프 타입을 스트링으로 변환 -> 날짜 비교
			String parseDate = strDate.substring(0,10); // 스크랩할 기사의 날짜를 년월일 까지만

			while(true){
				String aid = String.format("%010d", aidNum);
				String url = "https://news.naver.com/main/read.naver?mode=LSD&mid=shm&sid1=103&oid=437&aid=" + aid;
				
				try {
					String html = rt.getForObject(url, String.class);
					Document doc = Jsoup.parse(html);

					Element companyElement = doc.selectFirst(".press_logo img");
					Element titleElement = doc.selectFirst("#articleTitle");
					Element createdAtElement = doc.selectFirst(".t11");

					String company = companyElement.attr("alt");
					String title = titleElement.text();
					
					// 기사 발행일 
					String createdAt = createdAtElement.text();
					String createdAtParse = createdAt.substring(0, 10).replace(".", "-"); // yyyy-mm-dd 형식 기사 발행일 
					
					//어제 날짜와 기사 발행일을 비교
					if (createdAtParse.equals(parseDate)) {
						NaverRealtime naverRealtime = NaverRealtime.builder()
								.company(company)
								.title(title)
								.createdAt(tsDate)
								.build();
						
						realtimeList.add(naverRealtime);
						
						System.out.println(naverRealtime);
						
					} else {
						break;
					}
				}catch (Exception e) {
					System.out.println("통신 오류!!");
				}
				aidNum++;
				
			} // end of for
			
			return realtimeList;

		} // end of newsCraw
		
}
