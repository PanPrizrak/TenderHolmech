package com.holmech.tender.application.service;

import com.holmech.tender.application.entity.Worker;
import com.holmech.tender.application.repository.WorkerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkerService {
    private WorkerRepository workerRepository;

    public WorkerService(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    public List<Worker> findAllMemberOfCommission(){
        List<Worker> workerfromDB = findAll();
        for(int i=0; i<workerfromDB.size();i++)
            if(!workerfromDB.get(i).getMemberofcommission())
                workerfromDB.remove(i);
        return workerfromDB;
    }

    public List<Worker> findAll(){
        return (List<Worker>) workerRepository.findAll();
    }

    public void save(Worker worker){
        workerRepository.save(worker);
    }
}
