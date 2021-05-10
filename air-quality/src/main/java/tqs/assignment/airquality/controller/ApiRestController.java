package tqs.assignment.airquality.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tqs.assignment.airquality.entities.AirQuality;
import tqs.assignment.airquality.service.AirPollutionReportService;

import java.net.MalformedURLException;

@RestController
@RequestMapping("/api")
public class ApiRestController {

    @Autowired
    AirPollutionReportService service;

    @GetMapping("/location/{location}")
    public AirQuality getReportForLocation(@PathVariable(name = "location") String location) throws MalformedURLException, JsonProcessingException {
        AirQuality a = service.getDataByLocation(location);
        return a;
    }
}
