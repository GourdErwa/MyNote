package guice.ex1.demo;

import com.google.inject.Inject;
import com.google.inject.Provider;

import java.io.IOException;

class Saga {
    private final Provider<Hero> heroProvider;

    @Inject
    public Saga(Provider<Hero> heroProvider) {
        this.heroProvider = heroProvider;
    }

    public void start() throws IOException {
        for (int i = 0; i < 3; i++) {
            Hero hero = heroProvider.get();
            hero.fightCrime();
        }
    }
}
