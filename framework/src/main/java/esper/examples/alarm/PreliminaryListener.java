package esper.examples.alarm;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import org.slf4j.LoggerFactory;

/**
 * 初步过滤监听
 *
 * @author wei.Li by 14-8-14.
 */
public class PreliminaryListener implements UpdateListener {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PreliminaryListener.class);

    //合并处理
    private EsperService.EsperAlarmProvider mergeCepProvider;

    public PreliminaryListener(EsperService.EsperAlarmProvider mergeCepProvider) {
        this.mergeCepProvider = mergeCepProvider;
    }

    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents) {

        if (newEvents != null) {
            for (int i = 0; i < newEvents.length; i++) {
                EventBean eventBean = newEvents[i];
                LOGGER.debug("PreliminaryListener eventBean[{}] : <{}>", i, eventBean.getUnderlying().toString());
                Stream stream = new Stream(((Integer) eventBean.get("stream_id")), ((Integer) eventBean.get("num")));
                String type = eventBean.get("label") + "";
                Alarm alarm = Alarm.getInstance(stream, type);
                //过滤以后的事件发送到合并处理监听
                mergeCepProvider.sendEvent(alarm);
            }
        }
    }
}
