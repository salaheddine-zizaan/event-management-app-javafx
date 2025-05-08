package org.example.eventmanagement.Model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Event {
    private int id;
    private Integer organizerId;
    private Integer adminId;
    private boolean approved;
    private int capacity;
    private double price;
    private LocalDate date;
    private String location;
    private String city;
    private String country;
    private LocalTime startTime;
    private LocalTime endTime;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(Integer organizerId) {
        this.organizerId = organizerId;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }


    public Event(int id, Integer organizerId, Integer adminId, boolean approved, int capacity, double price, LocalDate date, String location, String city, String country, LocalTime startTime, LocalTime endTime) {
        this.id = id;
        this.organizerId = organizerId;
        this.adminId = adminId;
        this.approved = approved;
        this.capacity = capacity;
        this.price = price;
        this.date = date;
        this.location = location;
        this.city = city;
        this.country = country;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Event() {
        this.id = id;
        this.organizerId = organizerId;
        this.adminId = adminId;
        this.approved = approved;
        this.capacity = capacity;
        this.price = price;
        this.date = date;
        this.location = location;
        this.city = city;
        this.country = country;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
