package sim.app.cwts.gc1;

import sim.engine.*;
import sim.util.Double2D;

import java.util.ArrayList;
import java.util.List;
import java.lang.Integer;

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

    private Strategy pickStrategy(Integer numApplicant, Integer numGrant, double payoff, double lowestQuality, double avgQuality) {
        Strategy newstrategy = strategy;
        double chance = numGrant.doubleValue() / numApplicant.doubleValue();
        if (lowestQuality != -1) { // -1 when the collection is empty - first round
            if (payoff > lastpayoff) {
                if (quality > avgQuality || quality > lowestQuality)
                    newstrategy = Strategy.PROPOSAL;
                else if (chance * payoff > quality)
                    newstrategy = Strategy.PROPOSAL;
            }
        }

        return newstrategy;
    }

    @Override
    public void step(Academia state) {
        // Record payoffs and strategy in the last turn
        payoffs.add(lastpayoff);
        strategies.add(strategy);

        strategy = pickStrategy(state.numProposal,
                state.numCompetitiveFunding,
                state.competitiveFunding,
                state.acceptedProposal.stream().mapToDouble(Double::doubleValue).min().orElse(-1),
                state.acceptedProposal.stream().mapToDouble(Double::doubleValue).average().orElse(-1));

        if (strategy == Strategy.RESEARCH) {
            lastpayoff = quality;
        } else if (strategy == Strategy.PROPOSAL) {
            lastpayoff = 0.0;
            proposalQuality = state.lognormal(1., state.stdProposalQualityFactor) * quality;
        }

        System.out.println(state.yard.getObjectLocation(this).y);

        state.yard.setObjectLocation(this,
                new Double2D(quality, payoffs.stream().mapToDouble(Double::doubleValue).average().orElse(-1)));
    }
}
