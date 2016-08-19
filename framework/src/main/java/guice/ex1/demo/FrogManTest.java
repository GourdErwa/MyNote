package guice.ex1.demo;

import junit.framework.TestCase;

import java.io.IOException;

public class FrogManTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testFightCrime() throws IOException {
        MockVehicle mockVehicle = new MockVehicle();
        StringBuilder recorder = new StringBuilder();

        FrogMan hero = new FrogMan(mockVehicle);
//	  FrogMan hero = new FrogMan();
//	  hero.configure(mockVehicle, recorder);
        hero.recorder = recorder;
        hero.fightCrime();

        assertTrue(mockVehicle.didZoom);
    }

    private static class MockVehicle implements Vehicle {

        boolean didZoom;

        public String zoom() {
            this.didZoom = true;
            return "Mock Vehicle Zoomed.";
        }

    }

}
