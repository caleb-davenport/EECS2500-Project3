package stablematching;

import java.util.LinkedList;

public class Acceptor extends Person {

    public Acceptor(String name, LinkedList preferences) {
        super(name, preferences);
    }
    
    public Acceptor(String name) {
        super(name);
    }

    public boolean isBetterPartner(Proposer newProposer) {
        int n = 0;
        Object currentElement = preferences.get(n);
        while (true) {
            if (currentElement == newProposer) return true;
            if (currentElement == this.getPartner()) return true;
            currentElement = preferences.get(++n);
        }
    }
}
