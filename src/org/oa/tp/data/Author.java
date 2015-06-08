package org.oa.tp.data;

import com.google.gson.annotations.SerializedName;


public class Author {
    @SerializedName("id")
    private final long id;
    @SerializedName("firstName")
    private final String firstName;
    @SerializedName("lastName")
    private final String lastName;
    @SerializedName("age")
    private int age;

    public Author(long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Author(long id, String firstName, String lastName, int age) {
       this(id,firstName,lastName);
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                '}';
    }
}
