package com.co.alianza.coreclient.config;

import com.co.alianza.coreclient.dto.ClientDTO;
import com.co.alianza.coreclient.service.IClientService;
import com.co.alianza.coreclient.util.DateUtil;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@Profile("default")
public class DataLoader implements ApplicationRunner {

    private final IClientService clientService;

    public DataLoader(IClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<ClientDTO> clients = new ArrayList<>();
        String phone = "3219876543";
        LocalDate date = DateUtil.formatStringToLocaleDate("20/05/2019");

        clients.add( new ClientDTO("jgutierrez",
                "Julian Gutierrez",
                "jgutierrez@gmail.com",
                phone,
                date));

        clients.add( new ClientDTO("mmartinez",
                "Manuel Martinez",
                "jmmartinez@gmail.com",
                phone,
                date));

        clients.add( new ClientDTO("aruiz",
                "Ana Ruiz",
                "aruiz@gmail.com",
                phone,
                date));

        clients.add( new ClientDTO("ogarcia",
                "Oscar García",
                "ogarcia@gmail.com",
                phone,
                date));

        clients.add( new ClientDTO("tramos",
                "Tania Ramos",
                "tramos@gmail.com",
                phone,
                date));

        clients.add( new ClientDTO("cariza",
                "Carlos Ariza",
                "cariza@gmail.com",
                phone,
                date));

        clients.add( new ClientDTO("rvillaneda",
                "Rodrigo Villaneda",
                "rvillaneda@grnail.com",
                phone,
                date));

        clients.add( new ClientDTO("mfonseca",
                "Mauricio Fonseca",
                "mfonseca@gmail.com",
                phone,
                date));

        for (ClientDTO client : clients) {
            this.clientService.create(client);
        }
    }
}
