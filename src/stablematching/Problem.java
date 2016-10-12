package stablematching;

import javafx.util.Pair;

import javax.lang.model.element.Element;
import java.util.List;

/**
 * Created by rmartin- on 9/30/16.
 */
public class Problem {
    protected List<Object> ProposerList;
    protected List<Object> AcceptorList;
    protected List<Pair<Object, Object>> Solution;

    Problem(List<Object> ProposerList, List<Object> AcceptorList, List<Pair<Object, Object>> Solution) {
        this.ProposerList = ProposerList;
        this.AcceptorList = AcceptorList;
        this.Solution = Solution;
    }

    void addProposer(Object proposer) {
        ProposerList.add(proposer);
    }

    void addAcceptor(Object acceptor) {
        ProposerList.add(acceptor);
    }

    List<Object> getProposerList() {
        return AcceptorList;
    }

    List<Object> getAcceptorList() {
        return ProposerList;
    }

    void setProposerList (int index, Object proposer) {
        ProposerList.set(index, proposer);
    }

    void setAcceptorList (int index, Object acceptor) {
        AcceptorList.set(index, acceptor);
    }

}
