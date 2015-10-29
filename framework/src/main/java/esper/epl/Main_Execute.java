package esper.epl;


import com.espertech.esper.client.*;
import esper.javabean.Apple;

/**
 * main 函数测试
 *
 * @author wei.Li
 *         事件监听处理
 * @see AppleListener#update(com.espertech.esper.client.EventBean[], com.espertech.esper.client.EventBean[])
 */
public class Main_Execute implements Runnable {

    protected static final EPServiceProvider defaultProvider = EPServiceProviderManager.getDefaultProvider();
    protected static final EPAdministrator epAdministrator = defaultProvider.getEPAdministrator();
    protected static final EPRuntime epRuntime = defaultProvider.getEPRuntime();
    //线程执行时间间隔-ms
    private static final int EXECUTE_INTERVAL_MILLISECOND = 500;
    //执行次数
    private static final int EXECUTE_NUM = 10;

    public static void main(String[] args) throws InterruptedException {

        /**
         * 定义数据
         * @see EPL_3_Output#when()
         */
        ConfigurationOperations config = epAdministrator.getConfiguration();
        config.addVariable("exceed", boolean.class, false);
        Configuration configuration = new Configuration();
        configuration.getEngineDefaults().getViewResources().setAllowMultipleExpiryPolicies(true);
        //获取 epl
        String epl = EPL_1_Common.avg();

        EPStatement epStatement = epAdministrator.createEPL(epl);
        //注册监听
        epStatement.addListener(new AppleListener());

        new Main_Execute().run();

    }

    /**
     * use Thread add event
     */
    @Override
    public void run() {
        int temp = 1;
        while (temp <= EXECUTE_NUM) {
            temp++;

            epRuntime.sendEvent(Apple.getRandomApple());

            /**
             * 满足条件修改数据
             * @see EPL_3_Output#when()
             */
            if (temp % 3 == 0)
                //epRuntime.sendEvent(Banana.getRandomBanana());
                epRuntime.setVariableValue("exceed", true);

            //epRuntime.sendEvent(Orange.getRandomOrange());
            try {
                Thread.sleep(EXECUTE_INTERVAL_MILLISECOND);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    }
}




