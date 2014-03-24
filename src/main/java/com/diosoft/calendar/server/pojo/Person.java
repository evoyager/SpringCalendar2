package com.diosoft.calendar.server.pojo;

import java.io.Serializable;

/**
 * Created by EVGENIY on 24.03.14.
 */
public class Person implements Serializable {
    private final String firstName;
    private final String secondName;
    private final int age;
    private final String email;

    public String getEmail() {
        return email;
    }

    private Person(Builder builder){
        this.firstName = builder.firstName;
        this.secondName = builder.secondName;
        this.age = builder.age;
        this.email = builder.email;
    }

    public boolean equals(Object obj){
        if (!(obj instanceof Person))
            return false;
        Person person = (Person)obj;
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

    public int getAge() {
        return age;
    }

    public String getSecondName() {
        return secondName;
    }

    public static class Builder {
        private String firstName;
        private String secondName;
        private int age;
        private String email;

        public Builder(){}

        public Builder setFirstName(String firstName){
            this.firstName = firstName;
            return this;
        }

        public Builder setSecondName(String secondName){
            this.secondName = secondName;
            return this;
        }

        public Builder setAge(int age){
            this.age = age;
            return this;
        }

        public Builder setEmail(String email){
            this.email = email;
            return this;
        }

        public Person build(){
            return new Person(this);
        }
    }
}
