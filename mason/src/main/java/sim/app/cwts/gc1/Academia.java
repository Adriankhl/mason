package sim.app.cwts.gc1;

import sim.engine.*;
import sim.field.continuous.*;

public class Academia extends SimState {

    public Academia(long seed) {
        super(seed);
    }

    public Continuous2D yard = new Continuous2D(1.0,100,100);

    // Amount of block funding
    double totalBlockFunding = 100.0;
    double totalCompetitiveFunding = 50.0;
    int numCompetitiveFunding = 5;

    @Override
    public void start() {
        super.start();
    }

    public static void main(String[] args)
    {
        doLoop(Academia.class, args);
        System.exit(0);
    }
}
