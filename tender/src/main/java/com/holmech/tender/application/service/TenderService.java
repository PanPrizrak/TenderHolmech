package com.holmech.tender.application.service;

import com.holmech.tender.application.entity.Applicant;
import com.holmech.tender.application.entity.Order;
import com.holmech.tender.application.entity.Subject;
import com.holmech.tender.application.entity.Tender;
import com.holmech.tender.application.entity.calculations.Znach;
import com.holmech.tender.application.parser.fromexcel.ApplicantParseExcel;
import com.holmech.tender.application.repository.OrderRepository;
import com.holmech.tender.application.repository.TenderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class TenderService {

    @Value("${upload.path}")
    private String uploadPath;

    private final OrderRepository orderRepository;
    private final TenderRepository tenderRepository;
    private final DocumentsService documentsService;
    private final ApplicantService applicantService;
    private final SubjectService subjectService;

    public TenderService(OrderRepository orderRepository,
                         TenderRepository tenderRepository,
                         DocumentsService documentsService,
                         ApplicantService applicantService,
                         SubjectService subjectService) {
        this.orderRepository = orderRepository;
        this.tenderRepository = tenderRepository;
        this.documentsService = documentsService;
        this.applicantService = applicantService;
        this.subjectService = subjectService;
    }

    public void updateTender(Tender tender){
        tenderRepository.save(tender);
    }

    public void saveTender(@RequestParam(required = false, name = "file") MultipartFile file, Tender tenderBuf) throws IOException {
        Order orderBuf = tenderBuf.getOrder();
        if (tenderBuf.getIdT() != null) {
            Tender tenderFromDB = tenderRepository.findById(tenderBuf.getIdT()).get();
            Long idBuf = tenderFromDB.getOrder().getIdO();
            orderBuf.setIdO(idBuf);
            orderRepository.save(orderBuf);
        } else {
            orderRepository.save(orderBuf);
            Order orderFromDBBuf = orderRepository.findByNumberO(orderBuf.getNumberO());
            tenderBuf.setOrder(orderFromDBBuf);
        }
        if (!file.isEmpty()) {
            saveFile(tenderBuf, file);
            tenderRepository.save(tenderBuf);
            ImportFromExcel(tenderRepository.findByNumberT(tenderBuf.getNumberT()));
        } else {
            tenderRepository.save(tenderBuf);
        }
    }


    private void saveFile(Tender tender, @RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {//getOriginalFilename work  only in chrome
            File uploadDir = new File(uploadPath);
            if (!uploadDir.isDirectory()) {
                uploadDir.mkdirs();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + resultFilename));
            tender.setFilename(resultFilename);
        }
    }

    private void ImportFromExcel(Tender bufTender) throws IOException {
        String bufPath = new String(uploadPath + bufTender.getFilename());
        if (!documentsService.isDocuments(bufTender)) {
            ArrayList<Applicant> applicantArrayList = ApplicantParseExcel.parse(new File(bufPath));
            applicantService.addApplicants(applicantArrayList);//save applicants
            if (documentsService.addDocumentsFromExcel(bufTender, applicantArrayList)) {//save in documents
                subjectService.addSubjectFromExcel(bufTender, applicantArrayList);
            }
        }
    }

    public Tender findById(Long id) {
        return tenderRepository.findById(id).get();
    }

    public List<Tender> findAll() {
        List<Tender> tenderList = (List<Tender>) tenderRepository.findAll();
        for (Tender tender : tenderList) {
            if (documentsService.isDocuments(tender)) {
                tender.setDocuments(true);
            }
        }
        return tenderList;
    }

    public Tender findByNumberT(String numberT) {
        return tenderRepository.findByNumberT(numberT);
    }

    private ArrayList<Znach> findingExternsPrices(Tender tender){
        //Определини экстернов значений
        List<Subject> meetSubjectList = new ArrayList<>();
        if(!meetSubjectList.isEmpty())meetSubjectList.clear();
        meetSubjectList = subjectService.getMeetSubject(tender);
        ArrayList<Znach> znachs = new ArrayList<Znach>();
        Znach znach;
        int pos = 0;


        //сортировка по номеру лота
        meetSubjectList.sort((o1, o2) -> {
            if (o1.getNumberS() == o2.getNumberS()) return 0;
            else if (o1.getNumberS()> o2.getNumberS()) return 1;
            else return -1;
        });

        //нахождения лота с максимальным номером на который поступили предложения
        Subject subjectMaxNumberS = new Subject();
                subjectMaxNumberS = meetSubjectList.stream()
                .max((fc1, fc2) -> fc1.getNumberS() - fc2.getNumberS())
                .get();

        for (int i = 1; i <= subjectMaxNumberS.getNumberS(); i++) {

            List<Double> bufPrice = new ArrayList<>();
            if(bufPrice.isEmpty()) bufPrice.clear();
            for (Subject subject: meetSubjectList) {
                if(subject.getNumberS()==i)
                    bufPrice.add(subject.getPrice());
            }

            if(!bufPrice.isEmpty()) {
                float minC = (float) bufPrice.stream().mapToDouble(d->d).min().getAsDouble();
                float maxC = bufPrice.stream().max((max1, max2) -> (int) (max1 - max2)).get().floatValue();
                /*int minO = Integer.parseInt(bufPrice.stream().
                        min((min1, min2) -> (int) (Integer.parseInt(min1.getPayment()) - Integer.parseInt(min2.getPayment()))).get().getPayment());
                int maxO = Integer.parseInt(bufPrice.stream().
                        max((max1, max2) -> (int) (Integer.parseInt(max1.getPayment()) - Integer.parseInt(max2.getPayment()))).get().getPayment());
*/
                znach = new Znach((int) (i), maxC, minC, 0, 0);
                znachs.add(znach);
            }

        }
        return znachs;
    }

    public String getTheMinimumPriceForLots(Tender tender) {
        ArrayList<Znach> znachArrayList = findingExternsPrices(tender);
        String buf = new String();
        for (Znach znach: znachArrayList){
           buf = buf.concat("Лот№ " + String.valueOf(znach.getNumberLota()) + " - " + String.valueOf(znach.getPriceMin()) + " руб. за ед. без НДС\n");
        }
        return buf;
    }
}
