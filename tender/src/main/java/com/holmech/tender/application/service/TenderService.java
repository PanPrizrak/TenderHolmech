package com.holmech.tender.application.service;

import com.holmech.tender.application.entity.Order;
import com.holmech.tender.application.entity.Tender;
import com.holmech.tender.application.repository.OrderRepository;
import com.holmech.tender.application.repository.TenderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class TenderService {

    @Value("${upload.path}")
    private String uploadPath;

    private final OrderRepository orderRepository;
    private final TenderRepository tenderRepository;

    public TenderService(OrderRepository orderRepository, TenderRepository tenderRepository) {
        this.orderRepository = orderRepository;
        this.tenderRepository = tenderRepository;
    }

    public void saveTender(@RequestParam(required = false, name = "file") MultipartFile file, Tender tenderBuf) throws IOException {
        Order orderBuf = tenderBuf.getOrder();
        if (tenderBuf.getIdT() != null) {
            orderBuf.setIdO(this.findById(tenderBuf.getIdT()).getOrder().getIdO());
            orderRepository.save(orderBuf);
        }
        orderRepository.save(orderBuf);
        tenderBuf.setOrder(orderRepository.findLastOrder());
        saveFile(tenderBuf, file);
        tenderRepository.save(tenderBuf);
    }


    private void saveFile(Tender tender, @RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {//getOriginalFilename work  only in chrome
            System.out.println(file.getName().toString() + " getOrigi" + file.getOriginalFilename());
            File uploadDir = new File(uploadPath);

            if (!uploadDir.isDirectory()) {
                uploadDir.mkdirs();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            tender.setFilename(resultFilename);
        }
    }

    public Tender findById(Long id) {
        return tenderRepository.findById(id).get();
    }

    public List<Tender> findAll() {
        return (List<Tender>) tenderRepository.findAll();
    }

    public Tender findByNumberT(String numberT) {
        return tenderRepository.findByNumberT(numberT);
    }
}
