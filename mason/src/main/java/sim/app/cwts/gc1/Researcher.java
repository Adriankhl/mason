package sim.app.cwts.gc1;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.Double2D;

import java.util.ArrayList;
import java.util.List;

public class Researcher implements Steppable {

    public enum Strategy {
        RESEARCH,
        PROPOSAL
    }

    private double quality = 0.0;
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

    public double getLastpayoff() {
        return lastpayoff;
    }

    public void setLastpayoff(double lastpayoff) {
        this.lastpayoff = lastpayoff;
    }

    // last payoff
    double lastpayoff = quality;

    // Payoffs of all turns
    List<Double> payoffs = new ArrayList<>();

    // Strategies of all turns
    List<Strategy> strategies = new ArrayList<>();

    // Proposal quality
    private double proposalQuality = 0.0;

    public double getProposalQuality() {
        return proposalQuality;
    }

    private Strategy pickStrategy(Integer numApplicant, Integer numGrant, double payoff, double lowestQuality,
                                  double avgQuality) {
        Strategy newStrategy = strategy;
        if (lastpayoff < quality)
            newStrategy = Strategy.RESEARCH;

        double chance;

        if (numApplicant < numGrant) {
            chance = 1.0;
        } else {
            chance = numGrant.doubleValue() / numApplicant.doubleValue();
        }

        //System.out.println(lowestQuality);
        if (payoff > lastpayoff) {
            if (quality > avgQuality || quality > lowestQuality)
                newStrategy = Strategy.PROPOSAL;
            else if (chance * (payoff - quality) > 0.1)
                newStrategy = Strategy.PROPOSAL;
        }

        return newStrategy;
    }

    @Override
    public void step(SimState state) {
        Academia academia = (Academia) state;
        // Record payoffs and strategy in the last turn
        payoffs.add(lastpayoff);
        strategies.add(strategy);

        strategy = pickStrategy(academia.numProposal,
                academia.numCompetitiveFunding,
                academia.competitiveFunding,
                academia.acceptedProposal.stream().mapToDouble(Double::doubleValue).min().orElse(-1),
                academia.acceptedProposal.stream().mapToDouble(Double::doubleValue).average().orElse(-1));

        if (strategy == Strategy.RESEARCH) {
            lastpayoff = quality;
        } else if (strategy == Strategy.PROPOSAL) {
            lastpayoff = 0.0;
            proposalQuality = academia.lognormal(1., academia.stdProposalQualityFactor) * quality;
            academia.proposalResearchers.add(this);
        }

        //System.out.println(academia.yard.getObjectLocation(this).y);

        academia.yard.setObjectLocation(this,
                new Double2D(quality * 20, payoffs.stream().mapToDouble(Double::doubleValue).average().orElse(-1)));
    }
}
