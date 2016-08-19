package caliperbenchmark;

import caliperbenchmark.tutorial.Tutorial;
import com.google.caliper.runner.CaliperMain;

/**
 * @author wei.Li
 */
public class CaliperTest {


    public static void main(String[] args) {
        CaliperMain.main(Tutorial.Benchmark2.class, new String[]{});
    }
}
