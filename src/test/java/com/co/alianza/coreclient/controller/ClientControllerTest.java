package com.co.alianza.coreclient.controller;

import com.co.alianza.coreclient.dto.ClientDTO;
import com.co.alianza.coreclient.dto.GeneralResponse;
import com.co.alianza.coreclient.dto.PageResponse;
import com.co.alianza.coreclient.entity.Client;
import com.co.alianza.coreclient.entity.ClientSpecification;
import com.co.alianza.coreclient.enums.BusinessErrorEnum;
import com.co.alianza.coreclient.enums.TechinicalErrorEnum;
import com.co.alianza.coreclient.exception.AlianzaClientException;
import com.co.alianza.coreclient.repository.IClientRepository;
import com.co.alianza.coreclient.util.DateUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
class ClientControllerTest {

    @Autowired
    private ClientController clientController;

    @MockBean
    private IClientRepository clientRepository;

    /**
     * GET searchClients
     */

    @Test
    @DisplayName("Given a bac request, when calling searchClients() method and params are null or empty")
    void given_bad_request_in_searchClients_when_params_is_empty() {
        try {
            this.clientController.searchClients(null, null, null ,null, null, null, 0, 10, null, null);
        } catch (AlianzaClientException e) {
            assertSame(BusinessErrorEnum.PARAMS_REQUIRED.getHttpStatus(), e.getHttpStatus());
            assertSame(BusinessErrorEnum.PARAMS_REQUIRED.getMessage(), e.getBusinessMessage());
            assertEquals(BusinessErrorEnum.PARAMS_REQUIRED.getHttpStatus().value(), e.getBusinessCode());
        }
    }

    @Test
    @DisplayName("Given a bac request, when calling searchClients() method and toCreationDate is null and fromCreationDate is not null")
    void given_bad_request_in_searchClients_when_toCreationDate_is_null_and_fromCreationDate_is_not_null() {
        try {
            this.clientController.searchClients(null, null, null ,null,
                    "16/04/2023", null,
                    0, 10, null, null);
        } catch (AlianzaClientException e) {
            assertSame(BusinessErrorEnum.DATES_REQUIRED.getHttpStatus(), e.getHttpStatus());
            assertSame(BusinessErrorEnum.DATES_REQUIRED.getMessage(), e.getBusinessMessage());
            assertEquals(BusinessErrorEnum.DATES_REQUIRED.getHttpStatus().value(), e.getBusinessCode());
        }
    }

    @Test
    @DisplayName("Given a bac request, when calling searchClients() method and toCreationDate is not null and fromCreationDate is null")
    void given_bad_request_in_searchClients_when_toCreationDate_is_not_null_and_fromCreationDate_is_null() {
        try {
            this.clientController.searchClients(null, null, null ,null,
                    null, "16/04/2023",
                    0, 10, null, null);
        } catch (AlianzaClientException e) {
            assertSame(BusinessErrorEnum.DATES_REQUIRED.getHttpStatus(), e.getHttpStatus());
            assertSame(BusinessErrorEnum.DATES_REQUIRED.getMessage(), e.getBusinessMessage());
            assertEquals(BusinessErrorEnum.DATES_REQUIRED.getHttpStatus().value(), e.getBusinessCode());
        }
    }

