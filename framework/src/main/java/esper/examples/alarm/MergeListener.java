package esper.examples.alarm;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import org.slf4j.LoggerFactory;

/**
 * @author wei.Li by 14-8-14.
 */
public class MergeListener implements UpdateListener {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MergeListener.class);

    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        if (newEvents != null) {
            for (int i = 0; i < newEvents.length; i++) {

                LOGGER.debug("MergeListener eventBean[{}] : <{}>", i, newEvents[i].getUnderlying());
            }
        }
        System.out.println();
    }
}
