package com.example.shoppingbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.Date;


public class Container{
    private String itemID;
    private String itemName;
    private double cost;
    private String description;
    private String category;
    private String documentId;
    private String imageURL;
    private Long quantity;
    private String size;
    private String email;
    private String promoid;
    private String promocode;
    private String price;
    private String pdes;
    private String message, sender, reciever;

    public Container(String itemID, String itemName, double cost, String description, String category, String documentId, String imageURL) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.cost = cost;
        this.description = description;
        this.category = category;
        this.documentId = documentId;
        this.imageURL = imageURL;
    }

    public Container(String itemID, String itemName, double cost, String description, String category, String documentId, String imageURL, Long quantity, String size, String email) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.cost = cost;
        this.description = description;
        this.category = category;
        this.documentId = documentId;
        this.imageURL = imageURL;
        this.quantity = quantity;
        this.size = size;
        this.email = email;
    }

    public Container(String promoid, String promocode, String price, String documentId, String pdes) {
        this.promoid = promoid;
        this.promocode = promocode;
        this.price = price;
        this.documentId = documentId;
        this.pdes=pdes;
    }


    public Container(String itemID, String itemName, double cost, String documentId, String imageURL) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.cost = cost;
        this.documentId = documentId;
        this.imageURL = imageURL;
    }

    public Container(String documentId, String message, String sender, String reciever) {
        this.documentId = documentId;
        this.message = message;
        this.sender = sender;
        this.reciever = reciever;
    }

    public String getSender() {
        return sender;
    }

    public String getReciever() {
        return reciever;
    }

    public String getMessage() {
        return message;
    }

    public String getItemID() {
        return itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public double getCost() {
        return cost;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getDocumentId() {
        return documentId;
    }

    public String getImageURL() {
        return imageURL;
    }

    public Long getQuantity() {
        return quantity;
    }

    public String getSize() {
        return size;
    }

    public String getEmail() {
        return email;
    }

    public String getPromoid() {
        return promoid;
    }

    public String getPromocode() {
        return promocode;
    }

    public String getPrice() {
        return price;
    }

    public String getPdes() {
        return pdes;
    }
}

