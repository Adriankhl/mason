package sim.app.cwts.gc1;

import sim.engine.Steppable;

import java.util.Comparator;

public class EndOfTurn implements Steppable<Academia> {
    @Override
    public void step(Academia state) {
        var proposals = state.proposalResearchers;
        proposals.sort(Comparator.comparingDouble(Researcher::getProposalQuality).reversed());
        var topProposals = proposals.subList(0, state.numCompetitiveFunding);

        for(Researcher researcher: topProposals) {
            researcher.lastpayoff = state.competitiveFundingFactor * researcher.getProposalQuality();
        }
    }
}
