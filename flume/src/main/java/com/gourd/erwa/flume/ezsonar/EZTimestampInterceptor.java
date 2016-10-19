package com.gourd.erwa.flume.ezsonar;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.gourd.erwa.flume.ezsonar.EZTimestampInterceptor.Constants.TIMESTAMP;

/**
 * The type Ez timestamp interceptor.
 *
 * @author wei.Li by 16/2/24
 */
public class EZTimestampInterceptor implements Interceptor {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMM d HH:mm:ss", Locale.ENGLISH);

    /**
     * Only {@link EZTimestampInterceptor.Builder} can build me
     */
    private EZTimestampInterceptor() {
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {

        System.out.println();


    }

    @Override
    public void initialize() {
        // no-op
    }

    /**
     * Modifies events in-place.
     */
    @Override
    public Event intercept(Event event) {
        Map<String, String> headers = event.getHeaders();
        try {

            System.out.println("------------------------ start >" + headers);
            final String time = Calendar.getInstance().get(Calendar.YEAR) + headers.get("time");
            headers.put(TIMESTAMP, DATE_FORMAT.parse(time).getTime() + "");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        System.out.println("------------------------ end >" + headers);
        return event;
    }

    /**
     * Delegates to {@link #intercept(Event)} in a loop.
     *
     * @param events events
     * @return List<Event>
     */
    @Override
    public List<Event> intercept(List<Event> events) {
        for (Event event : events) {
            intercept(event);
        }
        return events;
    }

    @Override
    public void close() {
        // no-op
    }


    /**
     * Builder which builds new instances of the TimestampInterceptor.
     */
    public static class Builder implements Interceptor.Builder {

        @Override
        public Interceptor build() {
            return new EZTimestampInterceptor();
        }

        @Override
        public void configure(Context context) {
        }

    }

    /**
     * The type Constants.
     */
    static class Constants {
        /**
         * The constant TIMESTAMP.
         */
        static String TIMESTAMP = "timestamp";
    }

}
