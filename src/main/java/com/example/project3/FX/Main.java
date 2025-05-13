package com.example.project3.FX;

import com.example.project3.Classes.CategoryManagement;
import com.example.project3.Classes.Product;
import com.example.project3.Classes.ProductCategory;
import com.example.project3.Classes.ProductManagement;
import com.example.project3.DataStructures.Stack;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.GregorianCalendar;

public class Main extends Application {

    public static Stage stage;

    @Override
    public void start(Stage stage) {
        this.stage = stage;

        ProductManagement.addProduct(new Product("1","test","Category 1",'I'));
        ProductManagement.addProduct(new Product("2","test2","Category 1",'A'));

        CategoryManagement.addCategory(new ProductCategory(3,"Category 2","Description 2"));
        CategoryManagement.addCategory(new ProductCategory(4,"Category 3","Description 3"));
        CategoryManagement.addCategory(new ProductCategory(5,"Category 4","Description 4"));

        Scene scene = new Scene(new Pane());
        MainMenu mainMenu = new MainMenu(scene);
        scene.setRoot(mainMenu);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {

        launch();
    }


}