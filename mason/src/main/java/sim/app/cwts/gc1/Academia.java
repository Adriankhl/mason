package sim.app.cwts.gc1;

import sim.engine.*;
import sim.field.continuous.*;
import sim.util.Double2D;

import java.lang.Math;
import java.util.ArrayList;
import java.util.List;

public class Academia extends SimState {

    public Academia(long seed) {
        super(seed);
    }

    public Continuous2D yard = new Continuous2D(0.1,100,100);

    public int getNumResearchers() {
        return numResearchers;
    }

    public void setNumResearchers(int numResearchers) {
        this.numResearchers = numResearchers;
    }

    // Number of Researcher
    int numResearchers = 100;
    List<Researcher> allResearchers = new ArrayList<>();

    public double getStdQuality() {
        return stdQuality;
    }

    public void setStdQuality(double stdQuality) {
        this.stdQuality = stdQuality;
    }

    // Standard deviation of the quality of researchers
    double stdQuality = 1.0;

    public double getStdProposalQualityFactor() {
        return stdProposalQualityFactor;
    }

    public void setStdProposalQualityFactor(double stdProposalQualityFactor) {
        this.stdProposalQualityFactor = stdProposalQualityFactor;
    }

    // Standard deviation of the factor of quality of proposal
    double stdProposalQualityFactor = 1.0;

    // Amount of block funding and competitive funding
    //double totalBlockFunding = 100.0;
    //double totalCompetitiveFunding = 50.0;

    public double getCompetitiveFundingFactor() {
        return competitiveFundingFactor;
    }

    public void setCompetitiveFundingFactor(double competitiveFunding) {
        this.competitiveFundingFactor = competitiveFunding;
    }

    // competitive funding payoff multiplier
    double competitiveFundingFactor = 1.5;

    public int getNumCompetitiveFunding() {
        return numCompetitiveFunding;
    }

    public void setNumCompetitiveFunding(int numCompetitiveFunding) {
        this.numCompetitiveFunding = numCompetitiveFunding;
    }

    int numCompetitiveFunding = 5;

    public int getNumResearch() {
        return numResearch;
    }

    public void setNumResearch(int numResearch) {
        this.numResearch = numResearch;
        this.numProposal = this.numResearchers - numResearch;
    }

    // Strategy choice
    int numResearch = 70;

    public int getNumProposal() {
        return numProposal;
    }

    public void setNumProposal(int numProposal) {
        this.numProposal = numProposal;
        this.numResearch = this.numResearchers - numProposal;
    }

    int numProposal = numResearchers - numResearch;

    // List of researcher who choose proposal
    List<Researcher> proposalResearchers = new ArrayList<>();
    List<Double> acceptedProposal = new ArrayList<>();

    public double getTotalPayoff() {
        return totalPayoff;
    }

    public void setTotalPayoff(double totalPayoff) {
        this.totalPayoff = totalPayoff;
    }

    // Statistics
    double totalPayoff = 0.0;
    List<Double> totalPayoffs = new ArrayList<>();

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

        // clear List
        allResearchers.clear();
        proposalResearchers.clear();
        acceptedProposal.clear();
        totalPayoffs.clear();

        for(int i = 0; i < numResearchers; i++) {

            Researcher researcher = new Researcher();

            // Set researcher quality
            double quality = lognormal(1, stdQuality);
            researcher.setQuality(quality);
            researcher.lastpayoff = quality;

            if(i < numResearch) {
                researcher.setStrategy(Researcher.Strategy.RESEARCH);
            } else {
                researcher.setStrategy(Researcher.Strategy.PROPOSAL);
            }

            yard.setObjectLocation(researcher,
                    new Double2D(researcher.getQuality() * 20, researcher.lastpayoff * 20));

            allResearchers.add(researcher);

            schedule.scheduleRepeating(researcher);
        }

        EndOfTurn endOfTurn = new EndOfTurn();
        schedule.scheduleRepeating(Schedule.EPOCH, 1, endOfTurn);
    }

    public static void main(String[] args)
    {
        doLoop(Academia.class, args);
        System.exit(0);
    }
}
