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
    public void testLocationCurrentData() throws Exception {
        mvc.perform(get("/current"))
                .andExpect(status().isOk())
                .andExpect(view().name("current_index"));
    }

    @Test
    public void testLocationCurrentDataWithParameter() throws Exception {
        mvc.perform(get("/current?location=Aveiro"))
                .andExpect(status().isOk())
                .andExpect(view().name("current_index"));
    }

    @Test
    public void testLocationForecastData() throws Exception {
        mvc.perform(get("/forecast"))
                .andExpect(status().isOk())
                .andExpect(view().name("forecast_index"));
    }

    @Test
    public void testLocationForecastDataWithParameter() throws Exception {
        mvc.perform(get("/forecast?location=Aveiro"))
                .andExpect(status().isOk())
                .andExpect(view().name("forecast_index"));
    }

    @Test
    public void testLocationCacheData() throws Exception {
        mvc.perform(get("/cache"))
                .andExpect(status().isOk())
                .andExpect(view().name("cache_index"));
    }

    @Test
    public void testError() throws Exception {
        mvc.perform(get("/current?location=random_text_to_throw_error"))
                .andExpect(status().isOk())
                .andExpect(view().name("current_index"));

        mvc.perform(get("/forecast?location=random_text_to_throw_error"))
                .andExpect(status().isOk())
                .andExpect(view().name("forecast_index"));
    }
}