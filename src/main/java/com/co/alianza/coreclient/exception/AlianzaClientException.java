package com.co.alianza.coreclient.exception;

import com.co.alianza.coreclient.enums.BusinessErrorEnum;
import com.co.alianza.coreclient.enums.TechinicalErrorEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class AlianzaClientException extends Exception{

    private final int businessCode;
    private final String businessMessage;
    private final HttpStatus httpStatus;

    public AlianzaClientException(BusinessErrorEnum businessErrorEnum, Exception e) {
        super(e);
        this.businessCode = businessErrorEnum.getHttpStatus().value();
        this.businessMessage = businessErrorEnum.getMessage();
        this.httpStatus = businessErrorEnum.getHttpStatus();
    }

    public AlianzaClientException(BusinessErrorEnum businessErrorEnum) {
        super();
        this.businessCode = businessErrorEnum.getHttpStatus().value();
        this.businessMessage = businessErrorEnum.getMessage();
        this.httpStatus = businessErrorEnum.getHttpStatus();
    }

    public AlianzaClientException(TechinicalErrorEnum techinicalErrorEnum, Exception e) {
        super(e);
        this.businessCode = techinicalErrorEnum.getHttpStatus().value();
        this.businessMessage = techinicalErrorEnum.getValue();
        this.httpStatus = techinicalErrorEnum.getHttpStatus();
    }
}
