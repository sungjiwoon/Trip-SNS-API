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

        System.out.println("청와대: " + LocationUtil.requestKeywordSearch(location1));
        System.out.println("백악관: " + LocationUtil.requestKeywordSearch(location2));

    }

   @Test
    void 실패() throws IOException {

        String location3 = "";
       LocationUtil.requestKeywordSearch(location3);

    }

}
