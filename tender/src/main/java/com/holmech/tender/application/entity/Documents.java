package com.holmech.tender.application.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;

@Data
@Entity
public class Documents {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idD;

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

    public String thereAreNoTheseDocuments(){
        String buf = " ";
        int bufInt = 0;
        if(!registration) { buf = buf.concat("свидетельство о регистрации"); bufInt ++;}
        if(!charter) { if(bufInt!=0) {buf =buf.concat(", ");} buf = buf.concat("устав"); bufInt ++;}
        if(!bankreference) { if(bufInt!=0) {buf = buf.concat(", ");} buf = buf.concat("справку из банка"); bufInt ++;}
        if(!dealer) { if(bufInt!=0) {buf = buf.concat(", ");} buf = buf.concat("дилерский сертификат"); bufInt ++;}
        if(!product) { if(bufInt!=0) {buf = buf.concat(", ");} buf = buf.concat("сертификат на товар"); bufInt ++;}
        if(!feedback) { if(bufInt!=0) {buf = buf.concat(", ");} buf = buf.concat("отзывы"); bufInt ++;}
        return buf;
    }

}
