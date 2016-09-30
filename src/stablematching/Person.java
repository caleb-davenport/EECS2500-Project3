package stablematching;

import java.util.LinkedList;

public abstract class Person {
    public final String     name;
    public final LinkedList preferences;
    protected    Person     partner;
    Person(String name, LinkedList preferences) {
        this.name = name;
        this.preferences = preferences;
    }
    
    public void removePartner() {
        partner = null;
    }
     
}
