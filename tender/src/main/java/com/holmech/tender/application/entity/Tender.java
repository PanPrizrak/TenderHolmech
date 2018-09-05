package com.holmech.tender.application.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@Entity
public class Tender {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NotBlank(message="pleas fil number")
    //@Length(max = 255, message = "max long")
    private String number;

    @NotBlank(message="pleas fil date")
    private String date;

    @NotBlank(message="pleas fil name")
    private String name;

    @NotBlank(message="pleas fil stage")
    private String stage;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oreder_id")
    private Order order;


}
