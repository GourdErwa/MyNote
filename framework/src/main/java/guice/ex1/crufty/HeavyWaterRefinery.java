package guice.ex1.crufty;

class HeavyWaterRefinery {

    HeavyWaterRefinery() {
        throw new RuntimeException("Refinery startup failure.");
    }

    String refuel() {
        return "Manufacturing deuterium oxide\n";
    }
}
