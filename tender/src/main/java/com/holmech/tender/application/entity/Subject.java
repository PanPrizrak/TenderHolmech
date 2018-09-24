package com.holmech.tender.application.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Subject {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    private String nameS;
    private String units;
    private Integer amount;
    private Double price;
    private String code;
    private String delivery; //collection
    private String payment; //collection
    private Integer numberS;//number lot subject

    @ManyToOne
    @JoinColumn (name = "tender_id")
    private Tender tender;

    @ManyToOne
    @JoinColumn(name = "applicant_id")
    private Applicant applicant;

}
