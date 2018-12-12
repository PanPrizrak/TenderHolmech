package com.holmech.tender.application.service;

import com.holmech.tender.application.entity.Order;
import com.holmech.tender.application.entity.Worker;
import com.holmech.tender.application.entity.WorkerR;
import com.holmech.tender.application.entity.WorkerRole;
import com.holmech.tender.application.form.MemberCommissionForm;
import com.holmech.tender.application.repository.WorkerRReposytory;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class WorkerRService {
    private List<WorkerR> workerRList;
    private Order bufOrder;
    private final WorkerRReposytory workerRReposytory;

    public List<WorkerR> findByOrder(Order order){
        return workerRReposytory.findByOrder(order);
    }

    public WorkerRService(WorkerRReposytory workerRReposytory) {

        this.workerRReposytory = workerRReposytory;
    }

    public void saveMembersCommission(Order order, MemberCommissionForm memberCommissionForm) {
        setBufOrder(order);
        addTheChairman(memberCommissionForm.getThechairman());
        addViceChairman(memberCommissionForm.getVicechairman());
        addSecretary(memberCommissionForm.getSecretary());
        addCommissionMembers(memberCommissionForm.getCommissionmember());
        saveAll();
    }

    private void addTheChairman(Worker bufWorker) {
        workerRList.add(new WorkerR(bufOrder, bufWorker, WorkerRole.THECHAIRMAN));
    }

    private void addViceChairman(Worker bufWorker) {
        workerRList.add(new WorkerR(bufOrder, bufWorker, WorkerRole.VICECHAIRMAN));
    }

    private void addSecretary(Worker bufWorker) {
        workerRList.add(new WorkerR(bufOrder, bufWorker, WorkerRole.SECRETARY));
    }

    private void addCommissionMembers(List<Worker> bufWorkerList) {
        for (Worker bufWorker : bufWorkerList) {
            workerRList.add(new WorkerR(bufOrder, bufWorker, WorkerRole.COMMISSIONMEMBER));
        }
    }

    private void saveAll() {
        for (WorkerR bufWorkeR : workerRList) {
            save(bufWorkeR);
        }
    }

    public void save(WorkerR workerR) {
        workerRReposytory.save(workerR);
    }
}
