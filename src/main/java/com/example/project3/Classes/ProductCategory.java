package com.example.project3.Classes;

import com.example.project3.DataStructures.DLinkedList;

public class ProductCategory implements Comparable<ProductCategory>{

    private int categoryID;
    private String categoryName;
    private String description;
    private DLinkedList<Product> productList;

    public ProductCategory(int categoryID, String categoryName, String description){
        setCategoryID(categoryID);
        setCategoryName(categoryName);
        setDescription(description);
        productList = new DLinkedList<>();
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DLinkedList<Product> getProductList() {
        return productList;
    }

    public void setProductList(DLinkedList<Product> productList) {
        this.productList = productList;
    }

    public int compareTo(ProductCategory o) {
        return categoryName.compareTo(o.getCategoryName());
    }

    public int productCount(){
        return productList.size();
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof ProductCategory){
            if (categoryID == ((ProductCategory)o).categoryID)
                return true;
            return false;
        }
        return false;
    }
}
