package com.fastcampus.toyproject.common.util;

import com.fastcampus.toyproject.common.util.api.dto.KakaoResponse;
import com.fastcampus.toyproject.common.util.api.service.UriBuilder;
import java.net.URI;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class LocationUtil {

    private static String kakaoRestApiKey = System.getenv("API_KEY");


    public static String requestKeywordSearch(String keyword) {
        if(keyword.equals("")) {
            keyword="종로";
            log.info("keyword가 없어서 default(종로)로 검색합니다.");
        }

        URI uri = new UriBuilder().keywordUriBuilder(keyword);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoRestApiKey);

        HttpEntity<String> entity = new HttpEntity<String>(headers);

        return Optional.ofNullable
                (restTemplate.exchange(uri, HttpMethod.GET, entity, KakaoResponse.class).getBody())
            .map(KakaoResponse::getDocumentList)
            .filter(list -> !list.isEmpty())
            .map(list -> list.get(0).getPlaceName())
            .orElse(keyword);
    }
}
