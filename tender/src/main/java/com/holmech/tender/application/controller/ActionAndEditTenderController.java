package com.holmech.tender.application.controller;

import com.holmech.tender.application.entity.*;
import com.holmech.tender.application.form.TenderForm;
import com.holmech.tender.application.parser.intheword.FB;
import com.holmech.tender.application.parser.intheword.FBnewFill;
import com.holmech.tender.application.repository.TenderRepository;
import com.holmech.tender.application.service.*;
import net.sf.jasperreports.engine.JRException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static jdk.nashorn.internal.objects.NativeString.concat;

@Controller
public class ActionAndEditTenderController {

    private final TenderService tenderService;
    private final FB fbbService;
    private final DocumentsService documentsService;
    private final WorkerRService workerRService;
    private final SubjectService subjectService;
    private final SendMessageWithAnAttachmentService sendMessageService;
    private final SubjectAfterTheReductionService subjectAfterTheReductionService;

    public ActionAndEditTenderController(TenderService tenderService,
                                         FB fbbService,
                                         DocumentsService documentsService,
                                         WorkerRService workerRService,
                                         SubjectService subjectService,
                                         SendMessageWithAnAttachmentService sendMessageService,
                                         SubjectAfterTheReductionService subjectAfterTheReductionService) {
        this.tenderService = tenderService;
        this.fbbService = fbbService;
        this.documentsService = documentsService;
        this.workerRService = workerRService;
        this.subjectService = subjectService;
        this.sendMessageService = sendMessageService;
        this.subjectAfterTheReductionService = subjectAfterTheReductionService;
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
                                    @RequestParam(required = false) String dateOfDecline) throws IOException, MessagingException {
        Tender bufTenderFromDB = tenderService.findByNumberT(numberT);
        if (action.isEmpty()) {
            Tender tenderBuf = tenderForm.getTenderList().get(0);
            tenderBuf.setIdT(bufTenderFromDB.getIdT());
            tenderService.saveTender(file, tenderBuf);
        } else {
            switch (action) {
                case "Generate autopsy protocol": {
                    //bufTenderFromDB.setStage("Снижение цены");
                    subjectAfterTheReductionService.writeFromExcel(numberT,null);
                    //createAutopsyProtocol();
                    //
                    break;
                }
                case "Invite members to the price reduction procedure": {
                    bufTenderFromDB.setStage("Снижение цены");
                    int numberMessage = Integer.valueOf(numbersMessage);
                    String signature = new String();
                    String secretary = new String();
                    for (WorkerR workerR : workerRService.findByOrder(bufTenderFromDB.getOrder())) {
                        if (workerR.getRole() == WorkerRole.THECHAIRMAN) {
                            signature = signature.concat(workerR.getWorker().getPosition());
                            signature = signature.concat("                                 ");
                            signature = signature.concat(workerR.getWorker().getInitialsWorker());
                        } else if (workerR.getRole() == WorkerRole.SECRETARY) {
                            secretary = secretary.concat(workerR.getWorker().getInitialsWorker());
                            int lastIndexContakts = workerR.getWorker().getContactsList().size() - 1;
                            secretary = secretary.concat("\n +375447722687");//workerR.getWorker().getContactsList().get(lastIndexContakts).getPhone()todo
                        }

                    }

                    subjectAfterTheReductionService.writeFromExcel(numberT, null);

                    for (Documents documents : documentsService.isTheTenderDocuments(bufTenderFromDB)) {
                        String noMeet = subjectService.getNoMeetSubject(bufTenderFromDB, documents.getApplicant());

                        String textMessage = new String();
                        if(noMeet.equalsIgnoreCase("000")){//все лоты не соответствуют
                            StringBuilder buf = new StringBuilder();
                            textMessage = buf.append("  Коммунальное сельскохозяйственное унитарное предприятие «Агрокомбинат ")
                                    .append("\n «Холмеч» сообщает, что ваше предложения по лотам №")
                                    .append(noMeet)
                                    .append("было отклонено, так как они не соответствуют требованиям.")
                                    .append(" Процедура закупки: " + bufTenderFromDB.getNumberAndNameTender() + ". ").toString();
                        } else {
                            //Типовое начало
                            StringBuilder buf2 = new StringBuilder();
                            buf2.append("   Коммунальное сельскохозяйственное унитарное предприятие «Агрокомбинат ")
                                    .append("«Холмеч» просит принять участие в процедуре по снижению цены.")
                                    .append(" Процедура закупки: " + bufTenderFromDB.getNumberAndNameTender() + ". ");
                            textMessage = textMessage.concat(buf2.toString());

                            if (noMeet.length() > 2 ) {//есть несоответствия
                                StringBuilder bufNoMeetLots = new StringBuilder();
                                bufNoMeetLots.append("Сообщаем, что ваше предложение по лотам №" + noMeet)
                                        .append(" было отклонено, так как они не соответствуют требованиям.");
                                textMessage = textMessage.concat(bufNoMeetLots.toString());
                            }

                            String thereAreNoTheseDocuments = documents.thereAreNoTheseDocuments();
                            if(thereAreNoTheseDocuments.length()>2){
                                textMessage = textMessage.concat(" Также просим предоставить документы" + thereAreNoTheseDocuments + ".");
                            }
                            textMessage = textMessage.concat("\n    Минимальная цена: \n");
                            textMessage = textMessage.concat(subjectService.getTheMinimumPriceForLots(bufTenderFromDB));
                            textMessage = textMessage.concat("  Предложения предоставить по факсу или на электронную почту, в срок до " + dateOfDecline + ".");
                        }

                        FBnewFill fBnewFill = FBnewFill.builder()
                                .numberM(String.valueOf("№" + numberMessage++))
                                .nameA(documents.getApplicant().getNameA())
                                .textM(textMessage)
                                .signature(signature)
                                .worker(secretary)
                                .build();
                        String fileAttachment = new String();
                        try {
                            fileAttachment = fbbService.run(fBnewFill.FBnewFilltoMap(), "FBnew");
                        } catch (JRException e) {
                            e.printStackTrace();
                        }
                        String subject = " Холмеч! Приглашение на снижение цены! Запрос ценовых предложений №" + bufTenderFromDB.getNumberT();
                        List<String> attachments = new ArrayList<>();
                        attachments.add(fileAttachment);
                        sendMessageService.sendAttachmentEmail(documents.getApplicant().getContactsList().get(0).getEmail(),subject,"Просим подтвердить получение сообщения ответным письмом",attachments);
                    }
                    break;
                }
                case "Form a decision protocol": {
                    bufTenderFromDB.setStage("Формирование результата");

                    break;
                }
                case "Notify participants about the results": {
                    bufTenderFromDB.setStage("Принятия решения");
                    break;
                }
            }
            tenderService.updateTender(bufTenderFromDB);
        }
        List<Tender> tenders = tenderService.findAll();
        TenderForm tenderBufForm = new TenderForm(tenders);
        return new ModelAndView("tender", "tenderForm", tenderBufForm);
    }
}
