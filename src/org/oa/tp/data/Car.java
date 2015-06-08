package org.oa.tp.data;


import com.google.gson.annotations.SerializedName;

public class Car {

    @SerializedName("id")
    private long id;
    @SerializedName("brand")
    private String  brand;

    public Car(long id, String brand) {
        this.id = id;
        this.brand = brand;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                '}';
    }
}
