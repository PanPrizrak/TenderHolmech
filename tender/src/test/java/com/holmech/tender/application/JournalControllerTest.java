package com.holmech.tender.application;

import com.holmech.tender.application.controller.JournalController;
import com.holmech.tender.application.entity.Order;
import com.holmech.tender.application.entity.Tender;
import com.holmech.tender.application.form.TenderForm;
import org.exolab.castor.types.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql","/create-tender-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//@Sql(value = {"/messages-list-after.sql", "/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@WithUserDetails(value = "admin")
public class JournalControllerTest {

    @Autowired
    private JournalController controller;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void journalPageTest() throws Exception {
        this.mockMvc.perform(get("/journal"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='navbarSupportedContent']/div").string("admin"));
    }

    @Test
    public void tenderListTest() throws Exception {
        this.mockMvc.perform(get("/journal"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='tender-list']/tr").nodeCount(2));

    }

    /*@Test
    public void filterMessageTest() throws Exception {
        this.mockMvc.perform(get("/main").param("filter", "my-tag"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='message-list']/div").nodeCount(2))
                .andExpect(xpath("//*[@id='message-list']/div[@data-id='1']").exists())
                .andExpect(xpath("//*[@id='message-list']/div[@data-id='3']").exists());
    }*/

    @Test
    public void addMessageToListTest() throws Exception {
        DateTime dateTimeBufForTest = DateTime.parseDateTime("2002-12-12T00:00:00");

        Order orderTest = new Order();
        orderTest.setIdO((long) 2);
        orderTest.setDateO(dateTimeBufForTest.toDate());
        orderTest.setNumberO("2");

        Tender tenderTest = new Tender();
        tenderTest.setIdT((long) 3);
        tenderTest.setStage("Vskritie");
        tenderTest.setOrder(orderTest);
        tenderTest.setDateT(dateTimeBufForTest.toDate());
        tenderTest.setNumberT("7777-777777");
        tenderTest.setNameT("Add tender Test");
        tenderTest.setPaymentFactor("20");
        tenderTest.setPriceFactor("80");

        TenderForm tenderFormTest = new TenderForm();
        List<Tender> tenderListTest = new ArrayList<>();
        tenderListTest.add(tenderTest);
        tenderFormTest.setTenderList(tenderListTest);
        System.out.println(tenderFormTest);
    //multipart
        MockHttpServletRequestBuilder multipart = multipart("/journal")
                .file("file", "123".getBytes())
                //.param("tenderForm", String.valueOf(tenderFormTest))
                .requestAttr("tenderForm", new TenderForm(tenderListTest))
                .with(csrf());

        this.mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='tender-list']/tr").nodeCount(3));
               // .andExpect(xpath("//*[@id='message-list']/div[@data-id='10']").exists())
               // .andExpect(xpath("//*[@id='message-list']/div[@data-id='10']/div/span").string("fifth"))
               // .andExpect(xpath("//*[@id='message-list']/div[@data-id='10']/div/i").string("#new one"))
        ;
    }
}