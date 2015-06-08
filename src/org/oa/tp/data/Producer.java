package org.oa.tp.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Producer {
    @SerializedName("id")
    private long id;
    @SerializedName("firstName")
    private String  firstName;
    @SerializedName("lastName")
    private  String lastName;
    @SerializedName("car_id")
    private  long car_id;
    @SerializedName("cat_id")
    private  long cat_id;
    @Expose
    private Car car;
    @Expose
    private Cat cat;

    public Producer(long id, String firstName, String lastName, long car_id, long cat_id) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.car_id = car_id;
        this.cat_id = cat_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getCat_id() {
        return cat_id;
    }

    public void setCat_id(long cat_id) {
        this.cat_id = cat_id;
    }

    public long getCar_id() {
        return car_id;
    }

    public void setCar_id(long car_id) {
        this.car_id = car_id;
    }

    public Cat getCat() {
        return cat;
    }

    public void setCat(Cat cat) {
        this.cat = cat;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "Producer{" +
                "catId=" + car_id +
                ", carId=" + cat_id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", id=" + id +
                '}';
    }
}
