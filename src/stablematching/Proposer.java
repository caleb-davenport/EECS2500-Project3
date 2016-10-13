package stablematching;

import java.util.NoSuchElementException;

public class Proposer extends Person {
    
    Acceptor fiance;

    public Proposer(String name) {
        super(name);
    }
    
    public Person nextPref() throws NoSuchElementException {
        return prefIter.next();
    }
        
}
