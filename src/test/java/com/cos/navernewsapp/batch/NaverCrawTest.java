package com.cos.navernewsapp.batch;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import com.cos.navernewsapp.domain.NaverRealtime;

public class NaverCrawTest {
	// int aidNum =1;
	//int startNum = 277726; //7일
	//int aidNum = 277835; //8일
	int aidNum =277939; //9일
	//int aidNum = 277974; //10일
	

	// @Test
	public void test() {
		RestTemplate rt = new RestTemplate();
		List<NaverRealtime> naverRealtimeList = new ArrayList<>();

		LocalDateTime t = LocalDateTime.now().minusDays(1).plusHours(9);
		Timestamp ts = Timestamp.valueOf(t);

		for (int i = 1; i < 3; i++) {
			String aid = String.format("%010d", aidNum);
			String url = "https://news.naver.com/main/read.naver?mode=LSD&mid=shm&sid1=102&oid=022&aid=" + aid;
			String html = rt.getForObject(url, String.class);

			// try {
			Document doc = Jsoup.parse(html);

			Element companyElement = doc.selectFirst(".press_logo img");
			Element titleElement = doc.selectFirst("#articleTitle");
			Element createdAtElement = doc.selectFirst(".t11");

			String company = companyElement.attr("alt");
			String title = titleElement.text();
			String createdAt = createdAtElement.text();

			System.out.println(title);
			System.out.println(createdAt);
//			NaverRealtime naverRealtime = NaverRealtime.builder().company(company).title(title).createdAt(createdAt)
//					.build();
//			articles.add(naverRealtime);
//
//			System.out.println(company);
//			System.out.println(title);
//			System.out.println(createdAt);

//			articles.add(naverRealtime); 
			aidNum++;
			// } catch (IOException e) {
			// e.printStackTrace();
			// }

		}
	}

	@Test
	public void test2() {
		RestTemplate rt = new RestTemplate();
		List<NaverRealtime> realtimeList = new ArrayList<>();

		// 스크랩할 기사 기준 시간 (하루전)
		LocalDateTime date = LocalDateTime.now().minusDays(2).plusHours(9); //하루전 날짜와 시간
		Timestamp tsDate = Timestamp.valueOf(date); //시간을 타임스탬프 타입으로 변환 
		String strDate = tsDate.toString(); // 타임스탬프 타입을 스트링으로 변환 -> 날짜 비교
		String parseDate = strDate.substring(0,10); // 스크랩할 기사의 날짜를 년월일 까지만
		System.out.println(date.getClass().getName());//2021-10-09T19:49:53.531084 java.time.LocalDateTime
		System.out.println(tsDate.getClass().getName());//2021-10-09 19:49:53.531084 java.sql.Timestamp
		System.out.println(strDate.getClass().getName());//2021-10-09 19:49:53.531084 java.lang.String
		System.out.println(parseDate.getClass().getName());//2021-10-09 java.lang.String

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
				
				//현재 시간과 기사 발행일을 비교
				if (createdAtParse.equals(parseDate)) {
					System.out.println("good!!");
					NaverRealtime naverRealtime = NaverRealtime.builder()
							.company(company)
							.title(title)
							.createdAt(tsDate)
							.build();
					
					realtimeList.add(naverRealtime);
					
					System.out.println(naverRealtime);
					
				} else {
					System.out.println(aidNum);
					break;
				}
			}catch (Exception e) {
				System.out.println("통신 오류!!");
			}
			aidNum++;
			
		} // end of for
		System.out.println(aidNum);

	}
	
}