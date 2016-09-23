package guice.ex1.demo;

import com.google.inject.Inject;
import com.google.inject.Provider;

import java.util.Random;

/**
 * This is how you would implement a Provider "method" pre-Guice 2.0. Basically
 * you would have to host the Provider in its own class, and register it with
 * the binder:
 * <p>
 * binder.bind(Hero.class).toProvider(HallOfJustice.class);
 */
public class HallOfJustice implements Provider<Hero> {

    private FrogMan frog;
    private WeaselGirl weasel;
    private Random randomBoolean = new Random();

    @Inject
    public HallOfJustice(FrogMan frog, WeaselGirl weasel) {
        this.frog = frog;
        this.weasel = weasel;
    }

    public Hero get() {
        if (randomBoolean.nextBoolean()) {
            return frog;
        }
        return weasel;
    }
}
