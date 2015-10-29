package esper;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

/**
 * Created by IntelliJ IDEA.
 * User: wei.Li
 * Date: 14-7-28
 * Time: 14:44
 * <p>
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * TODO 暂不使用
 */
public class EPS_Utils {

    /**
     * 返回默认EPServiceProvider。返回服务的URI值是“默认”。
     *
     * @return EPServiceProvider
     */
    public static EPServiceProvider getDefaultProvider() {
        return EPServiceProviderManager.getDefaultProvider();
    }

    public static EPAdministrator getEpAdministrator(EPServiceProvider epServiceProvider) {
        return epServiceProvider.getEPAdministrator();
    }

    public static EPStatement createEPL(EPAdministrator epAdministrator, String epl) {
        return epAdministrator.createEPL(epl);
    }
}
