package esper.event_type;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

/**
 * Created by IntelliJ IDEA.
 * User: wei.Li
 * Date: 14-7-28
 * Time: 14:58
 */
public abstract class MyUpdateListener implements UpdateListener {
    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        update_Event(newEvents);
    }

    public abstract void update_Event(EventBean[] newEvents);

}
