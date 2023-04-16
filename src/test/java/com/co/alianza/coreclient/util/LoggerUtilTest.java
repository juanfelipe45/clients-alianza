package com.co.alianza.coreclient.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;

class LoggerUtilTest {

    @Test
    @DisplayName("Given a date string, when calling formatStringToDate() method, then it should return a date object")
    void givenDateString_whenCallingFormatStringToDate_thenShouldReturnDateObject(){
        String dateString = "01/01/2022";
        Assertions.assertNotNull(DateUtil.formatStringToDate(dateString));
    }

    @Test
    @DisplayName("Given an invalid date string, when calling formatStringToDate() method, then it should return null")
    void givenInvalidDateString_whenCallingFormatStringToDate_thenShouldReturnNull() {
        String dateString = "invalid_date";
        Assertions.assertNull(DateUtil.formatStringToDate(dateString));
    }

    @Test
    @DisplayName("Given an invalid date string, when calling formatDateToString() method, then it should return null")
    void givenInvalidDateString_whenCallingFormatDateToString_thenShouldReturnNull() {
        Assertions.assertNull(DateUtil.formatDateToString(null));
    }

    @Test
    @DisplayName("Given a date string, when calling formatDateToString() method, then it should return a String")
    void givenDateString_whenCallingFormatDateToString_thenShouldReturnNull() {
        Assertions.assertNotNull(DateUtil.formatDateToString(new Date()));
    }

    @Test
    @DisplayName("Given an invalid date string, when calling formatLocaleDateToString() method, then it should return null")
    void givenInvalidDateString_whenCallingFormatLocalDateToString_thenShouldReturnNull() {
        Assertions.assertNull(DateUtil.formatLocaleDateToString(null));
    }

    @Test
    @DisplayName("Given an invalid date, when calling formatStringToLocaleDate() method, then it should return null")
    void givenInvalidDateString_whenCallingFormatStringToLocaleDate_thenShouldReturnNull() {
        String dateString = "invalid_date";
        Assertions.assertNull(DateUtil.formatStringToLocaleDate(dateString));
    }

    @Test
    @DisplayName("Given a date, when calling formatLocaleDateToString() method, then it should return a date object")
    void givenDateString_whenCallingFormatStringToLocaleDate_thenShouldReturnDateObject(){
        String dateString = "01/01/2022";
        Assertions.assertNotNull(DateUtil.formatStringToLocaleDate(dateString));
    }

    @Test
    @DisplayName("Given a date string, when calling formatLocaleDateToString() method, then it should return a date string")
    void givenDateString_whenCallingFormatLocaleDateToString_thenShouldReturnDateObject(){
        Assertions.assertNotNull(DateUtil.formatLocaleDateToString(LocalDate.now()));
    }
}
