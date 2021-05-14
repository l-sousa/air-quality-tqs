package tqs.assignment.airquality.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tqs.assignment.airquality.entities.AirQuality;

import tqs.assignment.airquality.entities.Cache;
import tqs.assignment.airquality.entities.Location;
import tqs.assignment.airquality.service.AddressToCoordinatesService;
import tqs.assignment.airquality.service.AirPollutionReportService;

import java.net.MalformedURLException;
import java.text.ParseException;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


@Controller
public class InterfaceController {

    @Autowired
    private AirPollutionReportService service;

    @Autowired
    private AddressToCoordinatesService coords_service;

    @Autowired
    private Cache<AirQuality> currentCache;

    @Autowired
    private Cache<ArrayList<AirQuality>> forecastCache;

    private Logger logger = Logger.getLogger(InterfaceController.class.getName());

    @GetMapping("/")
    public String getIndex() {
        return "index";
    }

    @GetMapping("/current")
    public String getCurrentDataByLocation(@RequestParam(required = false) String location, Model model) throws JsonProcessingException, ParseException {
        logger.log(Level.INFO, String.format("Receiving request in /location "));

        if (location == null) {

            model.addAttribute("is_index", true);

        } else {
            try {
                location = location.strip();
                model.addAttribute("is_index", false);
                model.addAttribute("is_supported_location", true);

                if (currentCache.hasLocation(location)) {

                    logger.log(Level.INFO, String.format("Request is cached. Returning cache request."));
                    AirQuality airQuality = (AirQuality) currentCache.getCachedRequest(location);
                    model.addAttribute("location", airQuality.getLocation());
                    model.addAttribute("pollutants", airQuality.getPollutants());
                    logger.log(Level.INFO, String.format("Success, returning response."));

                } else {

                    logger.log(Level.INFO, String.format("Request is not cached. Calling service."));
                    Location l = new Location(location);
                    l.setCoordinates(coords_service.getCoordinatesOfAddress(location));


                    AirQuality airQuality = service.getCurrentDataByLocation(l);

                    model.addAttribute("location", airQuality.getLocation());
                    model.addAttribute("pollutants", airQuality.getPollutants());
                    currentCache.addRequestToCache(location, airQuality);
                    logger.log(Level.INFO, String.format("Success, returning response."));

                }
            } catch (Exception e) {

                logger.log(Level.SEVERE, String.format("ERROR, caught exception %s ", e));
                model.addAttribute("is_index", true);
                model.addAttribute("is_supported_location", false);

            }
        }
        return "current_index";
    }

    @GetMapping("/forecast")
    public String getForecastDataByLocation(@RequestParam(required = false) String location, Model model) throws JsonProcessingException, MalformedURLException, ParseException {
        logger.log(Level.INFO, String.format("Receiving request in /forecast"));

        if (location == null) {

            model.addAttribute("is_index", true);

        } else {

            try {

                location = location.strip();
                Location l = new Location(location);
                l.setCoordinates(coords_service.getCoordinatesOfAddress(location));
                model.addAttribute("is_index", false);
                model.addAttribute("is_supported_location", true);

                if (forecastCache.hasLocation(location)) {

                    logger.log(Level.INFO, String.format("Request is cached. Returning cache request."));
                    ArrayList<AirQuality> aq_list = (ArrayList<AirQuality>) forecastCache.getCachedRequest(location);
                    model.addAttribute("location", l);
                    model.addAttribute("aq_list", aq_list);

                } else {

                    logger.log(Level.INFO, String.format("Request is not cached. Calling service."));
                    model.addAttribute("location", l);
                    ArrayList<AirQuality> aq_list = service.getForecastDataByLocation(l);
                    model.addAttribute("aq_list", aq_list);
                    forecastCache.addRequestToCache(location, aq_list);

                }

            } catch (Exception e) {

                logger.log(Level.SEVERE, String.format("ERROR, caught exception %s ", e));
                model.addAttribute("is_index", true);
                model.addAttribute("is_supported_location", false);

            }

        }
        return "forecast_index";
    }

    @GetMapping("/cache")
    public String getCacheDataByLocation(Model model) {
        model.addAttribute("current_cache", currentCache);
        model.addAttribute("forecast_cache", forecastCache);

        return "cache_index";
    }

}
