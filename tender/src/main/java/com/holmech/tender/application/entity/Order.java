package com.holmech.tender.application.entity;

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
    private Long id;

    private String number;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    //@NotBlank(message="pleas fil date")
    private Date date;


/*@


    private Worker chairman;

    OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id")
    private Worker deputy;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id")
    private Set<Worker> memberofcommission;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id")
    private Worker secretary;*/

}
