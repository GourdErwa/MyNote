package guice.ex1.demo;

import com.google.inject.Inject;

import java.io.IOException;

class FrogMan implements Hero {
    @Inject
    Appendable recorder;
    private Vehicle vehicle;

    @Inject
    public FrogMan(Vehicle vehicle) {
        this.vehicle = vehicle;
    }


    public void fightCrime() throws IOException {
        recorder.append("FrogMan Lives!\n");
        recorder.append(vehicle.zoom()).append("\n");
    }

}
