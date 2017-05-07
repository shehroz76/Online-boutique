package com.example.msk.onlinebotique.Pojo;

/**
 * Created by DELL on 5/7/2017.
 */

public class Shop {

    private String ItemImage;
    private String ItemPrice;
    private String ItemName;

    public Shop() {

    }

    public Shop(String itemImage, String itemPrice, String itemName) {
        ItemImage = itemImage;
        ItemPrice = itemPrice;
        ItemName = itemName;
    }

    public String getItemImage() {
        return ItemImage;
    }

    public void setItemImage(String itemImage) {
        ItemImage = itemImage;
    }

    public String getItemPrice() {
        return ItemPrice;
    }

    public void setItemPrice(String itemPrice) {
        ItemPrice = itemPrice;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }
}
