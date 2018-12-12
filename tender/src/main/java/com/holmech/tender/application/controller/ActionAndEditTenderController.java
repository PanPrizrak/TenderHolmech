package com.holmech.tender.application.controller;

import com.holmech.tender.application.entity.*;
import com.holmech.tender.application.form.TenderForm;
import com.holmech.tender.application.parser.intheword.FB;
import com.holmech.tender.application.parser.intheword.FBnewFill;
import com.holmech.tender.application.repository.TenderRepository;
import com.holmech.tender.application.service.DocumentsService;
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

    public ActionAndEditTenderController(TenderService tenderService,
                                         FB fbbService,
                                         DocumentsService documentsService,
                                         WorkerRService workerRService) {
        this.tenderService = tenderService;
        this.fbbService = fbbService;
        this.documentsService = documentsService;
        this.workerRService = workerRService;
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
                    String signature = null;
                    String secretary = null;
                    for (WorkerR workerR : workerRService.findByOrder(bufTenderFromDB.getOrder())) {
                        if (workerR.getRole() == WorkerRole.THECHAIRMAN) {
                            signature.concat(workerR.getWorker().getPosition());
                        } else if (workerR.getRole() == WorkerRole.SECRETARY) {
                            secretary.concat(workerR.getWorker().getNameW())
                        }

                    }

                    for (Documents documents : documentsService.isTheTenderDocuments(bufTenderFromDB)) {
                        FBnewFill fBnewFill = FBnewFill.builder()
                                .numberM(String.valueOf(numberMessage++))
                                .nameA(documents.getApplicant().getNameA())
                                .textM("sfasfasdfsafsafsafsa")
                                .signature(signature)
                                .worker(se)
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
