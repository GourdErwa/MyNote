package esper.epl;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import org.slf4j.LoggerFactory;

import java.util.Date;


/**
 * 监听{@link esper.javabean.Apple}
 */
class AppleListener implements UpdateListener {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(AppleListener.class);

    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        if (newEvents != null) {
            for (int i = 0; i < newEvents.length; i++) {
                //LOGGER.error("~~~~~~~~ newEvents‘s size is <{}> ~~~~~~~~", i);
                String key = "count(*)";
                EventBean eventBean = newEvents[i];
                //System.out.println(eventBean.getUnderlying().toString());
                //System.out.println(" newEvents[" + i + "]  eventBean is: " + eventBean.getUnderlying().toString());
                System.out.println(new Date() + " newEvents[" + i + "]  " + key + " is: " + eventBean.get(key));
                //System.out.println(" newEvents[" + i + "]  " + key + " is: " + eventBean.get(key) + " , id is " + eventBean.get("id"));

            }
        }

        if (oldEvents != null) {
            System.out.println("~~~~~~~~ oldEvents is not null ! ~~~~~~~~");
            LOGGER.error("~~~~~~~~ oldEvents is not null ! ~~~~~~~~");
        }
    }

}
