package com.kallucompound.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;

    private int rating;

    private String message;

    public Feedback() {
    }

    public Feedback(Long customerId, int rating, String message) {
        this.customerId = customerId;
        this.rating = rating;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public int getRating() {
        return rating;
    }

    public String getMessage() {
        return message;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}