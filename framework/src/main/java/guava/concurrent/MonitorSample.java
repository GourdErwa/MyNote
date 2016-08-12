/*
 * Written by wei.Li and released to the public domain
 * Welcome to correct discussion as explained at
 *
 * -----------------------------------------------------------------
 *
 * GitHub:  https://github.com/GourdErwa
 * CSDN  :	http://blog.csdn.net/xiaohulunb
 * WeiBo :	http://www.weibo.com/xiaohulunb  	@GourdErwa
 * Email :	gourderwa@163.com
 *
 * Personal home page: http://grouderwa.com
 */

package guava.concurrent;

import com.google.common.util.concurrent.Monitor;

import java.util.ArrayList;
import java.util.List;

/**
 * 并发控制类Monitor
 *
 * @author wei.Li by 15/3/19 (gourderwa@163.com).
 */
public class MonitorSample {

    private static final int MAX_SIZE = 10;
    private static List<String> list = new ArrayList<>();
    private static Monitor monitor = new Monitor();

    private static Monitor.Guard listBelowCapacity = new Monitor.Guard(monitor) {
        @Override
        public boolean isSatisfied() {
            return list.size() < MAX_SIZE;
        }
    };

    public static void main(String[] args) {

        if (monitor.enterIf(listBelowCapacity)) {
            try {
                doWork();
            } finally {
                monitor.leave();
            }
        }

    }

    private static void doWork() {

    }

    public void addToList(String item) throws InterruptedException {
        monitor.enterWhen(listBelowCapacity); //Guard(形如Condition)，不满足则阻塞，而且我们并没有在Guard进行任何通知操作
        try {
            list.add(item);
        } finally {
            monitor.leave();
        }
    }
}


/**
 * 其他的Monitor访问方法：
 * <p>
 * Monitor.enter      //进入Monitor块，将阻塞其他线程直到Monitor.leave
 * Monitor.tryEnter   //尝试进入Monitor块，true表示可以进入, false表示不能，并且不会一直阻塞
 * Monitor.tryEnterIf //根据条件尝试进入Monitor块
 * 这几个方法都有对应的超时设置版本。
 */
