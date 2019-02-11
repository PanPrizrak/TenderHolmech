package com.holmech.tender.application.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idW;

    private String position;
    private String surname;
    private String nameW;
    private String patronymic;
    private Boolean memberofcommission;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "workwer_id")
    private List<Contacts> contactsList;


    public String getSNPWorker(){
        String SNPorker = this.getSurname() + " " + getInitialsWorker();
        return SNPorker;
    }

    public String getNPSWorker(){
        String NPSWorker = getInitialsWorker() + " " + this.getSurname();
        return NPSWorker;
    }



    private String getInitialsWorker(){
        String initials = this.getNameW().charAt(0) + ". " + this.getPatronymic().charAt(0) + ".";
        return initials;
    }



}
