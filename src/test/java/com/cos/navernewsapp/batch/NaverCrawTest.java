package com.cos.navernewsapp.batch;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
	//int aidNum = 277726; //7일
	int aidNum = 277835; //8일
	

	// @Test
	public void test() {
		RestTemplate rt = new RestTemplate();
		List<NaverRealtime> articles = new ArrayList<>();

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
			NaverRealtime naverRealtime = NaverRealtime.builder().company(company).title(title).createdAt(createdAt)
					.build();
			articles.add(naverRealtime);

			System.out.println(company);
			System.out.println(title);
			System.out.println(createdAt);

			articles.add(naverRealtime); // null 일 때 try ~ catch 처리해야함

			aidNum++;
			// } catch (IOException e) {
			// e.printStackTrace();
			// }

		}
	}

	@Test
	public void test2() {
		RestTemplate rt = new RestTemplate();
		List<NaverRealtime> articles = new ArrayList<>();

		for (int i = 1; i < 3; i++) {
			String aid = String.format("%010d", aidNum);
			String url = "https://news.naver.com/main/read.naver?mode=LSD&mid=shm&sid1=103&oid=437&aid=" + aid;
			String html = rt.getForObject(url, String.class);
			Document doc = Jsoup.parse(html);

			Element companyElement = doc.selectFirst(".press_logo img");
			Element titleElement = doc.selectFirst("#articleTitle");
			Element createdAtElement = doc.selectFirst(".t11");

			String company = companyElement.attr("alt");
			String title = titleElement.text();
			String createdAt = createdAtElement.text();
			
			LocalDate date = LocalDate.now(); // 오늘 날짜 
			String dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")); // 날짜 문자열로 변환
			if (createdAt.substring(0, 10).replace(".", "-").equals(dateStr)) {
				System.out.println("good!!");
				NaverRealtime naverRealtime = NaverRealtime.builder().company(company).title(title).createdAt(createdAt)
						.build();
				articles.add(naverRealtime);
				
				System.out.println(naverRealtime);
			}
			aidNum++;
		} // end of for

	}

	private String SimpleDateFormat(String string) {
		// TODO Auto-generated method stub
		return null;
	}
}