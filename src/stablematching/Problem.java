package stablematching;

import java.util.LinkedList;
import javafx.util.Pair;

import javax.lang.model.element.Element;
import java.util.List;
import javafx.collections.ObservableList;

/**
 * Created by rmartin- on 9/30/16.
 */
public class Problem {
    protected List<Proposer> ProposerList;
    protected List<Acceptor> AcceptorList;
    protected ObservableList<Pair<Proposer, Acceptor>> Solution;

    Problem(List<Proposer> ProposerList, List<Acceptor> AcceptorList, ObservableList<Pair<Proposer, Acceptor>> Solution) {
        this.ProposerList = ProposerList;
        this.AcceptorList = AcceptorList;
        this.Solution = Solution;
    }

    void addProposer(Proposer proposer) {
        ProposerList.add(proposer);
    }

    void addAcceptor(Acceptor acceptor) {
        AcceptorList.add(acceptor);
    }

    List<Proposer> getProposerList() {
        return ProposerList;
    }

    List<Acceptor> getAcceptorList() {
        return AcceptorList;
    }

    void setProposerList (int index, Proposer proposer) {
        ProposerList.set(index, proposer);
    }

    void setAcceptorList (int index, Acceptor acceptor) {
        AcceptorList.set(index, acceptor);
    }
    
//    void facilitateProposal(Proposer p) {
//        switch (p) {
//            case
//        }
//    }
    
    LinkedList<Proposer> getUnmatchedProposers(List<Proposer> people) {
        LinkedList<Proposer> unmatchedPeople = new LinkedList<>();
        for (Proposer p : people) {
            if (p.getPartner() == null) {
                unmatchedPeople.add(p);
            }
        }
        return unmatchedPeople;
    }

}
