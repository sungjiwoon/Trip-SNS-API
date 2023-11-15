package com.fastcampus.toyproject.common.util;

import com.fastcampus.toyproject.common.util.api.dto.KakaoResponse;
import com.fastcampus.toyproject.common.util.api.service.UriBuilder;
import java.net.URI;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;


public class LocationUtil {

    private static String kakaoRestApiKey = System.getenv("API_KEY");


    //예외 처리 해야 함: 0개인 경우, 공백 입력 값
    public static String requestKeywordSearch(String keyword) {

        URI uri = new UriBuilder().keywordUriBuilder(keyword);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoRestApiKey);

        HttpEntity<String> entity = new HttpEntity<String>(headers);

        //0개일때 예외 처리 해야 함

        return restTemplate.exchange(uri, HttpMethod.GET, entity, KakaoResponse.class).getBody()
            .getDocumentList().get(0).getPlaceName();
    }
}
