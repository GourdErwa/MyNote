package plug.jcommander;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lw on 14-6-30.
 * 参数注入
 */
public class JCommander_Ex {

    public static void main(String[] args) {
        Jcommader_Example jct = new Jcommader_Example();
        String[] argv = {"-log", "2", "-groups", "unit"};
        new JCommander(jct, argv);


    }
}

class Jcommader_Example {
    @Parameter
    private List<String> parameters = new ArrayList<String>();

    @Parameter(names = {"-log", "-verbose"}, description = "Level of verbosity")
    private Integer verbose = 1;

    @Parameter(names = "-groups", description = "Comma-separated list of group names to be run")
    private String groups;

    @Parameter(names = "-debug", description = "Debug mode")
    private boolean debug = false;


    public List<String> getParameters() {
        return parameters;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }

    public Integer getVerbose() {
        return verbose;
    }

    public void setVerbose(Integer verbose) {
        this.verbose = verbose;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}
