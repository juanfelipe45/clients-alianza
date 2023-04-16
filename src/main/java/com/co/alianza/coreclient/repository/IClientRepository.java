package com.co.alianza.coreclient.repository;

import com.co.alianza.coreclient.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IClientRepository extends JpaRepository<Client, String>, JpaSpecificationExecutor<Client> {
}
