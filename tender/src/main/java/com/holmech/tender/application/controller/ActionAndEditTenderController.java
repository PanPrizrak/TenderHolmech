package com.holmech.tender.application.controller;

import com.holmech.tender.application.entity.*;
import com.holmech.tender.application.form.TenderForm;
import com.holmech.tender.application.parser.intheword.FB;
import com.holmech.tender.application.parser.intheword.FBnewFill;
import com.holmech.tender.application.repository.TenderRepository;
import com.holmech.tender.application.service.DocumentsService;
import com.holmech.tender.application.service.SubjectService;
import com.holmech.tender.application.service.TenderService;
import com.holmech.tender.application.service.WorkerRService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ActionAndEditTenderController {

    private final TenderService tenderService;
    private final FB fbbService;
    private final DocumentsService documentsService;
    private final WorkerRService workerRService;
    private final SubjectService subjectService;

    public ActionAndEditTenderController(TenderService tenderService,
                                         FB fbbService,
                                         DocumentsService documentsService,
                                         WorkerRService workerRService,
                                         SubjectService subjectService) {
        this.tenderService = tenderService;
        this.fbbService = fbbService;
        this.documentsService = documentsService;
        this.workerRService = workerRService;
        this.subjectService = subjectService;
    }

    @GetMapping("/tender/{numberT}")
    private ModelAndView editTender(@PathVariable String numberT) {
        Tender bufTender = tenderService.findByNumberT(numberT);
        ArrayList<Tender> tenderBufList = new ArrayList<>();
        tenderBufList.add(bufTender);
        TenderForm tenderForm = new TenderForm(tenderBufList);
        return new ModelAndView("tender", "tenderForm", tenderForm);
    }

    @PostMapping("/tender/{numberT}")
    private ModelAndView editTender(@PathVariable String numberT,
                                    @RequestParam(required = false, defaultValue = "") String action,
                                    @ModelAttribute("tenderForm") TenderForm tenderForm,
                                    @RequestParam(required = false, name = "file") MultipartFile file,
                                    @RequestParam(required = false) String numbersMessage,
                                    @RequestParam(required = false) String dateOfDecline) throws IOException {
        Tender bufTenderFromDB = tenderService.findByNumberT(numberT);
        if (action.isEmpty()) {
            Tender tenderBuf = tenderForm.getTenderList().get(0);
            tenderBuf.setIdT(bufTenderFromDB.getIdT());
            tenderService.saveTender(file, tenderBuf);
        } else {
            switch (action) {
                case "Generate autopsy protocol": {
                    //bufTenderFromDB.setStage("Снижение цены");

                    //createAutopsyProtocol();
                    //
                    break;
                }
                case "Invite members to the price reduction procedure": {
                    int numberMessage = Integer.valueOf(numbersMessage);
                    String signature = new String();
                    String secretary = new String();
                    for (WorkerR workerR : workerRService.findByOrder(bufTenderFromDB.getOrder())) {
                        if (workerR.getRole() == WorkerRole.THECHAIRMAN) {
                            signature.concat(workerR.getWorker().getPosition());
                            signature.concat("             ");
                            signature.concat(workerR.getWorker().getInitialsWorker());
                        } else if (workerR.getRole() == WorkerRole.SECRETARY) {
                            secretary.concat(workerR.getWorker().getInitialsWorker());
                            int lastIndexContakts = workerR.getWorker().getContactsList().size() - 1;
                            secretary.concat("\n"+workerR.getWorker().getContactsList().get(lastIndexContakts).getPhone());
                        }

                    }



                    for (Documents documents : documentsService.isTheTenderDocuments(bufTenderFromDB)) {
                        String noMeet = subjectService.getNoMeetSubject(bufTenderFromDB, documents.getApplicant());

                        String textMessage = "";
                        if(noMeet.equalsIgnoreCase("000")){//все лоты не соответствуют
                            StringBuilder buf = null;
                            textMessage = buf.append("Коммунальное сельскохозяйственное унитарное предприятие «Агрокомбинат")
                                    .append("\n «Холмеч» сообщает, что ваше предложения по лотам №")
                                    .append(noMeet)
                                    .append("было откланено, так как они не соответствуют требованиям.")
                                    .append("Процедура закупки: " + bufTenderFromDB.getNumberAndNameTender() + ". ").toString();
                        } else {
                            //Типовое начало
                            StringBuilder buf2 = null;
                            buf2.append("Коммунальное сельскохозяйственное унитарное предприятие «Агрокомбинат")
                                    .append("«Холмеч» просит принять участие в процедуре по снижению цены.")
                                    .append("Процедура закупки: " + bufTenderFromDB.getNumberAndNameTender() + ". ");
                            textMessage.concat(buf2.toString());

                            if (noMeet.length() > 2 ) {//есть несоответствия
                                StringBuilder bufNoMeetLots = null;
                                bufNoMeetLots.append("Сообщаем, что ваше предложение по лотам №" + noMeet)
                                        .append("было откланено, так как они не соответствуют требованиям.");
                                textMessage.concat(bufNoMeetLots.toString());
                            }

                            String thereAreNoTheseDocuments = documents.thereAreNoTheseDocuments();
                            if(thereAreNoTheseDocuments.length()>2){
                                textMessage.concat(" Также просим предоставить документы " + thereAreNoTheseDocuments + ".");
                            }
                            textMessage.concat("Минимальная цена: \n");
                            textMessage.concat(tenderService.getTheMinimumPriceForLots(bufTenderFromDB));
                            textMessage.concat("Предложения предоставить по факсу или на электронную почту, в срок до " + dateOfDecline + ".");
                        }

                        FBnewFill fBnewFill = FBnewFill.builder()
                                .numberM(String.valueOf(numberMessage++))
                                .nameA(documents.getApplicant().getNameA())
                                .textM(textMessage)
                                .signature(signature)
                                .worker(secretary)
                                .build();
                        try {
                            fbbService.run(fBnewFill.FBnewFilltoMap(), "FBnew");
                        } catch (JRException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }
                case "Form a decision protocol": {
                    break;
                }
                case "Notify participants about the results": {
                    break;
                }
            }
        }
        List<Tender> tenders = tenderService.findAll();
        TenderForm tenderBufForm = new TenderForm(tenders);
        return new ModelAndView("tender", "tenderForm", tenderBufForm);
    }
}
