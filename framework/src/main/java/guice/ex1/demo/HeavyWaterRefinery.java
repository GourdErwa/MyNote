package guice.ex1.demo;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

@Singleton
class HeavyWaterRefinery implements FuelSource {

    @Inject
    public HeavyWaterRefinery(@Named("LicenseKey") String key) {
        if (!key.startsWith("QWERTY")) {
            throw new RuntimeException("Invalid License");
        }
        System.out.println("Manufacturing deuterium oxide\n");
    }

    public String refuel() {
        return "Filling with deuterium oxide\n";
    }
}
