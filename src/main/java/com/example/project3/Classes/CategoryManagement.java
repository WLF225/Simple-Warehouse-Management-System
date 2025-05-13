package com.example.project3.Classes;

import com.example.project3.DataStructures.DLinkedList;
import java.util.ListIterator;

public class CategoryManagement {

    public static DLinkedList<ProductCategory> categoriesList = new DLinkedList<>();
    public static int categoryID = 1;

    public static void addCategory(ProductCategory category) {
        ListIterator<ProductCategory> iterator = categoriesList.iterator();
        while (iterator.hasNext()) {
            ProductCategory curr = iterator.next();
            if (curr.getCategoryID() == category.getCategoryID())
                throw new AlertException("Category ID already exists.");
            if (curr.getCategoryName().equalsIgnoreCase(category.getCategoryName()))
                throw new AlertException("Category name already exist");
        }
        categoriesList.insetSorted(category);
        if (category.getCategoryID()  >= categoryID)
            categoryID = category.getCategoryID() + 1;
    }

    public static void updateCategory(ProductCategory category, String description) {
        category.setDescription(description);
    }

    public static void deleteCategory(ProductCategory category, boolean deleteChoice) {

        //To not delete it while the category has products
        if (deleteChoice) {
            categoriesList.delete(category);
        }
        //To delete category with it items
        else {
            if (!category.getProductList().isEmpty())
                throw new AlertException("Category has products assigned to it.");
            else
                categoriesList.delete(category);
        }
    }

    public static ProductCategory searchCategory(String categoryName) {
        ListIterator<ProductCategory> iterator = categoriesList.iterator();
        while (iterator.hasNext()) {
            ProductCategory category = iterator.next();
            if (category.getCategoryName().equalsIgnoreCase(categoryName))
                return category;
            if (category.getCategoryName().compareTo(categoryName) > 0)
                break;
        }
        return null;
    }

    //note: list categories in the CategoryManagementMenu class


}
