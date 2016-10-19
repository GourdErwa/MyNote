/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gourd.erwa.flume.ezsonar;

import org.apache.commons.lang.time.DateUtils;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Simple Interceptor class that sets the current system timestamp on all events
 * that are intercepted. By convention, this timestamp header is named
 * "timestamp" and its format is a "stringified" long timestamp in milliseconds
 * since the UNIX epoch.
 *
 * @author Frederick Haebin Na (haebin.na@gmail.com)
 */
public class EventTimestampInterceptor implements Interceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventTimestampInterceptor.class);

    private static final String YYYY_FIELD = "yyyy";
    private final boolean preserveExisting;
    private final String[] dateFormat = new String[1];
    private final String dateFormatField;
    private boolean isHaveYearField = false;

    /**
     * Only {@link Builder} can build me
     */
    private EventTimestampInterceptor(boolean preserveExisting,
                                      String dateFormatStr, String dateFormatField) {
        this.preserveExisting = preserveExisting;


        this.isHaveYearField = dateFormatStr.contains(YYYY_FIELD);
        if (!this.isHaveYearField) {
            dateFormatStr = YYYY_FIELD + dateFormatStr;
        }
        this.dateFormat[0] = dateFormatStr;
        this.dateFormatField = dateFormatField;
    }

    public void initialize() {
        // no-op
    }

    /**
     * Modifies events in-place.
     */
    public Event intercept(Event event) {
        Map<String, String> headers = event.getHeaders();
        if (preserveExisting && headers.containsKey(Constants.TIMESTAMP)) {
            // we must preserve the existing timestamp
        } else {
            System.out.println("------- EventTimestampInterceptor start -------" + headers);
            long now;
            try {
                String str = headers.get(dateFormatField);
                if (!this.isHaveYearField) {
                    str = Calendar.getInstance().get(Calendar.YEAR) + str;
                }
                now = DateUtils.parseDate(str, dateFormat).getTime();
                headers.put(Constants.TIMESTAMP, Long.toString(now));
            } catch (Exception e) {
                LOGGER.warn(
                        "Setting system time as timestamp header due to this error: {}",
                        e.getMessage());
                now = System.currentTimeMillis();
                headers.put(Constants.TIMESTAMP, Long.toString(now));
            }
            System.out.println("------- EventTimestampInterceptor end -------" + headers);
        }
        return event;
    }

    /**
     * Delegates to {@link #intercept(Event)} in a loop.
     *
     * @param events events
     * @return List<Event>
     */
    public List<Event> intercept(List<Event> events) {
        for (Event event : events) {
            intercept(event);
        }
        return events;
    }

    public void close() {
        // no-op
    }

    @Override
    public String toString() {
        return "EventTimestampInterceptor{" + "preserveExisting=" + preserveExisting +
                ", dateFormat=" + (dateFormat == null ? "null" : Arrays.asList(dateFormat).toString()) +
                ", dateFormatField='" + dateFormatField + '\'' +
                ", isHaveYearField=" + isHaveYearField +
                '}';
    }

    /**
     * Builder which builds new instances of the EventTimestampInterceptor.
     */
    public static class Builder implements Interceptor.Builder {

        private boolean preserveExisting = Constants.PRESERVE_D;
        private String dateFormat = "yyyy-MM-dd HH:mm:ss";
        private String dateFormatField = "time";

        public Interceptor build() {
            final EventTimestampInterceptor eventTimestampInterceptor = new EventTimestampInterceptor(preserveExisting,
                    dateFormat, dateFormatField);
            System.out.println(" build EventTimestampInterceptor object = " + eventTimestampInterceptor.toString());
            return eventTimestampInterceptor;
        }

        public void configure(Context context) {
            preserveExisting = context.getBoolean(Constants.PRESERVE, Constants.PRESERVE_D);
            dateFormat = context.getString(Constants.FORMAT, dateFormat);
            dateFormatField = context.getString(Constants.DATE_FORMAT_FIELD, dateFormatField);
        }
    }

    /**
     * The type Constants.
     */
    static class Constants {
        /**
         * The Timestamp.
         */
        static String TIMESTAMP = "timestamp";
        /**
         * The Preserve.
         */
        static String PRESERVE = "preserveExisting";
        /**
         * The Preserve d.
         */
        static boolean PRESERVE_D = false;

        /**
         * The Date format field.
         */
        static String DATE_FORMAT_FIELD = "dateFormatField";
        /**
         * The Format.
         */
        static String FORMAT = "dateFormat";
    }

}
