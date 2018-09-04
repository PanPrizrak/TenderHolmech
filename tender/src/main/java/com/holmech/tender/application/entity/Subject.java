package com.holmech.tender.application.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class Subject {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String units;
    private Integer amount;
    private Double price;
    private String code;
    private String delivery; //collection
    private String payment; //collection

    @ManyToOne
    @JoinColumn (name = "tender_id")
    private Tender tender;
}
