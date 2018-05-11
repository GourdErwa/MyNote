package esper.views;


import com.espertech.esper.client.*;
import esper.javabean.Apple;

/**
 * main 函数测试
 *
 * @author wei.Li
 * 事件监听处理
 * @see ViewsAppleListener#update(com.espertech.esper.client.EventBean[], com.espertech.esper.client.EventBean[])
 */
public class ViewsMain_Execute implements Runnable {

    protected static final EPServiceProvider defaultProvider = EPServiceProviderManager.getDefaultProvider();
    protected static final EPAdministrator epAdministrator = defaultProvider.getEPAdministrator();
    protected static final EPRuntime epRuntime = defaultProvider.getEPRuntime();
    //线程执行时间间隔-ms
    private static final int EXECUTE_INTERVAL_MILLISECOND = 1000;
    //执行次数
    private static final int EXECUTE_NUM = 10;

    public static void main(String[] args) throws InterruptedException {

        /**
         * 定义数据
         * @see esper.epl.EPL_3_Output#when()
         */
        ConfigurationOperations config = epAdministrator.getConfiguration();
        config.addVariable("exceed", boolean.class, false);

        //获取 epl
        String epl = View.dataWindowViews();

        EPStatement epStatement = epAdministrator.createEPL(epl);

        //注册监听
        epStatement.addListener(new ViewsAppleListener());

        new ViewsMain_Execute().run();

    }

    /**
     * use Thread add event
     */
    @Override
    public void run() {
        int temp = 1;
        while (temp <= EXECUTE_NUM) {
            System.out.println("~~~~~~~~~This is " + temp + " event~~~~~~~~~~~~~~");
            temp++;

            epRuntime.sendEvent(Apple.getRandomApple());

            try {
                Thread.sleep(EXECUTE_INTERVAL_MILLISECOND);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        System.out.println(" Thread stop ...");


    }
}




