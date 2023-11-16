package com.fastcampus.toyproject.common.util;

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

        System.out.println("청와대: " + LocationUtil.requestKeywordSearch(location1));
        System.out.println("백악관: " + LocationUtil.requestKeywordSearch(location2));
        System.out.println("공백: " + LocationUtil.requestKeywordSearch(location3));
        System.out.println("런던 브리타니아 인터내셔널 호텔: " + LocationUtil.requestKeywordSearch(location4));

    }

}