    @Test
    @DisplayName("Given a bac request, when calling searchClients() method and toCreationDate is  null and fromCreationDate is null")
    void given_ok_in_searchClients_when_toCreationDate_is_null_and_fromCreationDate_is_null() throws AlianzaClientException {
        Client clientMock;
        Page<Client> pageClientMock;
        List<Client> clientsMock = new ArrayList<>();

        clientMock = new Client("jgutierrez",
                "Julian Gutierrez",
                "jgutierrez@gmail.com",
                "3219876543",
                DateUtil.formatStringToLocaleDate("15/04/2023"));

        clientsMock.add(clientMock);

        pageClientMock = new PageImpl<>(clientsMock);

        Mockito.when(this.clientRepository.findAll(any(ClientSpecification.class), any(Pageable.class))).thenReturn(pageClientMock);

        ResponseEntity<GeneralResponse<PageResponse<ClientDTO>>> response = this.clientController.searchClients("jgutierrez", null, null ,null,
                null, null,
                0, 10, "creationDate", Sort.Direction.ASC);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getHeader());
        assertEquals(HttpStatus.OK.value(), response.getBody().getHeader().getCode());
        assertSame(clientsMock.size(), response.getBody().getBody().getCount().intValue());
    }

    @Test
    @DisplayName("Given ok, when calling searchClients() when method have a correct params")
    void given_Ok_in_searchClients_when_params_is_correct() throws AlianzaClientException {

        Client clientMock;
        Page<Client> pageClientMock;
        List<Client> clientsMock = new ArrayList<>();

        clientMock = new Client("jgutierrez",
                "Julian Gutierrez",
                "jgutierrez@gmail.com",
                "3219876543",
                DateUtil.formatStringToLocaleDate("15/04/2023"));

        clientsMock.add(clientMock);

        pageClientMock = new PageImpl<>(clientsMock);

        Mockito.when(this.clientRepository.findAll(any(ClientSpecification.class), any(Pageable.class))).thenReturn(pageClientMock);

        ResponseEntity<GeneralResponse<PageResponse<ClientDTO>>> response = this.clientController.searchClients(null, null, null ,null,
                "12/04/2023", "15/04/2023",
                0, 10, "creationDate", Sort.Direction.ASC);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getHeader());
        assertEquals(HttpStatus.OK.value(), response.getBody().getHeader().getCode());
        assertSame(clientsMock.size(), response.getBody().getBody().getCount().intValue());

    }

    /**
     * POST saveClient
     */

    @Test
    @DisplayName("Given a bad request, when calling saveClient() when request is invalid")
    void given_bad_request_in_saveClient_when_request_is_invalid() {
        try {
            this.clientController.saveClient(new ClientDTO());
        } catch (AlianzaClientException e) {
            assertSame(BusinessErrorEnum.INVALID_REQUEST.getHttpStatus(), e.getHttpStatus());
            assertSame(BusinessErrorEnum.INVALID_REQUEST.getMessage(), e.getBusinessMessage());
            assertEquals(BusinessErrorEnum.INVALID_REQUEST.getHttpStatus().value(), e.getBusinessCode());
        }
    }

    @Test
    @DisplayName("Given a conflict, when calling saveClient() when shared key exist")
    void given_conflict_in_saveClient_when_shared_key_exist() {
        try {
            Client clientMock = new Client("jgutierrez",
                    "Julian Gutierrez",
                    "jgutierrez@gmail.com",
                    "3219876543",
                    DateUtil.formatStringToLocaleDate("15/04/2023"));

            ClientDTO clientDTOMock = new ClientDTO("jgutierrez",
                    "Julian Gutierrez",
                    "jgutierrez@gmail.com",
                    "3219876543",
                    DateUtil.formatStringToLocaleDate("15/04/2023"));


            Mockito.when(this.clientRepository.findById(anyString())).thenReturn(Optional.of(clientMock));

            this.clientController.saveClient(clientDTOMock);
        } catch (AlianzaClientException e) {
            assertSame(BusinessErrorEnum.EXIST_SHARED_KEY.getHttpStatus(), e.getHttpStatus());
            assertSame(BusinessErrorEnum.EXIST_SHARED_KEY.getMessage(), e.getBusinessMessage());
            assertEquals(BusinessErrorEnum.EXIST_SHARED_KEY.getHttpStatus().value(), e.getBusinessCode());
        }
    }

    @Test
    @DisplayName("Given a internal server error, when calling saveClient(), when save data fail")
    void given_internal_server_error_in_saveClient_when_save_fail() {
        try {

            ClientDTO clientDTOMock = new ClientDTO("jgutierrez",
                    "Julian Gutierrez",
                    "jgutierrez@gmail.com",
                    "3219876543",
                    DateUtil.formatStringToLocaleDate("15/04/2023"));

            Mockito.when(this.clientRepository.findById(anyString())).thenReturn(Optional.empty());
            Mockito.when(this.clientRepository.save(any(Client.class))).thenThrow(new DataAccessException("Error saving the client") {});

            this.clientController.saveClient(clientDTOMock);
        } catch (AlianzaClientException e) {
            assertSame(TechinicalErrorEnum.DB_TRANSACTION_ERROR.getHttpStatus(), e.getHttpStatus());
            assertSame(TechinicalErrorEnum.DB_TRANSACTION_ERROR.getValue(), e.getBusinessMessage());
            assertEquals(TechinicalErrorEnum.DB_TRANSACTION_ERROR.getHttpStatus().value(), e.getBusinessCode());
        }
    }

    @Test
    @DisplayName("Given a ok, when calling createClient(), when all is ok")
    void given_ok_in_createClient_when_all_is_ok() throws AlianzaClientException {

        Client clientMock = new Client("jgutierrez",
                "Julian Gutierrez",
                "jgutierrez@gmail.com",
                "3219876543",
                DateUtil.formatStringToLocaleDate("15/04/2023"));

        ClientDTO clientDTOMock = new ClientDTO("jgutierrez",
                "Julian Gutierrez",
                "jgutierrez@gmail.com",
                "3219876543",
                DateUtil.formatStringToLocaleDate("15/04/2023"));

        Mockito.when(this.clientRepository.findById(anyString())).thenReturn(Optional.empty());
        Mockito.when(this.clientRepository.save(any(Client.class))).thenReturn(clientMock);

        ResponseEntity<GeneralResponse<ClientDTO>> response = this.clientController.saveClient(clientDTOMock);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getHeader());
        assertEquals(HttpStatus.OK.value(), response.getBody().getHeader().getCode());
    }

    /**
     * POST saveClient
     */

    @Test
    @DisplayName("Given a conflict, when calling updateClient(), when shared key no exist")
    void given_conflict_in_updateClient_when_shared_key_no_exist() {
        try {

            ClientDTO clientDTOMock = new ClientDTO("jgutierrez",
                    "Julian Gutierrez",
                    "jgutierrez@gmail.com",
                    "3219876543",
                    DateUtil.formatStringToLocaleDate("15/04/2023"));

            Mockito.when(this.clientRepository.findById(anyString())).thenReturn(Optional.empty());
            this.clientController.updateClient(clientDTOMock);
        } catch (AlianzaClientException e) {
            assertSame(BusinessErrorEnum.NO_EXIST_SHARED_KEY.getHttpStatus(), e.getHttpStatus());
            assertSame(BusinessErrorEnum.NO_EXIST_SHARED_KEY.getMessage(), e.getBusinessMessage());
            assertEquals(BusinessErrorEnum.NO_EXIST_SHARED_KEY.getHttpStatus().value(), e.getBusinessCode());
        }
    }

    @Test
    @DisplayName("Given a ok, when calling updateClient(), when all is ok")
    void given_ok_in_updateClient_when_all_is_ok() throws AlianzaClientException {

        Client clientMock = new Client("jgutierrez",
                "Julian Gutierrez",
                "jgutierrez@gmail.com",
                "3219876543",
                DateUtil.formatStringToLocaleDate("15/04/2023"));

        ClientDTO clientDTOMock = new ClientDTO("jgutierrez",
                "Julian Gutierrez",
                "jgutierrez@gmail.com",
                "3219876543",
                DateUtil.formatStringToLocaleDate("15/04/2023"));

        Mockito.when(this.clientRepository.findById(anyString())).thenReturn(Optional.of(clientMock));
        Mockito.when(this.clientRepository.save(any(Client.class))).thenReturn(clientMock);

        ResponseEntity<GeneralResponse<ClientDTO>> response = this.clientController.updateClient(clientDTOMock);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getHeader());
        assertEquals(HttpStatus.OK.value(), response.getBody().getHeader().getCode());
    }
}
