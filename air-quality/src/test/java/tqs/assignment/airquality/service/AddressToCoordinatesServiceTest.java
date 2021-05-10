package tqs.assignment.airquality.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import tqs.assignment.airquality.entities.Coordinates;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AddressToCoordinatesServiceTest {

    @Mock
    private RestTemplate rest_template;

    @InjectMocks
    private AddressToCoordinatesService service;

    @Test
    void whenValidLocation_thenReturnCoordinates() throws MalformedURLException, JsonProcessingException {

        String foz_coa = "Rua da Lameira, Vila Nova de Foz Côa, Guarda, Portugal";
        Coordinates hard_coded_coords = new Coordinates(41.083118, -7.140839);

        ResponseEntity<String> response = ResponseEntity.accepted().body("<200,{\"data\":[{\"latitude\":41.083118,\"longitude\":-7.140839,\"type\":\"locality\",\"name\":\"Vila Nova de Foz C\\u00f4a Municipality\",\"number\":null,\"postal_code\":null,\"street\":null,\"confidence\":0.6,\"region\":\"Guarda\",\"region_code\":\"GU\",\"county\":null,\"locality\":\"Vila Nova de Foz C\\u00f4a Municipality\",\"administrative_area\":\"Vila Nova de Foz C\\u00f4a\",\"neighbourhood\":null,\"country\":\"Portugal\",\"country_code\":\"PRT\",\"continent\":\"Europe\",\"label\":\"Vila Nova de Foz C\\u00f4a Municipality, GU, Portugal\"},{\"latitude\":41.094457,\"longitude\":-7.143016,\"type\":\"localadmin\",\"name\":\"Vila Nova de Foz C\\u00f4a\",\"number\":null,\"postal_code\":null,\"street\":null,\"confidence\":0.6,\"region\":\"Guarda\",\"region_code\":\"GU\",\"county\":null,\"locality\":null,\"administrative_area\":\"Vila Nova de Foz C\\u00f4a\",\"neighbourhood\":null,\"country\":\"Portugal\",\"country_code\":\"PRT\",\"continent\":\"Europe\",\"label\":\"Vila Nova de Foz C\\u00f4a, GU, Portugal\"}]},[Date:\"Mon, 10 May 2021 15:36:31 GMT\", Content-Type:\"application/json\", Transfer-Encoding:\"chunked\", Connection:\"keep-alive\", user-agent:\"Java/11.0.10\", accept:\"text/plain, application/json, application/*+json, */*\", accept-encoding:\"gzip\", cache-control:\"no-cache, private\", x-apilayer-transaction-id:\"2e052b29-ba71-41cd-8b6b-99d6617bcb0a\", access-control-allow-origin:\"*\", access-control-allow-methods:\"GET, POST, HEAD, OPTIONS\", access-control-allow-headers:\"*\", x-quota-limit:\"25000\", x-quota-remaining:\"24741\", x-increment-usage:\"1\", x-request-time:\"0.220\", CF-Cache-Status:\"DYNAMIC\", cf-request-id:\"09f8854e0300002ce339a7f000000001\", Report-To:\"{\"endpoints\":[{\"url\":\"https:\\/\\/a.nel.cloudflare.com\\/report?s=sX1Q%2FMdyoBq0gpGOs%2FbczbSUw8cy4Y4sBxRPhIPMuGa8Kk%2FzHBNi4prH6HgpgSXgQakIFkDubcmpMm3uqbH4AWOaO8DcDmwDjYZB9RDaZA5Ph4w6RmY%3D\"}],\"group\":\"cf-nel\",\"max_age\":604800}\", NEL:\"{\"report_to\":\"cf-nel\",\"max_age\":604800}\", Server:\"cloudflare\", CF-RAY:\"64d43e5cddf32ce3-LHR\", alt-svc:\"h3-27=\":443\"; ma=86400, h3-28=\":443\"; ma=86400, h3-29=\":443\"; ma=86400\"]>");

        URL url = new URL(AddressToCoordinatesService.PS_API_URL + String.format("&query=%s", foz_coa));

        when(rest_template.getForEntity(url.toString(), String.class)).thenReturn(response);

        Coordinates mocked_coords = service.getCoordinatesOfAddress(foz_coa);

        assertEquals(mocked_coords, hard_coded_coords);
    }
}