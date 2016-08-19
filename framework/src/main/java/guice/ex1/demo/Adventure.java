package guice.ex1.demo;

import com.google.inject.Guice;
import com.google.inject.Injector;

import java.io.IOException;


public class Adventure {
    public static void main(String[] args) throws IOException {
        Injector injector = Guice.createInjector(new HeroModule());
        Saga saga = injector.getInstance(Saga.class);
        saga.start();
    }
}
