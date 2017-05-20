package com.example.msk.onlinebotique.Pojo;

import java.security.PrivateKey;

/**
 * Created by DELL on 5/20/2017.
 */

public class orders {

    private String OrderProfileImage;
    private String Name;
    private String noOfOrders;

    public orders() {
    }

    public orders(String orderProfileImage, String name, String noOfOrders) {
        OrderProfileImage = orderProfileImage;
        Name = name;
        this.noOfOrders = noOfOrders;
    }

    public String getOrderProfileImage() {
        return OrderProfileImage;
    }

    public void setOrderProfileImage(String orderProfileImage) {
        OrderProfileImage = orderProfileImage;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNoOfOrders() {
        return noOfOrders;
    }

    public void setNoOfOrders(String noOfOrders) {
        this.noOfOrders = noOfOrders;
    }
}
