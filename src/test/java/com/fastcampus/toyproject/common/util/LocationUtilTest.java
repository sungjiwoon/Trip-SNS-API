package com.fastcampus.toyproject.common.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LocationUtilTest {

    @Test
    void 속도() throws IOException {
        long startTime = System.currentTimeMillis();

        String location1 = "청와대";

        LocationUtil.requestKeywordSearch(location1);

        long stopTime = System.currentTimeMillis();

        System.out.println(stopTime-startTime);

    }@Test
    void 위치_변환() throws IOException {

        String location1 = "청와대";
        String location2 = "백악관";
        String location3 = "";
        String location4 = "런던 브리타니아 인터내셔널 호텔";

        assertThat(LocationUtil.requestKeywordSearch(location1)).isEqualTo("청와대 본관");
        assertThat(LocationUtil.requestKeywordSearch(location2)).isEqualTo("백악관");
        assertThat(LocationUtil.requestKeywordSearch(location3)).isEqualTo("북한산둘레길 6구간평창마을길");
        assertThat(LocationUtil.requestKeywordSearch(location4)).isEqualTo(location4);


    }

}
