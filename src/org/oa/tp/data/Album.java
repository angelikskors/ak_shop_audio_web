package org.oa.tp.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Album {
    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String  name;
    @SerializedName("year")
    private  int year;
    @SerializedName("producer_id")
    private  long producer_id;
    @SerializedName("country")
    private  String country;
    @SerializedName("language")
    private  String language;
    @Expose
    private Producer producer;



    public Album(long id, int year, String name, long producer_id, String country, String language) {
        this.id = id;
        this.year = year;
        this.name = name;
        this.producer_id=producer_id;
        this.country=country;
        this.language=language;
    }


    public long getProducer_id() {
        return producer_id;
    }

    public void setProducer_id(long producer_id) {
        this.producer_id = producer_id;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Album album = (Album) o;

        return id == album.id;

    }

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", producer='" + producer_id + '\'' +
                ", country='" + country + '\'' +
                ", language='" + language + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
