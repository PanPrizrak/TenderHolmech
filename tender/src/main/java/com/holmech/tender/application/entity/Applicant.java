package com.holmech.tender.application.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class Applicant {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long idA;


    private  String nameA;
    private  String address;
    private  String pan;

    @Transient
    private ArrayList<Integer> lots;


    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    private List<Contacts> contactsList;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id")
    private List<Worker> workerList;


}
