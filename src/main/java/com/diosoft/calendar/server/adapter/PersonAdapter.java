package com.diosoft.calendar.server.adapter;

import com.diosoft.calendar.server.pojo.Person;

/**
 * Created by EVGENIY on 24.03.14.
 */
public class PersonAdapter {

    private String firstName;
    private String secondName;
    private int age;
    private String email;

    public PersonAdapter(){}

    public PersonAdapter(Person person){
        this.firstName = person.getFirstName();
        this.secondName = person.getSecondName();
        this.email = person.getEmail();
        this.age = person.getAge();
    }

    public boolean equals(Object obj){
        if (!(obj instanceof Person))
            return false;
        PersonAdapter person = (PersonAdapter)obj;
        return firstName.equals(person.firstName) &&
                secondName.equals(person.secondName) &&
                age==person.age && email.equals(person.email);
    }

    public int hashCode(){
        int hash = 37;
        hash = hash + firstName.hashCode();
        hash = hash + secondName.hashCode();
        hash = hash + email.hashCode();
        return hash;
    }

    public String toString() {
        return firstName + " " + secondName + " " + age + " " + email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
