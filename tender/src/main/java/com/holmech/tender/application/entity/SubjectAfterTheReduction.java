package com.holmech.tender.application.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SubjectAfterTheReduction {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long idSa;

    private Double price;
    private String payment; //collection

    @OneToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

}
