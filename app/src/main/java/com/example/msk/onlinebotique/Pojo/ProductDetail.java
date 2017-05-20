package com.example.msk.onlinebotique.Pojo;

/**
 * Created by DELL on 5/8/2017.
 */

public class ProductDetail {



    private String product_image;
    private String product_name;
    private String product_category;
    private int product_price;
    private int product_total_price;
    private int product_quantity;

    public ProductDetail() {
    }

    public ProductDetail(String product_image, String product_name, String product_category, int product_price, int product_total_price, int product_quantity) {
        this.product_image = product_image;
        this.product_name = product_name;
        this.product_category = product_category;
        this.product_price = product_price;
        this.product_total_price = product_total_price;
        this.product_quantity = product_quantity;
    }


    public ProductDetail(String product_image, String product_name, String product_category, int product_price) {
        this.product_image = product_image;
        this.product_name = product_name;
        this.product_category = product_category;
        this.product_price = product_price;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_category() {
        return product_category;
    }

    public void setProduct_category(String product_category) {
        this.product_category = product_category;
    }

    public int getProduct_price() {
        return product_price;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }

    public int getProduct_total_price() {
        return product_total_price;
    }

    public void setProduct_total_price(int product_total_price) {
        this.product_total_price = product_total_price;
    }

    public int getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(int product_quantity) {
        this.product_quantity = product_quantity;
    }
}
