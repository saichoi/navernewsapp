package com.cos.navernewsapp.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
@Document(collection = "naver_realtime")
public class NaverRealtime {
	@Id
	private String _id;
	
	private String company;
	private String title;
	private String createdAt;
}
