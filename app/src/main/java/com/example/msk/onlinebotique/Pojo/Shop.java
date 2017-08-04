package com.example.msk.onlinebotique.Pojo;

/**
 * Created by DELL on 5/7/2017.
 */

public class Shop {

    private String Location;
    private String OwnerName;
    private String OwnerUuid;
    private String ShopCategory;
    private String ShopDesc;
    private String ShopImage;
    private String ShopName;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Shop() {

    }

    public Shop(String location, String ownerName, String ownerUuid, String shopCategory, String shopDesc, String shopImage, String shopName) {
        Location = location;
        OwnerName = ownerName;
        OwnerUuid = ownerUuid;
        ShopCategory = shopCategory;
        ShopDesc = shopDesc;
        ShopImage = shopImage;
        ShopName = shopName;

    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getOwnerName() {
        return OwnerName;
    }

    public void setOwnerName(String ownerName) {
        OwnerName = ownerName;
    }

    public String getOwnerUuid() {
        return OwnerUuid;
    }

    public void setOwnerUuid(String ownerUuid) {
        OwnerUuid = ownerUuid;
    }

    public String getShopCategory() {
        return ShopCategory;
    }

    public void setShopCategory(String shopCategory) {
        ShopCategory = shopCategory;
    }

    public String getShopDesc() {
        return ShopDesc;
    }

    public void setShopDesc(String shopDesc) {
        ShopDesc = shopDesc;
    }

    public String getShopImage() {
        return ShopImage;
    }

    public void setShopImage(String shopImage) {
        ShopImage = shopImage;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }
}
