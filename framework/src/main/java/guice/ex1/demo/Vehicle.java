package guice.ex1.demo;

import com.google.inject.ImplementedBy;

@ImplementedBy(FrogMobile.class)
interface Vehicle {

    String zoom();

}
