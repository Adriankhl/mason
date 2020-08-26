package sim.app.cwts.gcmodel1;

import sim.engine.*;
import sim.field.continuous.*;
import sim.util.*;
import sim.field.network.*;

import java.util.ArrayList;
import java.util.List;

public class Researcher implements Steppable {

    double quality = 1.0;

    List<Double> payoffs = new ArrayList<>();

    @Override
    public void step(SimState state) {

    }
}
