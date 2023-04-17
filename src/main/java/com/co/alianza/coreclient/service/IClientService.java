package com.co.alianza.coreclient.service;

import com.co.alianza.coreclient.dto.ClientDTO;
import com.co.alianza.coreclient.dto.PageResponse;
import com.co.alianza.coreclient.exception.AlianzaClientException;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;

public interface IClientService {

    ClientDTO create(ClientDTO client) throws AlianzaClientException;
    ClientDTO update(ClientDTO client) throws AlianzaClientException;

    PageResponse<ClientDTO> getClient(String sharedKey,  String name, String email,
            String phone, LocalDate fromCreationDate, LocalDate toCreationDate,
            int pageIndex, int pageSize, String sortField, Sort.Direction sortOrder) throws AlianzaClientException;

    List<ClientDTO> findAll() throws AlianzaClientException;
}
