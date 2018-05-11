package plug.metrics;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.CsvReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;
import com.espertech.esper.metrics.codahale_metrics.metrics.reporting.JmxReporter;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * @author wei.Li
 */
interface MetricRegistry$ {

    /**
     * 实例化一个registry，最核心的一个模块，相当于一个应用程序的metrics系统的容器，维护一个Map
     */
    MetricRegistry REGISTRY = new MetricRegistry();

    /**
     * JMX
     *
     * @return JmxReporter {@link JmxReporter}
     */
    /*static JmxReporter fetchJmxReporter() {

        return JmxReporter.forRegistry(REGISTRY).build();
    }*/

    /**
     * ConsoleReporter
     *
     * @return ConsoleReporter {@link ConsoleReporter}
     */
    static ConsoleReporter fetchConsoleReporter() {
        return ConsoleReporter.forRegistry(REGISTRY)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
    }

    /**
     * CVS
     *
     * @param filePath cvs文件目录
     * @return CsvReporter {@link CsvReporter}
     */
    static CsvReporter fetchCvsReporter(String filePath) {
        return CsvReporter.forRegistry(REGISTRY)
                .formatFor(Locale.US)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build(new File(filePath));
    }

    /**
     * @param clazz 日志名称
     * @return Slf4jReporter {@link Slf4jReporter}
     */
    static Slf4jReporter fetchSlf4jReporter(Class<?> clazz) {
        return Slf4jReporter.forRegistry(REGISTRY)
                .outputTo(LoggerFactory.getLogger(clazz))
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
    }
}
