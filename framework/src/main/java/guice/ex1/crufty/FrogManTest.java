package guice.ex1.crufty;

import junit.framework.TestCase;

import java.io.IOException;

public class FrogManTest extends TestCase {

    public void testFrogManFightsCrime() throws IOException {
        FrogMan hero = new FrogMan();
        hero.fightCrime();
        //make some assertions...
    }
}
