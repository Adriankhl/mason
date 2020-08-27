package sim.app.cwts.gc1;

import sim.engine.SimState;
import sim.engine.Steppable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EndOfTurn implements Steppable {
    @Override
    public void step(SimState state) {
        Academia academia = (Academia) state;

        // Clean collection
        academia.acceptedProposal.clear();

        var proposals = academia.proposalResearchers;
        proposals.sort(Comparator.comparingDouble(Researcher::getProposalQuality).reversed());

        List<Researcher> topProposals = new ArrayList<>();

        if (proposals.size() > 0)
            topProposals = proposals.subList(0, Math.min(academia.numCompetitiveFunding, proposals.size()));

        for (Researcher researcher : topProposals) {
            researcher.lastpayoff = academia.competitiveFundingFactor * researcher.getProposalQuality();
            System.out.println(researcher.getProposalQuality());
            academia.acceptedProposal.add(researcher.getProposalQuality());
        }

        academia.totalPayoff = 0.0;
        for (Researcher researcher : academia.allResearchers) {
            academia.totalPayoff += researcher.lastpayoff;
        }
        //System.out.println(academia.totalPayoff);

        academia.totalPayoffs.add(academia.totalPayoff);

        academia.numResearch = Math.toIntExact(academia.allResearchers.stream()
                .filter(p -> p.getStrategy() == Researcher.Strategy.RESEARCH).count());

        academia.numProposal = Math.toIntExact(academia.allResearchers.stream()
                .filter(p -> p.getStrategy() == Researcher.Strategy.PROPOSAL).count());


        // clear list
        academia.proposalResearchers.clear();
    }
}
