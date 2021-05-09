package tqs.assignment.airquality.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tqs.assignment.airquality.entities.AirQuality;

import tqs.assignment.airquality.entities.Cache;
import tqs.assignment.airquality.service.AirPollutionReportService;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.Map;

import java.util.logging.Level;
import java.util.logging.Logger;


@Controller
public class InterfaceController {

    @Autowired
    private AirPollutionReportService service;

    private Cache cache = new Cache();

    private Logger logger = Logger.getLogger(InterfaceController.class.getName());


    @GetMapping("/")
    public String getDataByLocation(Model model) throws MalformedURLException, JsonProcessingException {

        model.addAttribute("is_index", true);

        return "index";
    }

    @GetMapping("/location")
    public String getDataByLocation(@RequestParam(required = true) String location, Model model) throws MalformedURLException, JsonProcessingException, ParseException {
        logger.log(Level.INFO, String.format("Receiving request in /location for location \"%s\"", location));

        model.addAttribute("is_index", false);

        if (cache.hasLocation(location)) {
            logger.log(Level.INFO, String.format("Request is cached. Returning cache request."));

            AirQuality airQuality = cache.getCachedRequest(location);
            model.addAttribute("location", airQuality.getLocation());
            model.addAttribute("pollutants", airQuality.getPollutants());

            return "index";
        }

        logger.log(Level.INFO, String.format("Request is not cached. Calling service."));

        AirQuality airQuality = service.getDataByLocation(location);

        model.addAttribute("location", airQuality.getLocation());
        model.addAttribute("pollutants", airQuality.getPollutants());
        cache.addRequestToCache(location, airQuality);

        return "index";
    }

    @GetMapping("/forecast")
    public void getForecastDataByLocation() {

    }

    @GetMapping("/cache")
    public void getCacheDataByLocation() {

    }

    @GetMapping("/error")
    public String error() {
        logger.log(Level.INFO, String.format("/error mapping, invalid request"));
        return "invalid_request";
    }

}
