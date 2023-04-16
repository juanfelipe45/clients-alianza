package com.co.alianza.coreclient.service.impl;

import com.co.alianza.coreclient.dto.ClientDTO;
import com.co.alianza.coreclient.dto.PageResponse;
import com.co.alianza.coreclient.entity.Client;
import com.co.alianza.coreclient.entity.ClientSpecification;
import com.co.alianza.coreclient.enums.BusinessErrorEnum;
import com.co.alianza.coreclient.enums.TechinicalErrorEnum;
import com.co.alianza.coreclient.exception.AlianzaClientException;
import com.co.alianza.coreclient.repository.IClientRepository;
import com.co.alianza.coreclient.service.IClientService;
import com.co.alianza.coreclient.util.DateUtil;
import com.co.alianza.coreclient.util.EntityDtoMapper;
import com.co.alianza.coreclient.util.LoggerUtil;
import com.co.alianza.coreclient.util.Util;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements IClientService {

    private final EntityDtoMapper entityDtoMapper;
    private final IClientRepository clientRepository;

    private final LoggerUtil<ClientServiceImpl> logger = LoggerUtil.getLogger(ClientServiceImpl.class);

    public ClientServiceImpl(IClientRepository clientRepository, EntityDtoMapper entityDtoMapper) {
        this.entityDtoMapper = entityDtoMapper;
        this.clientRepository = clientRepository;
    }

    @Override
    @Transactional
    public ClientDTO create(ClientDTO client) throws AlianzaClientException {
        String methodComponent = Thread.currentThread().getStackTrace()[1].getMethodName();

        this.validatePostRequest(client, methodComponent);
        return  this.saveClient(client, methodComponent);
    }

    @Override
    @Transactional
    public ClientDTO update(ClientDTO client) throws AlianzaClientException {
        String methodComponent = Thread.currentThread().getStackTrace()[1].getMethodName();

        this.validatePutRequest(client, methodComponent);
        return  this.saveClient(client, methodComponent);

    }

    @Override
    public PageResponse<ClientDTO> getClient(String sharedKey, String name, String email,
                                          String phone, LocalDate fromCreationDate, LocalDate toCreationDate,
                                          int pageIndex, int pageSize, String sortField, Sort.Direction sortOrder) throws AlianzaClientException {

        String methodComponent = Thread.currentThread().getStackTrace()[1].getMethodName();

        long startTime = System.currentTimeMillis();
        long startTimeConsult;
        long endTimeConsult ;

        this.isValidParamsGetClient(sharedKey, name, email, phone, fromCreationDate, toCreationDate, methodComponent);

        logger.info(LoggerUtil.getLogStartMethod(methodComponent, "sharedKey:" + sharedKey,
                "name:" + name, "email:" + email, "phone:" + phone, "fromCreationDate:" + fromCreationDate,
                "toCreationDate:" + toCreationDate, "pageIndex:" + pageIndex, "pageSize:" + pageSize,
                "sortField:" + sortField, "sortOrder:" + sortOrder));

        Map<String, String> params = new HashMap<>();
        params.put("sharedKey", sharedKey);
        params.put("name", name);
        params.put("email", email);
        params.put("phone", phone);
        params.put("creationDateFrom", DateUtil.formatLocaleDateToString(fromCreationDate));
        params.put("creationDateTo", DateUtil.formatLocaleDateToString(toCreationDate));

        Pageable pageable = PageRequest.of(pageIndex, pageSize, sortOrder, sortField);
        Specification<Client> specification = new ClientSpecification(params);

        startTimeConsult = System.currentTimeMillis();
        Page<Client> pageClient = clientRepository.findAll(specification, pageable);
        endTimeConsult = System.currentTimeMillis();

        List<ClientDTO> clients = pageClient.get()
                                            .map(client -> entityDtoMapper.toDto(client, ClientDTO.class))
                                            .collect(Collectors.toList());

        logger.info("Execution method time:" + (System.currentTimeMillis() - startTime) );
        logger.info("Execution consult time: " +  (endTimeConsult - startTimeConsult));

        return new PageResponse<>(clients, pageClient.getTotalElements());
    }

    private void isValidParamsGetClient(String sharedKey, String name, String email,
                                            String phone, LocalDate fromCreationDate, LocalDate toCreationDate,
                                            String methodComponent) throws AlianzaClientException {

        if (Util.validateEmptyParams(sharedKey, name, email, phone, fromCreationDate, toCreationDate)) {
            logger.error(LoggerUtil.generateErrorLog(methodComponent, BusinessErrorEnum.PARAMS_REQUIRED.getMessage()));
            throw new AlianzaClientException(BusinessErrorEnum.PARAMS_REQUIRED);
        }

        if (
                (Util.isNullOrEmptyObject(toCreationDate) && !Util.isNullOrEmptyObject(fromCreationDate)) ||
                (!Util.isNullOrEmptyObject(toCreationDate) && Util.isNullOrEmptyObject(fromCreationDate))) {
            logger.error(LoggerUtil.generateErrorLog(methodComponent, BusinessErrorEnum.DATES_REQUIRED.getMessage()));
            throw new AlianzaClientException(BusinessErrorEnum.DATES_REQUIRED);
        }
    }

    private boolean existSharedKey(String sharedKey) {
        Optional<Client> clientOptional = this.clientRepository.findById(sharedKey);
        return clientOptional.isPresent();
    }

    private void validatePostRequest(ClientDTO client,String methodComponent) throws AlianzaClientException {

        this.validateRequiredParams(client, methodComponent);

        if (existSharedKey(client.getSharedKey())) {
            logger.error(LoggerUtil.generateErrorLog(methodComponent, BusinessErrorEnum.EXIST_SHARED_KEY.getMessage()));
            throw new AlianzaClientException(BusinessErrorEnum.EXIST_SHARED_KEY);
        }
    }

    private void validatePutRequest(ClientDTO client,String methodComponent) throws AlianzaClientException {

        this.validateRequiredParams(client, methodComponent);

        if (!existSharedKey(client.getSharedKey())) {
            logger.error(LoggerUtil.generateErrorLog(methodComponent, BusinessErrorEnum.NO_EXIST_SHARED_KEY.getMessage()));
            throw new AlianzaClientException(BusinessErrorEnum.NO_EXIST_SHARED_KEY);
        }
    }

    private void validateRequiredParams(ClientDTO client,String methodComponent) throws AlianzaClientException {
        if (!Util.validRequiredParams(
                client.getSharedKey(),
                client.getName(),
                client.getEmail(),
                client.getCreationDate())) {
            logger.error(LoggerUtil.generateErrorLog(methodComponent, BusinessErrorEnum.INVALID_REQUEST.getMessage()));
            throw new AlianzaClientException(BusinessErrorEnum.INVALID_REQUEST);
        }
    }

    private void generateLogsTime(long startTime, long endTimeConsult, long startTimeConsult) {
        logger.info("Execution method time:" + (System.currentTimeMillis() - startTime) );
        logger.info("Execution consult time: " +  (endTimeConsult - startTimeConsult));
    }

    private void generateInitialLog(ClientDTO client, String methodComponent) {
        logger.info(LoggerUtil.getLogStartMethod(methodComponent, "sharedKey:" + client.getSharedKey(),
                "name:" + client.getName(), "email:" + client.getEmail(), "phone:" + client.getPhone(),
                "creationDate:" + client.getCreationDate()));
    }

    private ClientDTO saveClient(ClientDTO client, String methodComponent) throws AlianzaClientException {
        long startTime = System.currentTimeMillis();
        long startTimeConsult = 0;
        long endTimeConsult;

        this.generateInitialLog(client, methodComponent);

        Client clientUpdate;

        try {
            startTimeConsult = System.currentTimeMillis();
            clientUpdate = this.clientRepository.save(entityDtoMapper.toEntity(client, Client.class));

        } catch (Exception e) {
            logger.error(LoggerUtil.generateErrorLog(methodComponent, TechinicalErrorEnum.DB_TRANSACTION_ERROR.getValue()), e);
            throw new AlianzaClientException(TechinicalErrorEnum.DB_TRANSACTION_ERROR, e);
        } finally {
            endTimeConsult = System.currentTimeMillis();
            this.generateLogsTime(startTime, endTimeConsult, startTimeConsult);
        }

        return entityDtoMapper.toDto(clientUpdate, ClientDTO.class);
    }
}
