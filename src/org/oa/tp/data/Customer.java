package org.oa.tp.data;


import com.google.gson.annotations.SerializedName;

public class Customer {

    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String  name;
    @SerializedName("phone")
    private  long phone;
    @SerializedName("address")
    private  String address;
    @SerializedName("rating")
    private  long rating;

    public Customer(long id, String name, long phone, String address, long rating) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.rating = rating;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone=" + phone +
                ", address='" + address + '\'' +
                ", rating=" + rating +
                '}';
    }
}
