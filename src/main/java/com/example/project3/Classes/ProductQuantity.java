package com.example.project3.Classes;

//This class for inventory summery
public class ProductQuantity implements Comparable<ProductQuantity>{

    private String prodName;
    private int quantity;

    public ProductQuantity(String prodName, int quantity) {
        this.prodName = prodName;
        this.quantity = quantity;
    }

    public String getProdName() {
        return prodName;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public int compareTo(ProductQuantity o) {
        return o.quantity-quantity;
    }
}
