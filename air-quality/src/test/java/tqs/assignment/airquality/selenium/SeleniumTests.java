package tqs.assignment.airquality.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class SeleniumTests {
    WebDriver driver;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }


    @Test
    void checkCurrentDataForValidLocation() {

        driver.get("http://localhost:8080/current");
        driver.findElement(By.id("searchBar")).sendKeys("Aveiro");
        driver.findElement(By.id("searchButton")).click();
        assertEquals(driver.findElement(By.id("searchQuery")).getText(), "Results for: Aveiro Coordinates(latitude=40.635013, longitude=-8.651136)");
        List<WebElement> pollutants = driver.findElements(By.id("pollutants"));
        assert (pollutants.size() > 0);

    }


    @Test
    void checkCurrentDataForInvalidLocation() {

        driver.get("http://localhost:8080/current");
        driver.findElement(By.id("searchBar")).sendKeys("pqwoeiruqwpeoiruqweproiuq");
        driver.findElement(By.id("searchButton")).click();
        assertEquals(driver.findElement(By.id("standardMessage")).getText(), "Search for a location!");

    }



    @Test
    void checkForecastDataForValidLocation() {

        driver.get("http://localhost:8080/forecast");
        driver.findElement(By.id("searchBar")).sendKeys("Aveiro");
        driver.findElement(By.id("searchButton")).click();
        assertEquals(driver.findElement(By.id("searchQuery")).getText(), "Results for: Aveiro Coordinates(latitude=40.635013, longitude=-8.651136)");
        List<WebElement> forecast_container = driver.findElements(By.id("forecastContainer"));
        assert (forecast_container.size() > 0);

    }

    @Test
    void checkForecastDataForInvalidLocation() {

        driver.get("http://localhost:8080/forecast");
        driver.findElement(By.id("searchBar")).sendKeys("pqwoeiruqwpeoiruqweproiuq");
        driver.findElement(By.id("searchButton")).click();
        assertEquals(driver.findElement(By.id("standardMessage")).getText(), "Search for a location!");

    }

    @Test
    void checkCache() {

        driver.get("http://localhost:8080/cache");
        assertEquals(driver.findElement(By.id("currentTitle")).getText(), "Current Location Cache");
        assertEquals(driver.findElement(By.id("forecastTitle")).getText(), "Forecast Location Cache");

    }

}
