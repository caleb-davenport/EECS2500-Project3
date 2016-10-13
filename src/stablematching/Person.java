package stablematching;

import java.util.LinkedList;
import java.util.ListIterator;

public abstract class Person {
    private final String             name;
    public LinkedList<Person> preferences;
    private      Person             partner;
    ListIterator<Person> prefIter;
    public boolean isEngaged = false;
    
    Person(String name) {
        this.name = name;
    }
    
    public void removePartner() {
        if (isEngaged = true) {
           partner = null; 
           isEngaged = false;
        }
    }
    
    public void setPartner(Person partner) {
        this.partner = partner;
    }
    
    public void setPreferences(LinkedList<Person> preferences) {
        this.preferences = preferences;
        prefIter = preferences.listIterator();
    }
    
    public String outputPreferences() {
        String s = "";
        for (int i = 0; i < 5; i++) {
            s = s.concat(preferences.get(i).getName());
        }
        return s;
    }
    
    public Person getPartner() {
        return this.partner;
    }
    
    public String getPartnerName() {
        return this.partner.name;
    }
    
    public String getName() {
        return this.name;
    }
     
}
