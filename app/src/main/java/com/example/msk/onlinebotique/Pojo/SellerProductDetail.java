package com.example.msk.onlinebotique.Pojo;

/**
 * Created by DELL on 6/3/2017.
 */

public class SellerProductDetail {


    private String Image1 ;
    private String Image2 ;
    private String Image3 ;
    private String Image4 ;
    private String Image5 ;
    private String Image6 ;
    private String Product_Name ;
    private String Product_Type ;
    private String Product_price ;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String key;



    public SellerProductDetail(String image1, String image2, String image3
            , String image4, String image5, String image6, String product_Name,
                               String product_Type, String product_price , String Key) {
        Image1 = image1;
        Image2 = image2;
        Image3 = image3;
        Image4 = image4;
        Image5 = image5;
        Image6 = image6;
        Product_Name = product_Name;
        Product_Type = product_Type;
        Product_price = product_price;
        key = Key;

    }

    public SellerProductDetail() {
    }


    public String getImage1() {
        return Image1;
    }

    public void setImage1(String image1) {
        Image1 = image1;
    }

    public String getImage2() {
        return Image2;
    }

    public void setImage2(String image2) {
        Image2 = image2;
    }

    public String getImage3() {
        return Image3;
    }

    public void setImage3(String image3) {
        Image3 = image3;
    }

    public String getImage4() {
        return Image4;
    }

    public void setImage4(String image4) {
        Image4 = image4;
    }

    public String getImage5() {
        return Image5;
    }

    public void setImage5(String image5) {
        Image5 = image5;
    }

    public String getImage6() {
        return Image6;
    }

    public void setImage6(String image6) {
        Image6 = image6;
    }

    public String getProduct_Name() {
        return Product_Name;
    }

    public void setProduct_Name(String product_Name) {
        Product_Name = product_Name;
    }

    public String getProduct_Type() {
        return Product_Type;
    }

    public void setProduct_Type(String product_Type) {
        Product_Type = product_Type;
    }

    public String getProduct_price() {
        return Product_price;
    }

    public void setProduct_price(String product_price) {
        Product_price = product_price;
    }
}
