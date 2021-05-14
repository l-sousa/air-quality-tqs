package tqs.assignment.airquality.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tqs.assignment.airquality.entities.AirQuality;
import tqs.assignment.airquality.entities.Cache;
import tqs.assignment.airquality.entities.Location;
import tqs.assignment.airquality.service.AddressToCoordinatesService;
import tqs.assignment.airquality.service.AirPollutionReportService;

import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class ApiRestController {

    private Logger logger = Logger.getLogger(InterfaceController.class.getName());

    @Autowired
    private Cache<AirQuality> currentCache;

    @Autowired
    private Cache<ArrayList<AirQuality>> forecastCache;

    @Autowired
    private AirPollutionReportService service;

    @Autowired
    private AddressToCoordinatesService coords_service;

    @Operation(summary = "Root")
    @GetMapping("/")
    public String getIndex() {
        logger.log(Level.INFO, "External API request / ");
        return "Make a request!";
    }

    @Operation(summary = "Gets current date pollution data for a given {location}")
    @GetMapping("/current/{location}")
    public AirQuality getCurrentDataForLocation(@PathVariable(name = "location") String location) throws MalformedURLException, JsonProcessingException {
        logger.log(Level.INFO, "External API request /current");

        Location l = new Location(location);
        l.setCoordinates(coords_service.getCoordinatesOfAddress(location));

        AirQuality a = service.getCurrentDataByLocation(l);
        return a;
    }

    @Operation(summary = "Gets forecast pollution data for a given {location}")
    @GetMapping("/forecast/{location}")
    public ArrayList<AirQuality> getForecastDataForLocation(@PathVariable(name = "location") String location) throws MalformedURLException, JsonProcessingException, ParseException {
        logger.log(Level.INFO, "External API request /forecast");

        Location l = new Location(location);
        l.setCoordinates(coords_service.getCoordinatesOfAddress(location));

        ArrayList<AirQuality> aq_list = service.getForecastDataByLocation(l);
        return aq_list;
    }

    @Operation(summary = "Gets current date requests' statistics")
    @GetMapping("/cache/current")
    public Cache getCurrentCache() {
        logger.log(Level.INFO, "External API request /cache/current ");
        return currentCache;
    }

    @Operation(summary = "Gets forecast requests' statistics")
    @GetMapping("/cache/forecast")
    public Cache getForecastCache() {
        logger.log(Level.INFO, "External API request /cache/forecast ");
        return forecastCache;
    }
}
