package sim.app.cwts.gc1;

import sim.engine.*;

import java.util.ArrayList;
import java.util.List;

public class Researcher implements Steppable {

    public enum Strategy {
        RESEARCH,
        PROPOSAL
    }

    double quality = 1.0;

    List<Double> payoffs = new ArrayList<>();

    @Override
    public void step(SimState state) {

    }
}
