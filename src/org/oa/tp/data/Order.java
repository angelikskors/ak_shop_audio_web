package org.oa.tp.data;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Order {
    @SerializedName("id")
    private long id;
    @SerializedName("date")
    private Date date;
    @SerializedName("audio_id")
    private  long audio_id;
    @SerializedName("amount")
    private  int amount;
    @SerializedName("customer_id")
    private  long customer_id;
    @Expose
    private Audio audio;
    @Expose
    private Customer customer;

    public Order(long id, Date date, long audio_id, int amount, long customer_id) {
        this.id = id;
        this.date = date;
        this.audio_id = audio_id;
        this.amount = amount;
        this.customer_id = customer_id;
    }

    public Audio getAudio() {
        return audio;
    }

    public void setAudio(Audio audio) {
        this.audio = audio;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public long getId() {
        return id;
    }



    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getAudio_id() {
        return audio_id;
    }

    public void setAudio_id(long audio_id) {
        this.audio_id = audio_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(long customer_id) {
        this.customer_id = customer_id;
    }

    @Override
    public String toString() {
        return "Order{" +
                "customer_id=" + customer_id +
                ", amount=" + amount +
                ", audio_id=" + audio_id +
                ", date=" + date +
                ", id=" + id +
                '}';
    }
}
