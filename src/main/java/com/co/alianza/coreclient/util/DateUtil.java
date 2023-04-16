package com.co.alianza.coreclient.util;

import com.co.alianza.coreclient.constants.DateFormatConstant;
import lombok.experimental.UtilityClass;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@UtilityClass
public class DateUtil {


    public static Date formatStringToDate(String date) {

        try {
            SimpleDateFormat simpleDate = new SimpleDateFormat(DateFormatConstant.DD_MM_YYYY);
            return simpleDate.parse(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static String formatDateToString(Date date) {

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DateFormatConstant.DD_MM_YYYY);
            return dateFormat.format(date);
        } catch (Exception e) {
            return null;
        }
    }
    public static String formatLocaleDateToString(LocalDate date) {

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateFormatConstant.DD_MM_YYYY);
            return date.format(formatter);
        } catch (Exception e) {
            return null;
        }
    }

    public static LocalDate formatStringToLocaleDate(String date) {

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateFormatConstant.DD_MM_YYYY);
            return LocalDate.parse(date, formatter);
        } catch (Exception e) {
            return null;
        }
    }
}
