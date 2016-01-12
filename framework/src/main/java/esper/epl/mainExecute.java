package esper.epl;


import com.espertech.esper.client.*;
import esper.javabean.Apple;

import java.util.Date;

/**
 * main 函数测试
 *
 * @author wei.Li
 *         事件监听处理
 * @see AppleListener#update(com.espertech.esper.client.EventBean[], com.espertech.esper.client.EventBean[])
 */
public class mainExecute extends Thread {

    protected static final EPServiceProvider DEFAULT_PROVIDER = EPServiceProviderManager.getDefaultProvider();
    protected static final EPAdministrator EP_ADMINISTRATOR = DEFAULT_PROVIDER.getEPAdministrator();
    protected static final EPRuntime EP_RUNTIME = DEFAULT_PROVIDER.getEPRuntime();
    //执行次数
    private static final int EXECUTE_NUM = 1000;
    //线程执行时间间隔-ms
    private static int EXECUTE_INTERVAL_MILLISECOND = 1000 * 60;

    public static void main(String[] args) throws InterruptedException {

        /*
         * 定义数据
         * @see EPL_3_Output#when()
         */
/*        ConfigurationOperations config = epAdministrator.getConfiguration();
        config.addVariable("exceed", boolean.class, false);
        Configuration configuration = new Configuration();
        configuration.getEngineDefaults().getViewResources().setAllowMultipleExpiryPolicies(true);*/
        //获取 epl

        EPStatement epStatement = EP_ADMINISTRATOR.createEPL(EPL_Test.time());
        //注册监听
        epStatement.addListener(new AppleListener());

        new mainExecute().start();

    }

    /**
     * use Thread add event
     */
    @Override
    public void run() {
        int temp = 0;
        while (++temp <= EXECUTE_NUM) {
            final Apple apple = Apple.getRandomApple();
            EP_RUNTIME.sendEvent(apple);
            System.out.println(new Date() + " sendEvent");

            //epRuntime.sendEvent(Orange.getRandomOrange());
            try {
                //EXECUTE_INTERVAL_MILLISECOND += EXECUTE_INTERVAL_MILLISECOND;
                Thread.sleep(EXECUTE_INTERVAL_MILLISECOND);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}




