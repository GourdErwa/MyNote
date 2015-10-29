package esper.examples.alarm;


/**
 * @author wei.Li by 14-8-14.
 */
public class Main {

    public static final String ALARMNAME = Alarm.class.getName();
    public static final String STREAM = Stream.class.getName();
    public static final String STREAMCATEGORY_CONTEXT =
            "create context streamcategory group by num > " + Baseline.num + " as baselinealarm,group by num > " + Condition.num + " as conditionalarm from " + STREAM;
    public static final String STREAMCATEGORY_EPL =
            "context streamcategory select context.label , stream_id , num from " + STREAM + ".win:length(1)";//context.id,context.name,context.label,
    public static final String ALARMCATEGORY_CONTEXT =
            "create context alarmcategory start pattern [every-distinct(a.keyunique) a=" + ALARMNAME + "] end  after 15 minutes";
    public static final String ALARMCATEGORY_EPL =
            "context alarmcategory select context.name ,context.id ,keyunique  from " + ALARMNAME;
    //线程执行时间间隔-ms
    private static final int EXECUTE_INTERVAL_MILLISECOND = 500;
    //执行次数
    private static final int EXECUTE_NUM = 2;

    public static void main(String[] args) {

        //启动线程动态更新基线与条件的设定值
        new Thread(new Baseline()).start();
        new Thread(new Condition()).start();

        EsperService.EsperAlarmProvider mergeCepProvider = EsperService
                .getInstance("mergeCepProvider", new MergeListener());
        mergeCepProvider.registerEPL2Listener(ALARMCATEGORY_CONTEXT);
        mergeCepProvider.registerEPL2Listener(ALARMCATEGORY_EPL);


        EsperService.EsperAlarmProvider preliminaryCepProvider = EsperService
                .getInstance("preliminaryCepProvider", new PreliminaryListener(mergeCepProvider));
        preliminaryCepProvider.registerEPL(STREAMCATEGORY_CONTEXT);
        preliminaryCepProvider.registerEPL2Listener(STREAMCATEGORY_EPL);
        sendEventByEPRuntime(preliminaryCepProvider);
    }

    /**
     * 发送事件
     *
     * @param preliminaryCepProvider EPRuntime引擎接收
     */
    private static void sendEventByEPRuntime(EsperService.EsperAlarmProvider preliminaryCepProvider) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int temp = 0;
                while (true) {
                    if (EXECUTE_NUM < temp)
                        break;
                    temp++;
                    preliminaryCepProvider.sendEvent(Stream.getRandom());
                    try {
                        Thread.sleep(EXECUTE_INTERVAL_MILLISECOND);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        });

        thread.start();
    }
}
