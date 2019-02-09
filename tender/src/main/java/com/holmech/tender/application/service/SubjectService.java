package com.holmech.tender.application.service;

import com.holmech.tender.application.entity.Applicant;
import com.holmech.tender.application.entity.Subject;
import com.holmech.tender.application.entity.Tender;
import com.holmech.tender.application.entity.calculations.Znach;
import com.holmech.tender.application.parser.fromexcel.SubjectParseExcel;
import com.holmech.tender.application.repository.SubjectRepository;
import com.holmech.tender.application.repository.TenderRepository;
import com.holmech.tender.application.utilities.PathFromOS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectService {

    @Value("${upload.path}")
    private String uploadPath;

    private final SubjectRepository subjectRepository;
    private final ApplicantService applicantService;
    private final TenderRepository tenderRepository;



    public SubjectService(SubjectRepository subjectRepository,
                          ApplicantService applicantService,
                          TenderRepository tenderRepository) {
        this.subjectRepository = subjectRepository;
        this.applicantService = applicantService;
        this.tenderRepository = tenderRepository;
    }

    public void addSubjectFromExcel(Tender bufTender,
                                    ArrayList<Applicant> applicantArrayList) throws IOException {
        Subject subject;
        for (Applicant bufApplicant : applicantArrayList) {
            for (int i = 0; i < bufApplicant.getLots().size(); i++) {// определение количества лотов претендента
                int bufQuantityLots = bufApplicant.getLots().get(i);//определение количества предложений по лоту i
                if (bufQuantityLots > 0) {
                    for (int j = 0; j < bufQuantityLots; j++) {
                        subject = new Subject();
                        subject.setNumberS(i + 1);
                        subject.setTender(bufTender);

                        subject.setApplicant(applicantService.findByNameA(bufApplicant.getNameA()));
                        subjectRepository.save(subject);
                    }
                }
            }
        }
        SubjectParseExcel.saveInExcel(findByTenderSortNumberT(bufTender), new File(uploadPath +
                bufTender.getNumberT() + PathFromOS.getPath() + bufTender.getFilename()));
    }

    public List<Subject> findByTenderSortNumberT(Tender tenderFromDB) {
        return subjectRepository.findByTender(tenderFromDB, Sort.by("idS").ascending());
    }

    public List<Subject> findByNumberT(String numberT) {
        return subjectRepository.findByTender(tenderRepository.findByNumberT(numberT));
    }



    public List<Subject> findByMeetTrue() {//todo неизвестно как сработает при Subject.Meet == NULL
        return subjectRepository.findByMeetTrue();
    }


    public void updateSubjectList(List<Subject> subjectList) throws IOException {
        Subject subject;
        Tender bufTender = null;
        for (Subject subjects : subjectList) {
            subject = subjects;
            subjects.setApplicant(applicantService.findByNameA(subjects.getApplicantNameA()));
            bufTender = tenderRepository.findByNumberT(subjects.getTenderNumberT());
            subjects.setTender(bufTender);

            subjectRepository.save(subject);
        }
        SubjectParseExcel.saveInExcel(findByTenderSortNumberT(bufTender), new File(new String(uploadPath +
                bufTender.getNumberT() + PathFromOS.getPath() + bufTender.getFilename())));
    }

    public ArrayList<Subject> setApplicantInSubjectList(List<Subject> subjectList, List<Subject> subjectListWithId) throws IOException {
        ArrayList<Subject> bufSubjectList = new ArrayList<Subject>();
        for (int i = 0; i < subjectList.size(); i++) {
            Subject bufSubject = subjectList.get(i);
            bufSubject.setIdS(subjectListWithId.get(i).getIdS());
            bufSubject.setTenderNumberT(subjectListWithId.get(i).getTenderNumberT());
            bufSubjectList.add(bufSubject);
        }
        return bufSubjectList;
    }

    public void removeTheSubjectsFromTheTender(List<Subject> subjectList) {
        subjectRepository.deleteAll(subjectList);
    }

    public String getNoMeetSubject(Tender tender, Applicant applicant){
        String noMeetSubject = " ";
        List<Subject> subjectList = subjectRepository.findByTenderAndApplicant(tender,applicant);
        int amountNoMeet = 0;
        for (Subject subject: subjectList) {
            if(subject.getMeet() != null && subject.getMeet() == false){
                if(noMeetSubject.length()>2)
                   noMeetSubject = noMeetSubject.concat(", ");
                noMeetSubject = noMeetSubject.concat(subject.getNumberS().toString());
                amountNoMeet++;
            }
        }
        if(amountNoMeet == subjectList.size())
            return "000";
        return noMeetSubject;
    }


    public List<Subject> getMeetSubject(Tender tender) {
        List<Subject> meetSubjectList = new ArrayList<>();
        List<Subject> subjectFromDB = findByTenderSortNumberT(tender);
        for (Subject subject : subjectFromDB) {
            if (subject.getMeet() != null && subject.getMeet()) {
                meetSubjectList.add(subject);
            }
        }
        return meetSubjectList;
    }

    private ArrayList<Znach> findingExternsPrices(Tender tender){
        //Определини экстернов значений
        List<Subject> meetSubjectList = new ArrayList<>();
        if(!meetSubjectList.isEmpty())meetSubjectList.clear();
        meetSubjectList = getMeetSubject(tender);
        ArrayList<Znach> znachs = new ArrayList<Znach>();
        Znach znach;

        //сортировка по номеру лота
        meetSubjectList.sort((o1, o2) -> {
            if (o1.getNumberS() == o2.getNumberS()) return 0;
            else if (o1.getNumberS()> o2.getNumberS()) return 1;
            else return -1;
        });

        //нахождения лота с максимальным номером на который поступили предложения
        Subject subjectMaxNumberS = new Subject();
        subjectMaxNumberS = meetSubjectList.stream().max((fc1, fc2) -> fc1.getNumberS() - fc2.getNumberS()).get();

        for (int i = 1; i <= subjectMaxNumberS.getNumberS(); i++) {

            List<Subject> bufSubject = new ArrayList<>();
            if(bufSubject.isEmpty()) bufSubject.clear();
            for (Subject subject: meetSubjectList) {
                if(subject.getNumberS()==i)
                    bufSubject.add(subject);
            }

            if(!bufSubject.isEmpty()) {
                float minC = (float) bufSubject.stream().mapToDouble(d->d.getPrice()).min().getAsDouble();
                float maxC = (float) bufSubject.stream().mapToDouble(d->d.getPrice()).max().getAsDouble();
                int minO = bufSubject.stream().mapToInt(d->Integer.parseInt(d.getPayment())).min().getAsInt();
                int maxO = bufSubject.stream().mapToInt(d->Integer.parseInt(d.getPayment())).max().getAsInt();

                znach = new Znach((int) (i), maxC, minC, minO, maxO, bufSubject.get(0).getUnits());
                znachs.add(znach);
            }

        }
        return znachs;
    }

    public String getTheMinimumPriceForLots(Tender tender) {
        ArrayList<Znach> znachArrayList = findingExternsPrices(tender);
        String buf = new String();
        for (Znach znach: znachArrayList){
            buf = buf.concat("Лот№ " + String.valueOf(znach.getNumberLota()) + " - " + String.valueOf(znach.getPriceMin())
                    + " руб. за "
                    + znach.getUnitsLota()
                    + " без НДС\n");
        }
        return buf;
    }

    public Subject findByApplicantAndNameSAndNumberSAndPrice(String nameA, String nameS,Integer numberS, Double price){
        Applicant applicant= applicantService.findByNameA(nameA);
        return subjectRepository.findByApplicantAndNameSAndNumberSAndPrice(applicant,nameS,numberS,price);
    }
    public Subject findById(long IdS){
        return subjectRepository.findById(IdS).get();
    }
}
