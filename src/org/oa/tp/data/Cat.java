package org.oa.tp.data;


import com.google.gson.annotations.SerializedName;

public class Cat {
    @SerializedName("id")
    private long id;
    @SerializedName("breed")
    private String  breed;
    @SerializedName("tail")
    private boolean  tail;

    public Cat(long id, String breed, boolean tail) {
        this.id = id;
        this.breed = breed;
        this.tail = tail;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public boolean isTail() {
        return tail;
    }

    public void setTail(boolean tail) {
        this.tail = tail;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "id=" + id +
                ", breed='" + breed + '\'' +
                ", tail=" + tail +
                '}';
    }
}
