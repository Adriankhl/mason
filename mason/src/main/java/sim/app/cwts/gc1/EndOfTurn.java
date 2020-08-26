package sim.app.cwts.gc1;

import sim.engine.Steppable;

import java.util.ArrayList;
import java.util.Comparator;
import java.lang.Math;
import java.util.List;

public class EndOfTurn implements Steppable<Academia> {
    @Override
    public void step(Academia state) {
        // Clean collection
        state.acceptedProposal.clear();

        var proposals = state.proposalResearchers;
        proposals.sort(Comparator.comparingDouble(Researcher::getProposalQuality).reversed());

        List<Researcher> topProposals = new ArrayList<>();

        if (proposals.size() > 0)
            proposals.subList(0, Math.max(state.numCompetitiveFunding, proposals.size()));

        for(Researcher researcher: topProposals) {
            researcher.lastpayoff = state.competitiveFunding;
            state.acceptedProposal.add(researcher.getProposalQuality());
        }

        state.totalPayoff = 0.0;
        for(Researcher researcher: state.allResearchers) {
            state.totalPayoff += researcher.lastpayoff;
        }

        state.totalPayoffs.add(state.totalPayoff);

        state.numResearch = Math.toIntExact(state.allResearchers.stream()
                .filter(p -> p.getStrategy() == Researcher.Strategy.RESEARCH).count());

        state.numProposal = Math.toIntExact(state.allResearchers.stream()
                .filter(p -> p.getStrategy() == Researcher.Strategy.PROPOSAL).count());


        // clear list
        state.proposalResearchers.clear();
    }
}
