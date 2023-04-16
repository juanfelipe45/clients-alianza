package com.co.alianza.coreclient.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Date;

@SpringBootTest
class DateUtilTest {

    @Test
    @DisplayName("Given a date string, when calling formatStringToDate() method, then it should return a date object")
    void givenDateString_whenCallingFormatStringToDate_thenShouldReturnDateObject(){
        String dateString = "01/01/2022";
        Assertions.assertNotNull(DateUtil.formatStringToDate(dateString));
    }
}
