package sample;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

public class Person implements Serializable {

    public static final String FIRSTNAME_CHANGED = "Firstname_changed";
    public static final String LASTNAME_CHANGED = "Lastname_changed";
    public static final String AGE_CHANGED = "Age_changed";

    private String firstname;
    private String lastname;
    private int age;

    private PropertyChangeSupport support;

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public int getAge() {
        return age;
    }

    public void setFirstname(String firstname){
        this.support.firePropertyChange(FIRSTNAME_CHANGED, this.firstname, firstname);
        this.firstname = firstname;
    }

    public void setLastname(String lastname){
        this.support.firePropertyChange(LASTNAME_CHANGED, this.lastname, lastname);
        this.lastname = lastname;
    }

    public void setAge(int age){
        this.support.firePropertyChange(AGE_CHANGED, this.age, age);
        this.age = age;
    }


    public Person(){
        this.support = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl){
        this.support.addPropertyChangeListener(pcl);
    }
}
