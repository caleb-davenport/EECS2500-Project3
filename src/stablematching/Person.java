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
        return "Test";
        //return this.partner.name;
    }
    
    public String getName() {
        return this.name;
    }
     
}
