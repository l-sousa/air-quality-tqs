package tqs.assignment.airquality.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.MalformedURLException;
import java.text.ParseException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(MockitoExtension.class)
public class CacheUnitTest {

    private Cache cache;

    private static final AirQuality aq = Mockito.mock(AirQuality.class);

    @BeforeEach
    public void setup() {
        cache = new Cache();
    }

    @Test
    public void whenAddHit_thenHitsShouldIncreaseOne() {

        int before = cache.getHits();
        cache.addHit();

        assertThat(cache.getHits()).isEqualTo(before + 1);
    }

    @Test
    public void whenAddMiss_thenMissesShouldIncreaseOne() {

        int misses = cache.getMisses();

        cache.addMiss();

        assertThat(cache.getMisses()).isEqualTo(misses + 1);
    }

    @Test
    public void whenAddRequest_thenRequestCountShouldIncreaseOne() {

        int request_count = cache.getRequest_count();

        cache.addRequest();

        assertThat(cache.getRequest_count()).isEqualTo(request_count + 1);
    }

    /* --- Cache --- */
    @Test
    public void whenCheckIfHasLocation_thenReturnTrueOrFalseAccordingly() throws MalformedURLException, JsonProcessingException, ParseException {
        String foz_coa = "Rua da Lameira, Vila Nova de Foz Côa, Guarda, Portugal";

        assertFalse(cache.hasLocation(foz_coa));

        cache.addRequestToCache(foz_coa, aq);

        assertTrue(cache.hasLocation(foz_coa));

    }
}