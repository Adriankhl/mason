package sim.app.cwts.gcmodel1;

import sim.app.tutorial.Students;
import sim.engine.*;
import sim.field.continuous.*;

public class Academia extends SimState {

    public Academia(long seed) {
        super(seed);
    }

    public Continuous2D yard = new Continuous2D(1.0,100,100);

    @Override
    public void start() {
        super.start();
    }

    public static void main(String[] args)
    {
        doLoop(Students.class, args);
        System.exit(0);
    }
}
