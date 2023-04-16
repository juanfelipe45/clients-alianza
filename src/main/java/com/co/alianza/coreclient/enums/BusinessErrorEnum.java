package com.co.alianza.coreclient.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BusinessErrorEnum {
    SEARCH_NOT_FOUND("Your search did not return any results, review the information and try again", HttpStatus.NO_CONTENT),
    PARAMS_REQUIRED("At least one parameter is required", HttpStatus.BAD_REQUEST),
    DATES_REQUIRED("Start date and end date are required", HttpStatus.BAD_REQUEST),
    DATE_FORMAT("The date format is incorrect. The format I allow is dd/mm/yyyy", HttpStatus.BAD_REQUEST),
    INVALID_REQUEST("The request is invalid or does not have all the required parameters", HttpStatus.BAD_REQUEST),
    EXIST_SHARED_KEY("That sharedKey already exists", HttpStatus.CONFLICT),
    NO_EXIST_SHARED_KEY("The shareKey no exist", HttpStatus.CONFLICT)
    ;

    private final String message;
    private final HttpStatus httpStatus;
}
