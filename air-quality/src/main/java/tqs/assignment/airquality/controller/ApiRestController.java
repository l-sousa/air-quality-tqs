package tqs.assignment.airquality.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
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

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class ApiRestController {

    private Logger logger = Logger.getLogger(InterfaceController.class.getName());

    @Autowired
    private Cache currentCache;

    @Autowired
    private AirPollutionReportService service;

    @Autowired
    private AddressToCoordinatesService coords_service;

    @GetMapping("/")
    public String getIndex() {
        logger.log(Level.INFO, "External API request / ");
        return "Make a request!";
    }

    @GetMapping("/location/{location}")
    public AirQuality getReportForLocation(@PathVariable(name = "location") String location) throws MalformedURLException, JsonProcessingException, URISyntaxException {
        logger.log(Level.INFO, "External API request /location{" + location + "} ");

        Location l = new Location(location);
        l.setCoordinates(coords_service.getCoordinatesOfAddress(location));

        AirQuality a = service.getDataByLocation(l);
        return a;
    }

    @GetMapping("/cache/current")
    public Cache getCurrentCache() {
        logger.log(Level.INFO, "External API request /cache/current ");
        return currentCache;
    }

    @GetMapping("/cache/forecast")
    public Cache getForecastCache() {
        logger.log(Level.INFO, "External API request /cache/forecast ");
        return currentCache;
    }

}
