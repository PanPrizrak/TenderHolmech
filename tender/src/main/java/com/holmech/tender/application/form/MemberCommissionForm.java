package com.holmech.tender.application.form;

import com.holmech.tender.application.entity.Worker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberCommissionForm {
    private Worker thechairman;
    private Worker vicechairman;
    private Worker secretary;
    private List<Worker> commissionmember;
}
