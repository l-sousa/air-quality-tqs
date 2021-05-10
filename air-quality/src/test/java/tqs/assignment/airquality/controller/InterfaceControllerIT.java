package tqs.assignment.airquality.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import tqs.assignment.airquality.AirQualityApplication;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class InterfaceControllerIT {
    @Autowired
    private MockMvc mvc;

    @Test
    public void testGetRoot() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void testGetLocation() throws Exception {
        mvc.perform(get("/location?location=Aveiro"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    // fazer uma de erro? -> descobrir commo mapear o /error para um apagina html
}