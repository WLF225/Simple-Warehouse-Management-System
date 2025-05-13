package com.example.project3.FX;

import com.example.project3.Classes.AlertException;
import com.example.project3.Classes.CategoryManagement;
import com.example.project3.Classes.ProductCategory;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class CategoryManagementMenu extends BorderPane {

    public CategoryManagementMenu(Scene scene) {

        Main.stage.setTitle("Category Management");

        TextField searchTextField = new TextField();
        searchTextField.setPromptText("Category name");

        Button[] buttons = {new Button("Search for Category"), new Button("Add Category"),
                new Button("Edit Category"), new Button("Delete Category"),
                new Button("Product Management for this category"), new Button("Back")};

        HBox hBox = new HBox(40,searchTextField,buttons[0]);
        hBox.setPadding(new javafx.geometry.Insets(0,0,40,0));


        setPadding(new javafx.geometry.Insets(100));

        setTop(hBox);

        ObservableList<ProductCategory> observableList = FXCollections.observableArrayList();

        CategoryManagement.categoriesList.addToObservableList(observableList);

        TableView<ProductCategory> tableView = new TableView<>(observableList);

        TableColumn<ProductCategory, Integer> categoryIDColumn = new TableColumn<>("Category ID");
        categoryIDColumn.setCellValueFactory(new PropertyValueFactory<>("categoryID")); // Maps to the categoryID field
        categoryIDColumn.setMinWidth(300);

        TableColumn<ProductCategory, String> categoryNameColumn = new TableColumn<>("Category Name");
        categoryNameColumn.setCellValueFactory(new PropertyValueFactory<>("categoryName")); // Maps to the categoryName field
        categoryNameColumn.setMinWidth(300);

        TableColumn<ProductCategory, Integer> productCountTC = new TableColumn<>("Product count");
        productCountTC.setCellValueFactory(e->new SimpleIntegerProperty(e.getValue().productCount()).asObject());
        productCountTC.setMinWidth(300);

        TableColumn<ProductCategory, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionColumn.setMinWidth(600);

        tableView.getColumns().addAll(categoryIDColumn, categoryNameColumn, productCountTC,descriptionColumn);

        setCenter(tableView);

        buttons[0].setOnAction(e -> {
            if (searchTextField.getText().isEmpty()) {
                tableView.setItems(observableList);
                return;
            }
            ProductCategory category = CategoryManagement.searchCategory(searchTextField.getText());
            if (category == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Search Failed");
                alert.setHeaderText(null);
                alert.setContentText("No category found with this name");
                alert.showAndWait();
                return;
            }
            tableView.setItems(FXCollections.observableArrayList(category));
        });

        buttons[1].setOnAction(e -> scene.setRoot(new AddCategoryMenu(scene)));

        buttons[2].setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if (tableView.getSelectionModel().getSelectedItem() == null) {
                alert.setTitle("Select Category");
                alert.setHeaderText(null);
                alert.setContentText("Please select a category to edit");
                alert.showAndWait();
                return;
            }
            scene.setRoot(new EditCategoryMenu(scene,tableView.getSelectionModel().getSelectedItem()));
        });


        buttons[3].setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if (tableView.getSelectionModel().getSelectedItem() == null) {
                alert.setTitle("Select Category");
                alert.setHeaderText(null);
                alert.setContentText("Please select a category to delete");
                alert.showAndWait();
                return;
            }
            alert.setTitle("Delete Type");
            alert.setHeaderText(null);
            ButtonType safeDelete = new ButtonType("Safe Delete");
            ButtonType forceDelete = new ButtonType("Force Delete");
            alert.getButtonTypes().setAll(safeDelete,forceDelete, ButtonType.CANCEL);
            alert.setContentText("What the delete type you want ?");
            ButtonType result = alert.showAndWait().get();
            if (result == safeDelete) {
                try {
                    CategoryManagement.deleteCategory(tableView.getSelectionModel().getSelectedItem(),false);
                    observableList.remove(tableView.getSelectionModel().getSelectedItem());
                    tableView.setItems(observableList);
                }catch (AlertException e1){
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Delete Failed");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText(e1.getMessage());
                    errorAlert.showAndWait();
                    return;
                }

            }else if (result == forceDelete){
                CategoryManagement.deleteCategory(tableView.getSelectionModel().getSelectedItem(),true);
                observableList.remove(tableView.getSelectionModel().getSelectedItem());
                tableView.setItems(observableList);
            }else if (result == ButtonType.CANCEL){
                return;
            }
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Successful");
            alert.setHeaderText(null);
            alert.setContentText("The category has been deleted successfully");
            alert.showAndWait();

        });

        buttons[4].setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if (tableView.getSelectionModel().getSelectedItem() == null) {
                alert.setTitle("Select Category");
                alert.setHeaderText(null);
                alert.setContentText("Please select a category to manage it products");
                alert.showAndWait();
                return;
            }
            scene.setRoot(new ProductManagementMenu(scene,tableView.getSelectionModel().getSelectedItem()));

        });

        buttons[5].setOnAction(e -> scene.setRoot(new MainMenu(scene)));

        HBox buttonsHB = new HBox(40,buttons[1],buttons[2],buttons[3],buttons[4],buttons[5]);
        buttonsHB.setPadding(new javafx.geometry.Insets(40,0,0,0));
        buttonsHB.setAlignment(Pos.CENTER);


        setBottom(buttonsHB);


    }
}

