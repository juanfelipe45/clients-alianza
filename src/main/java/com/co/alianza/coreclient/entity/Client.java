package com.co.alianza.coreclient.entity;

import com.co.alianza.coreclient.constants.DateFormatConstant;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clients")
public class Client {

    @Id
    @Column(name = "shared_key")
    private String sharedKey;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "creation_date")
    @DateTimeFormat(pattern = DateFormatConstant.DD_MM_YYYY)
    private LocalDate creationDate;

}
