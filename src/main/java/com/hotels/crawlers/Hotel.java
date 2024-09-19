package com.hotels.crawlers;

public class Hotel {
    public String name;
    public String address;
    public String location;
    public String price;
    public String rating;
    public String description;
    public String descriptionHeading;
    public String imageUrl;
    public String reviewCount;
    public static void print(Hotel hotel) {
        System.out.println("====Hotel====");
        System.out.println("Name: " + hotel.name);
        System.out.println("Address: " + hotel.address);
        System.out.println("Location: " + hotel.location);
        System.out.println("Price: " + hotel.price);
        System.out.println("Rating: " + hotel.rating);
        if(hotel.description != null)
            System.out.println("Description: " + hotel.description);
        System.out.println("=============");
    }
    public Hotel(String name, String address, String location, String price, String rating, String description) {
        this.name = name;
        this.address = address;
        this.location = location;
        this.price = price;
        this.rating = rating;
        this.description = description;
    }
    public Hotel(String name, String price, String location, String rating, String phone) {
        this.name = name;
        this.price = price;
        this.location = location;
        this.rating = rating;
        this.reviewCount = phone;
    }
    public Hotel(String name, String price, String location, String rating, String description, boolean b) {
        this.name = name;
        this.price = price;
        this.location = location;
        this.rating = rating;
        this.description = description;
    }
    public Hotel(String name, String location, String price) {
        this.name = name;
        this.price = price;
        this.location = location;
    }
    public Hotel(String name, String location, String price, String rating) {
        this.name = name;
        this.price = price;
        this.location = location;
        this.rating = rating;
    }
}