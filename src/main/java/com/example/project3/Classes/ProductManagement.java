package com.example.project3.Classes;

import java.util.ListIterator;

public class ProductManagement {

    public static int productID = 1;

    public static void addProduct(Product product) {

        if (findProductById(product.getProductID()) != null)
            throw new AlertException("This product ID already exists.");

        ProductCategory cat = CategoryManagement.searchCategory(product.getCategoryName());

        if (cat == null){//To create new category if the category does not exist
            cat = new ProductCategory(CategoryManagement.categoryID, product.getCategoryName(), "");
            CategoryManagement.addCategory(cat);
        }
        cat.getProductList().insetSorted(product);

        if (Integer.parseInt(product.getProductID().replace("P","")) >= productID)
            productID = Integer.parseInt(product.getProductID().replace("P","")) + 1;
    }

    public static void updateProduct(Product product, String name, char status){
        product.setProductName(name);
        product.setStatus(status);
    }

    public static Product removeProduct(String productID, ProductCategory category){

        Product product = searchProductByID(productID, category);

        if (product != null){
            category.getProductList().delete(product);
            return product;
        }
        return null;
    }

    public static Product searchProductByID(String productID, ProductCategory category){

            ListIterator<Product> productIterator = category.getProductList().iterator();
            while (productIterator.hasNext()){
                Product curr = productIterator.next();
                if (curr.getProductID().equalsIgnoreCase(productID))
                    return curr;
            }
        return null;
    }

    public static Product searchProductByName(String productID, ProductCategory category){

        ListIterator<Product> productIterator = category.getProductList().iterator();
        while (productIterator.hasNext()){
            Product curr = productIterator.next();
            if (curr.getProductName().equalsIgnoreCase(productID))
                return curr;
        }
        return null;
    }

    public static Product findProductById(String productID){
        for (ProductCategory category : CategoryManagement.categoriesList){
            for (Product product : category.getProductList()) {
                if (product.getProductID().equalsIgnoreCase(productID))
                    return product;
                if(product.getProductID().compareTo(productID) > 0)
                    break;
            }
        }
        return null;
    }

    //Display product and sort product in ProductManagementMenu class
}
