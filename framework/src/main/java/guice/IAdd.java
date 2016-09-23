package guice;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * @author wei.Li
 */
interface IAdd {
    int add(int a, int b);
}

class SimpleIAdd implements IAdd {

    public int add(int a, int b) {
        return a + b;
    }

}

class AddModule implements Module {

    public void configure(Binder binder) {
        binder.bind(IAdd.class).to(SimpleIAdd.class);
    }

}

class AddClient {

    public static void main(String[] args) {

        Injector injector = Guice.createInjector(new AddModule());
        IAdd IAdd = injector.getInstance(IAdd.class);
        System.out.println(IAdd.add(10, 54));
    }
}
