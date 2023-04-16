package com.co.alianza.coreclient.controller.exceptionhandler;

import com.co.alianza.coreclient.dto.ResponseErrorModel;
import com.co.alianza.coreclient.enums.BusinessErrorEnum;
import com.co.alianza.coreclient.exception.AlianzaClientException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class ExceptionHandlerControllerTest {

    @Autowired
    private ExceptionHandlerController exceptionHandlerController;

    private final String MOCKED_EXCEPTION = "Mocked Exception";

    @Test
    @DisplayName("Given a error response model, when calling handleAlianzaClientException()")
    void given_error_response_model_in_handleAlianzaClientException() {
        AlianzaClientException alianzaClientExceptionMock = new AlianzaClientException(
                BusinessErrorEnum.SEARCH_NOT_FOUND, new Exception(MOCKED_EXCEPTION));

        ResponseEntity<ResponseErrorModel> errorResponse = this.exceptionHandlerController.handleAlianzaClientException(alianzaClientExceptionMock);

        assertNotNull(errorResponse);
        assertNotNull(errorResponse.getBody());
        assertNotNull(errorResponse.getBody().getErrorHeader());
        assertNotNull(errorResponse.getBody().getErrorDetail());
    }

    @Test
    @DisplayName("Given a error response model, when calling handleSQLException()")
    void given_error_response_model_in_handleSQLException() {
        SQLException sqlExceptionMocked = new SQLException(MOCKED_EXCEPTION);

        ResponseEntity<ResponseErrorModel> errorResponse = this.exceptionHandlerController.handleSQLException(sqlExceptionMocked);

        assertNotNull(errorResponse);
        assertNotNull(errorResponse.getBody());
        assertNotNull(errorResponse.getBody().getErrorHeader());
        assertNotNull(errorResponse.getBody().getErrorDetail());
    }

    @Test
    @DisplayName("Given a error response model, when calling handleConversationFailedException()")
    void given_error_response_model_in_handleConversationFailedException() {
        Exception exception = new Exception(MOCKED_EXCEPTION);

        ResponseEntity<ResponseErrorModel> errorResponse = this.exceptionHandlerController.handleException(exception);

        assertNotNull(errorResponse);
        assertNotNull(errorResponse.getBody());
        assertNotNull(errorResponse.getBody().getErrorHeader());
        assertNotNull(errorResponse.getBody().getErrorDetail());
    }
}
