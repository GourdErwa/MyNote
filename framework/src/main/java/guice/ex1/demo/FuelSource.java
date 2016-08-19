package guice.ex1.demo;

import com.google.inject.ImplementedBy;

@ImplementedBy(HeavyWaterRefinery.class)
interface FuelSource {

    String refuel();
}
