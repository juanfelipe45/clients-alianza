package com.co.alianza.coreclient.controller.exceptionhandler;

import com.co.alianza.coreclient.dto.ErrorDetail;
import com.co.alianza.coreclient.dto.HeaderResponse;
import com.co.alianza.coreclient.dto.ResponseErrorModel;
import com.co.alianza.coreclient.enums.TechinicalErrorEnum;
import com.co.alianza.coreclient.exception.AlianzaClientException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.util.Date;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = AlianzaClientException.class)
    public @ResponseBody ResponseEntity<ResponseErrorModel> handleAlianzaClientException(AlianzaClientException e) {

        return createErrorResponse(
                e.getHttpStatus().value(),
                e.getHttpStatus().getReasonPhrase(),
                e.getBusinessMessage(),
                e,
                e.getHttpStatus()
        );
    }

    @ExceptionHandler(value = SQLException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ResponseEntity<ResponseErrorModel> handleSQLException(SQLException e) {

        return createErrorResponse(
                TechinicalErrorEnum.DB_TRANSACTION_ERROR.getHttpStatus().value(),
                TechinicalErrorEnum.DB_TRANSACTION_ERROR.getHttpStatus().getReasonPhrase(),
                TechinicalErrorEnum.DB_TRANSACTION_ERROR.getValue(),
                e,
                TechinicalErrorEnum.DB_TRANSACTION_ERROR.getHttpStatus()
        );
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ResponseEntity<ResponseErrorModel> handleException(Exception e) {

        return createErrorResponse(
                TechinicalErrorEnum.SERVICE_NOT_AVAILABLE.getHttpStatus().value(),
                TechinicalErrorEnum.SERVICE_NOT_AVAILABLE.getHttpStatus().getReasonPhrase(),
                TechinicalErrorEnum.SERVICE_NOT_AVAILABLE.getValue(),
                e,
                TechinicalErrorEnum.SERVICE_NOT_AVAILABLE.getHttpStatus()
        );
    }

    private ResponseEntity<ResponseErrorModel> createErrorResponse(
            int returnCode,
            String message,
            String detailMessage,
            Exception e,
            HttpStatus httpStatus) {
        return new ResponseEntity<>(
                new ResponseErrorModel(
                        new HeaderResponse(returnCode, message),
                        new ErrorDetail(detailMessage, e.getLocalizedMessage(), new Date())
                ),
                httpStatus
        );
    }
}
