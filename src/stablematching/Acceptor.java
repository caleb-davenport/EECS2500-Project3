package stablematching;

import java.util.LinkedList;

public class Acceptor extends Person {

    public Acceptor(String name, LinkedList preferences) {
        super(name, preferences);
    }
    
    public Acceptor(String name) {
        super(name);
    }

    public boolean isPreferable(Proposer newProposer) throws Exception {
        int n = 0;
        for (Person p : preferences) {
            if (p == newProposer) return true;
            if (p == this.getPartner()) return false;
        }
        Exception e = new RuntimeException();
        e.initCause(new Throwable("Proposer not found in list!" + 
                                  "(THIS SHOULD NEVER HAAPEN!!)"));
        throw e;
    }
}
