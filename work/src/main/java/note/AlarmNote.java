package note;

/**
 * Created by lw on 14-7-22.
 * <p>
 * TODO {@link com.fusionskye.ezsonar.analyzier.buffers.OutputBuffer}
 * TODO {@link com.fusionskye.ezsonar.analyzier.processors.AlarmOutputBufferProcessor  消费对象~~~process 方法对Alarm告警进行保存、更新}
 * TODO {@link com.fusionskye.ezsonar.analyzier.baseline.cmb.BaselineCMBCalculator  100:20  生产者 调用server.getAlarmOutputBuffer().insertCached(submittedAlarm);}
 * <p>
 * <p>
 * TODO {@link com.fusionskye.ezsonar.analyzier.buffers.ProcessBuffer}
 * TODO {@link com.fusionskye.ezsonar.analyzier.processors.AlarmProcessBufferProcessor  消费对象~~~process 处理AlarmMessage后插入Alarm到上述AlarmOutputBufferProcessor消费对象中  }
 * TODO {@link com.fusionskye.ezsonar.analyzier.processors.StatisticalProcessBufferProcessor 98:42 生产者 调用server.getAlarmProcessBuffer().insertCached(alarmMessage)}
 */
public class AlarmNote {

}
