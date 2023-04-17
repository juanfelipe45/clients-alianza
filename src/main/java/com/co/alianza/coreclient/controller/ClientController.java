package com.co.alianza.coreclient.controller;

import com.co.alianza.coreclient.config.ApiResponseErrorModel;
import com.co.alianza.coreclient.dto.ClientDTO;
import com.co.alianza.coreclient.dto.GeneralResponse;
import com.co.alianza.coreclient.dto.HeaderResponse;
import com.co.alianza.coreclient.dto.PageResponse;
import com.co.alianza.coreclient.exception.AlianzaClientException;
import com.co.alianza.coreclient.service.IClientService;
import com.co.alianza.coreclient.util.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.co.alianza.coreclient.constants.UrlMappingConstant.*;

@RestController
@CrossOrigin("*")
@Api(tags = "Client", protocols = "http, https")
@RequestMapping(value = API + VERSION_1 + CLIENTS)
public class ClientController {

    private final IClientService clientService;

    public ClientController(IClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping(value = CLIENTS_ALL)
    @ApiResponseErrorModel
    @ApiOperation("Get Clients with pagination and order")
    public ResponseEntity<GeneralResponse<List<ClientDTO>>> findAll() throws AlianzaClientException {
        return ResponseEntity.ok(new GeneralResponse<>(
                new HeaderResponse(200, ""),
                this.clientService.findAll()
        ));
    }
    @GetMapping(value = CLIENTS_PAGE)
    @ApiResponseErrorModel
    @ApiOperation("Get Clients with pagination and order")
    public ResponseEntity<GeneralResponse<PageResponse<ClientDTO>>> searchClients(
            @RequestParam(required = false) String sharedKey,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String fromCreationDate,
            @RequestParam(required = false) String toCreationDate,
            @RequestParam(defaultValue = "0") int pageIndex,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "creationDate") String sortField,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder) throws AlianzaClientException {
        return ResponseEntity.ok(new GeneralResponse<>(
                new HeaderResponse(200, ""),
                this.clientService.getClient(sharedKey, name, email, phone, DateUtil.formatStringToLocaleDate(fromCreationDate), DateUtil.formatStringToLocaleDate(toCreationDate), pageIndex, pageSize, sortField, sortOrder)
        ));
    }

    @PostMapping
    @ApiResponseErrorModel
    @ApiOperation("Save client")
    public ResponseEntity<GeneralResponse<ClientDTO>> saveClient(@RequestBody ClientDTO client) throws AlianzaClientException {
        return ResponseEntity.ok(new GeneralResponse<>(
                new HeaderResponse(200, ""),
                this.clientService.create(client)
        ));
    }

    @PutMapping
    @ApiResponseErrorModel
    @ApiOperation("update client")
    public ResponseEntity<GeneralResponse<ClientDTO>> updateClient(@RequestBody ClientDTO client) throws AlianzaClientException {
        return ResponseEntity.ok(new GeneralResponse<>(
                new HeaderResponse(200, ""),
                this.clientService.update(client)
        ));
    }
}
