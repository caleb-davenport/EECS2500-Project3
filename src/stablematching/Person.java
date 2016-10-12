package stablematching;

import java.util.LinkedList;

public abstract class Person {
    private final String             name;
    public LinkedList<Person> preferences;
    private      Person             partner;
    Person(String name, LinkedList preferences) {
        this.name = name;
        this.preferences = preferences;
    }
    
    Person(String name) {
        this.name = name;
    }
    
    public void removePartner() {
        partner = null;
    }
    
    public void setPartner(Person partner) {
        this.partner = partner;
    }
    
    public void setPreferences(LinkedList<Person> preferences) {
        this.preferences = preferences;
    }
    
    public Person getPartner() {
        return this.partner;
    }
    
    public String getName() {
        return this.name;
    }
     
}
