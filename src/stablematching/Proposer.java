package stablematching;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class Proposer extends Person {
    
    ListIterator<Acceptor> prefIter;
    Acceptor fiance;

    public Proposer(String name, LinkedList<Acceptor> preferences) {
        super(name, preferences);
        prefIter = preferences.listIterator();
    }

    public Proposer(String name) {
        super(name);
    }
    
    public Acceptor nextPref() throws NoSuchElementException {
        return prefIter.next();
    }
        
}
