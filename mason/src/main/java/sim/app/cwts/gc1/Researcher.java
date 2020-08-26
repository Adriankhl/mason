package sim.app.cwts.gc1;

import sim.engine.*;

import java.util.ArrayList;
import java.util.List;

public class Researcher implements Steppable<Academia> {

    public enum Strategy {
        RESEARCH,
        PROPOSAL
    }

    private double quality = 1.0;
    private Strategy strategy = Strategy.RESEARCH;

    public double getQuality() {
        return quality;
    }

    public void setQuality(double quality) {
        this.quality = quality;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
            this.strategy = strategy;
    }

    // last payoff
    double lastpayoff = 0.0;

    // Payoffs of all turns
    List<Double> payoffs = new ArrayList<>();

    // Strategies of all turns
    List<Strategy> strategies = new ArrayList<>();

    @Override
    public void step(Academia state) {

        Academia academia = state;

    }
}
