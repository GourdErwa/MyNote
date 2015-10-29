package plug.metrics;

import com.codahale.metrics.*;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by lw on 14-7-2.
 * <p>
 * 输出类别工具
 */
public class Reporters {

    /**
     * JMX
     *
     * @param registry registry
     * @return JmxReporter {@link JmxReporter}
     */
    public static JmxReporter get2JmxReporter(MetricRegistry registry) {
        final JmxReporter reporter = JmxReporter.forRegistry(registry).build();
        return reporter;
    }

    /**
     * ConsoleReporter
     *
     * @param registry registry
     * @return ConsoleReporter {@link ConsoleReporter}
     */
    public static ConsoleReporter get2ConsoleReporter(MetricRegistry registry) {
        final ConsoleReporter reporter = ConsoleReporter.forRegistry(registry)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        return reporter;
    }

    /**
     * CVS
     *
     * @param registry  registry
     * @param file_Path cvs文件目录
     * @return CsvReporter {@link CsvReporter}
     */
    public static CsvReporter get2CsvReporter(MetricRegistry registry, String file_Path) {
        final CsvReporter reporter = CsvReporter.forRegistry(registry)
                .formatFor(Locale.US)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build(new File(file_Path));
        return reporter;
    }

    /**
     * @param registry    registry
     * @param logger_Name 日志名称
     * @return Slf4jReporter {@link Slf4jReporter}
     */
    public static Slf4jReporter get2Slf4jReporter(MetricRegistry registry, String logger_Name) {
        final Slf4jReporter reporter = Slf4jReporter.forRegistry(registry)
                .outputTo(LoggerFactory.getLogger(logger_Name))
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        return reporter;
    }
}
