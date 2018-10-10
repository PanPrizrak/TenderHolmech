package com.holmech.tender.application.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idO;

    private String numberO;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    //@NotBlank(message="pleas fil date")
    private Date dateO;


    public Order(String numberO, Date dateO) {
        this.numberO = numberO;
        this.dateO = dateO;
    }
}
