package com.example.project3.FX;

import com.example.project3.Classes.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ListIterator;

public class ProductManagementMenu extends BorderPane {



    public ProductManagementMenu(Scene scene,ProductCategory productCategory) {

        Main.stage.setTitle("Product Management for "+productCategory.getCategoryName());

        ComboBox<String> sortCB = new ComboBox<>();
        sortCB.getItems().addAll("Sort by name","Sort by status");
        sortCB.setValue("Sort by name");

        ComboBox<String> filterCB = new ComboBox<>();
        filterCB.getItems().addAll("Show all items","Show Active items","Show Inactive items");
        filterCB.setValue("Show all items");


        TextField searchTextField = new TextField();

        Button[] buttons = {new Button("Search for Product by the name"),new Button("Search for Product by the ID"),
                new Button("Add Product"), new Button("Edit Product"), new Button("Delete Product"),
                new Button("Back"),new Button("Previous category"),new Button("Next category")};

        HBox hBox = new HBox(40,searchTextField,buttons[0],buttons[1], sortCB,filterCB);
        hBox.setPadding(new javafx.geometry.Insets(0,0,40,0));


        setPadding(new javafx.geometry.Insets(100));

        setTop(hBox);

        ObservableList<Product> observableList = FXCollections.observableArrayList();

        productCategory.getProductList().addToObservableList(observableList);

        TableView<Product> tableView = new TableView<>(observableList);

        TableColumn<Product, String> iDColumn = new TableColumn<>("Product ID");
        iDColumn.setCellValueFactory(new PropertyValueFactory<>("productID")); // Maps to the categoryID field
        iDColumn.setMinWidth(300);

        TableColumn<Product, String> nameColumn = new TableColumn<>("Product Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("productName")); // Maps to the categoryName field
        nameColumn.setMinWidth(300);

        TableColumn<Product, String> categoryNameTC = new TableColumn<>("Category Name");
        categoryNameTC.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        categoryNameTC.setMinWidth(300);

        TableColumn<Product, Character> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusColumn.setMinWidth(600);

        tableView.getColumns().addAll(iDColumn, nameColumn, categoryNameTC, statusColumn);

        setCenter(tableView);

        sortCB.setOnAction(e -> {
            if (sortCB.getValue().equals("Sort by name")){
                tableView.getSortOrder().clear();
                nameColumn.setSortType(TableColumn.SortType.ASCENDING); // Use DESCENDING for reverse
                tableView.getSortOrder().add(nameColumn); // Add column to sort order
                tableView.sort();
            }else {
                tableView.getSortOrder().clear();
                statusColumn.setSortType(TableColumn.SortType.ASCENDING); // Use DESCENDING for reverse
                tableView.getSortOrder().add(statusColumn); // Add column to sort order
                tableView.sort();
            }

        });

        filterCB.setOnAction(e -> {
            if (filterCB.getValue().equals("Show all items")){
                tableView.setItems(observableList);
            }else if (filterCB.getValue().equals("Show Active items")){
                ObservableList<Product> activeList = FXCollections.observableArrayList(observableList);
                activeList.removeIf(product -> product.getStatus() == 'I');
                tableView.setItems(activeList);
            }else {
                ObservableList<Product> inactiveList = FXCollections.observableArrayList(observableList);
                inactiveList.removeIf(product -> product.getStatus() == 'A');
                tableView.setItems(inactiveList);
            }
        });

        buttons[0].setOnAction(e -> {
            if (searchTextField.getText().isEmpty()) {
                tableView.setItems(observableList);
                return;
            }
            Product product = ProductManagement.searchProductByName(searchTextField.getText(),productCategory);
            if (product == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Search Failed");
                alert.setHeaderText(null);
                alert.setContentText("No product found with this name in this category");
                alert.showAndWait();
                return;
            }
            tableView.setItems(FXCollections.observableArrayList(product));
        });

        buttons[1].setOnAction(e -> {
            if (searchTextField.getText().isEmpty()) {
                tableView.setItems(observableList);
                return;
            }
            Product product = ProductManagement.searchProductByID(searchTextField.getText(),productCategory);
            if (product == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Search Failed");
                alert.setHeaderText(null);
                alert.setContentText("No product found with this ID in this category");
                alert.showAndWait();
                return;
            }
            tableView.setItems(FXCollections.observableArrayList(product));
        });

        buttons[2].setOnAction(e -> scene.setRoot(new AddProductMenu(scene,productCategory)));

        buttons[3].setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if (tableView.getSelectionModel().getSelectedItem() == null) {
                alert.setTitle("Select Category");
                alert.setHeaderText(null);
                alert.setContentText("Please select a product to edit");
                alert.showAndWait();
                return;
            }
            scene.setRoot(new EditProductMenu(scene,tableView.getSelectionModel().getSelectedItem(),productCategory));
        });

        //To delete the selected product
        buttons[4].setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if (tableView.getSelectionModel().getSelectedItem() == null) {
                alert.setTitle("Select Category");
                alert.setHeaderText(null);
                alert.setContentText("Please select a product to delete");
                alert.showAndWait();
                return;
            }
            ProductManagement.removeProduct(tableView.getSelectionModel().getSelectedItem().getProductID(),productCategory);
            observableList.remove(tableView.getSelectionModel().getSelectedItem());
            tableView.setItems(observableList);
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Successful");
            alert.setHeaderText(null);
            alert.setContentText("The category has been deleted successfully");
            alert.showAndWait();
        });

        buttons[5].setOnAction(e -> scene.setRoot(new CategoryManagementMenu(scene)));

        buttons[6].setOnAction(e -> {
            ListIterator<ProductCategory> curr = CategoryManagement.categoriesList.iterator();
            while (curr.hasNext()){
                if (curr.next() == productCategory) {
                    scene.setRoot(new ProductManagementMenu(scene, curr.previous()));
                    return;
                }
            }
        });

        buttons[7].setOnAction(e -> {
            ListIterator<ProductCategory> curr = CategoryManagement.categoriesList.iterator();
            while (curr.hasNext()){
                if (curr.next() == productCategory) {
                    scene.setRoot(new ProductManagementMenu(scene, curr.next()));
                    return;
                }
            }
        });

        ListIterator<ProductCategory> curr = CategoryManagement.categoriesList.iterator();
        boolean isFirst = true;
        while (curr.hasNext()){
            ProductCategory currC = curr.next();
            if (currC == productCategory && !curr.hasNext()) {
                buttons[7].setDisable(true);
            }else if (currC == productCategory && isFirst) {
                buttons[6].setDisable(true);
            }
            isFirst = false;
        }

        HBox nextPrevButtonsHB = new HBox(40,buttons[6],buttons[7]);
        nextPrevButtonsHB.setAlignment(Pos.CENTER);
        nextPrevButtonsHB.setPadding(new javafx.geometry.Insets(40,0,0,0));

        HBox buttonsHB = new HBox(40,buttons[2],buttons[3],buttons[4],buttons[5]);
        VBox buttonsVBox = new VBox(40,nextPrevButtonsHB,buttonsHB);

        buttonsHB.setPadding(new javafx.geometry.Insets(40,0,0,0));
        buttonsHB.setAlignment(Pos.CENTER);


        setBottom(buttonsVBox);
    }

}
