package guice.ex1.crufty;

import com.google.inject.Inject;

import java.io.IOException;

class FrogMan {

    @Inject
    private
    Appendable recorder = System.out;

    private FrogMobile vehicle = new FrogMobile();

    public FrogMan(FrogMobile vehicle) {
        this.vehicle = vehicle;
    }


    FrogMan() {
    }


    void fightCrime() throws IOException {
        recorder.append("FrogMan Lives!\n");
        recorder.append(vehicle.zoom()).append("\n");
    }
}
