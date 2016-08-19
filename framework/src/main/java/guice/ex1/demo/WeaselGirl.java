package guice.ex1.demo;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.io.IOException;

class WeaselGirl implements Hero {

    private final Vehicle vehicle;
    private Appendable recorder;
    private String saying;

    @Inject
    public WeaselGirl(Vehicle vehicle, Appendable recorder, @Named("WeaselSaying") String saying) {
        this.vehicle = vehicle;
        this.recorder = recorder;
        this.saying = saying;
    }

    public void fightCrime() throws IOException {
        recorder.append("Weasel girl chatters her teeth!\n");
        recorder.append("And shouts, '").append(saying).append("\n");
        recorder.append(vehicle.zoom()).append("\n");
    }


}
