package guice.ex1.crufty;

class FrogMobile {

    private HeavyWaterRefinery refinery = new HeavyWaterRefinery();

    FrogMobile() {
    }

    public FrogMobile(HeavyWaterRefinery refinery) {
        this.refinery = refinery;
    }


    String zoom() {
        refinery.refuel();
        return "The FrogMobile hops slowly accross the landscape.\n";
    }

}
