package com.holmech.tender.application.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity(name = "worker_role")//Описание ролей работника если он член комисии
public class WorkerR {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idWR;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToOne
    @JoinColumn(name = "worker_id")
    private Worker worker;

    private WorkerRole role;

    public WorkerR(Order order, Worker worker, WorkerRole role) {
        this.order = order;
        this.worker = worker;
        this.role = role;
    }
}
