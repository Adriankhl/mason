package sim.app.cwts.gc1;

import sim.engine.*;
import sim.field.continuous.*;
import sim.util.Double2D;

import java.lang.Math;

public class Academia extends SimState {

    public Academia(long seed) {
        super(seed);
    }

    public Continuous2D yard = new Continuous2D(1.0,100,100);

    // Number of Researcher
    int numResearchers = 100;

    // Standard deviation of the quality of researchers
    double stdQuality = 1;

    // Amount of block funding and competitive funding
    double totalBlockFunding = 100.0;
    double totalCompetitiveFunding = 50.0;
    int numCompetitiveFunding = 5;

    // Initial strategy choice
    int initNumResearch = 70;
    int iniNumProposal = numResearchers - initNumResearch;

    // Compute lognormal random number by mean and std of the lognormal distribution
    public double lognormal(double mean, double std) {
        double mu = Math.log(mean * mean / Math.sqrt(mean * mean + std * std));
        double sigma = Math.log(1 + std * std / (mean * mean));
        return Math.exp(mu + sigma * random.nextGaussian());
    }

    @Override
    public void start() {
        super.start();

        // clear the yard
        yard.clear();

        for(int i = 0; i < numResearchers; i++) {

            Researcher researcher = new Researcher();

            // Set researcher quality
            double quality = lognormal(1, stdQuality);
            researcher.setQuality(quality);

            if(i < initNumResearch) {
                researcher.setStrategy(Researcher.Strategy.RESEARCH);
            } else {
                researcher.setStrategy(Researcher.Strategy.PROPOSAL);
            }

            yard.setObjectLocation(researcher,
                    new Double2D(researcher.getQuality(), researcher.lastpayoff));

            schedule.scheduleRepeating(researcher);
        }
    }

    public static void main(String[] args)
    {
        doLoop(Academia.class, args);
        System.exit(0);
    }
}
