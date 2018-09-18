package com.holmech.tender.application.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;

@Data
@Entity
public class Documents {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private boolean registration;

    private boolean charter;

    private boolean bankreference;
    private boolean dealer;
    private boolean product;
    private boolean feedback;

    @ManyToOne
    @JoinColumn(name = "applicant_id")
    private Applicant applicant;

    @ManyToOne
    @JoinColumn(name = "tender_id")
    private Tender tender;
}
