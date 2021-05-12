package tqs.assignment.airquality.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@EqualsAndHashCode
@ToString
@Getter
@Setter
public class Cache<T> {
    private int hits = 0;
    private int misses = 0;
    private int request_count = 0;

    @ToString.Exclude
    private Map<String, T> cached_requests;

    @ToString.Exclude
    private Map<String, Date> cached_requests_time;

    public Boolean hasLocation(String location) throws ParseException {
        addRequest();
        if (this.cached_requests.containsKey(location) && checkTTLNotExpired(location)) {
            addHit();
            return true;
        }
        addMiss();
        return false;
    }

    public T getCachedRequest(String location) {
        return this.cached_requests.get(location);
    }

    private Boolean checkTTLNotExpired(String location) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
        Date time_now = new Date();

        Date time_cached = cached_requests_time.get(location);

        long diff = time_cached.getTime() - time_now.getTime();
        TimeUnit time = TimeUnit.MINUTES;
        long diff_in_secs = time.convert(diff, TimeUnit.MINUTES) / 1000;

        if (diff_in_secs > 0) {
            return true;
        }

        this.cached_requests_time.remove(location);
        this.cached_requests.remove(location);
        return false;
    }

    public void addRequestToCache(String location, T air_quality) {
        this.cached_requests.put(location, (T) air_quality);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");

        Date time_now = new Date();

        final long FIVE_MINUTS_IN_MILIS = 5 * 60000; //millisecs

        long t = time_now.getTime();
        Date time_of_expiration = new Date(t + (FIVE_MINUTS_IN_MILIS));

        this.cached_requests_time.put(location, time_of_expiration);

    }

    public Cache() {
        this.cached_requests_time = new HashMap<>();
        this.cached_requests = new HashMap<>();
    }

    public void addRequest() {
        this.request_count++;
    }

    public void addHit() {
        this.hits++;
    }

    public void addMiss() {
        this.misses++;
    }


}
