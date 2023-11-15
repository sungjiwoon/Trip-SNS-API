package com.fastcampus.toyproject.common.util.api.service;

import java.net.URI;
import org.springframework.web.util.UriComponentsBuilder;

public class UriBuilder {


    private static final String KAKAO_KEYWORD_SEARCH_ADDRESS_URL = "https://dapi.kakao.com/v2/local/search/keyword.json";

    public URI keywordUriBuilder(String keyword) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(KAKAO_KEYWORD_SEARCH_ADDRESS_URL);
        uriBuilder.queryParam("query", keyword);

        URI uri = uriBuilder.build().encode().toUri();

        return uri;
    }

}
