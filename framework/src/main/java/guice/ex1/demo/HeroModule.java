package guice.ex1.demo;


import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class HeroModule implements Module {

    public void configure(Binder binder) {
        binder.bind(Vehicle.class).annotatedWith(Names.named("Fast")).to(WeaselCopter.class);
        binder.bind(Appendable.class).toInstance(System.out);
        loadProperties(binder);
        binder.bind(String.class).annotatedWith(Names.named("LicenseKey")).toInstance("QWERTY");
    }

    @Provides
    @Inject
    private Hero provideHero(FrogMan frogMan, WeaselGirl weaselGirl) {
        if (Math.random() > 0.5D) {
            return frogMan;
        }
        return weaselGirl;
    }

    @Provides
    @Inject
    private WeaselGirl provideWeaselGirl(WeaselCopter copter, Appendable recorder, @Named("WeaselSaying") String saying) {
        return new WeaselGirl(copter, recorder, saying);
    }

    private void loadProperties(Binder binder) {
        InputStream stream = HeroModule.class.getResourceAsStream("/guice/ex1/app.properties");
        Properties appProperties = new Properties();
        try {
            appProperties.load(stream);
            Names.bindProperties(binder, appProperties);
        } catch (IOException e) {
            binder.addError(e);
        }
    }

}
