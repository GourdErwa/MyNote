/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package esper.examples.autoid;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RFIDTagsPerSensorListener implements UpdateListener {
    private static final Log log = LogFactory.getLog(RFIDTagsPerSensorListener.class);

    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        if (newEvents != null) {
            logRate(newEvents[0]);
        }
    }

    private void logRate(EventBean theEvent) {
        String sensorId = (String) theEvent.get("sensorId");
        double numTags = (Double) theEvent.get("numTagsPerSensor");

        log.info("Sensor " + sensorId + " totals " + numTags + " tags");
    }
}
