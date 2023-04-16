package com.co.alianza.coreclient.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TechinicalErrorEnum {
    DB_TRANSACTION_ERROR("Error processing the transaction in the database", HttpStatus.INTERNAL_SERVER_ERROR),
    SERVICE_NOT_AVAILABLE("The service is not available or has a failure", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String value;
    private final HttpStatus httpStatus;
}
