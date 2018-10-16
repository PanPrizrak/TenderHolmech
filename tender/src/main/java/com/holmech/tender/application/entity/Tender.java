package com.holmech.tender.application.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
public class Tender {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long idT;

    //@NotBlank(message="pleas fil number")
    //@Length(max = 255, message = "max long")
    private String numberT;

    //@NotBlank(message="pleas fil name")
    private String nameT;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    //@NotBlank(message="pleas fil date")
    private Date dateT;

    //@NotBlank(message="pleas fil stage")
    private String stage;

    //@NotBlank(message="pleas fil priceFactor")
    private String priceFactor;

    //@NotBlank(message="pleas fil paymentFactor")
    private String paymentFactor;

    private String filename;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Transient
    private boolean documents;

}
