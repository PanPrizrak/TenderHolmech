package com.holmech.tender.application.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String position;
    private String surname;
    private String name;
    private String patronymic;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    private Set<Contacts> contactsSet;

}
