package main.projet;

import entity.Admin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;



public class AdminController implements Initializable {
    @FXML
    VBox AdminAffichage;
    @FXML
    Pane AddbAdminPage;





    //table view admin
    @FXML
    TableView<Admin> adminTableView;
    @FXML
    TableColumn<?, ?> idColumn;
    @FXML
    TableColumn<?, ?> nomColumn;
    @FXML
    TableColumn<?, ?> prenomColumn;
    @FXML
    TableColumn<?, ?> ageColumn;
    @FXML
    TableColumn<?, ?> mailColumn;
    @FXML
    TableColumn<?, ?> passwordColumn;
    @FXML
    TableColumn<Admin,Void> actionsColumn;





    ObservableList<Admin> admins= FXCollections.observableArrayList(
            new Admin(1, "Smith", "John", 35, "john.smith@email.com", 123456),
            new Admin(2, "Doe", "Jane", 28, "jane.doe@email.com", 654321),
            new Admin(3, "Williams", "Tom", 42, "tom.williams@email.com", 987654),
            new Admin(4, "Johnson", "Alice", 30, "alice.johnson@email.com", 111222),
            new Admin(5, "Brown", "Chris", 50, "chris.brown@email.com", 333444)
    );





// methods

    @FXML
    public void addAdminInterface(){
        //hide interfaces
        AdminAffichage.setVisible(false);
        AdminAffichage.setManaged(false);



        //show addAdminPage interface

        AddAdminPage.setVisible(true);
        AddAdminPage.setManaged(true);
    }

    @FXML
    public void GoToAdminAffichagePage(){
        //hide interfaces
        AddAdminPage.setVisible(false);
        AddAdminPage.setManaged(false);


        //show Adminaffichage interface

        AdminAffichage.setVisible(true);
        AdminAffichage.setManaged(true);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //initialisation de tableview coach
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        mailColumn.setCellValueFactory(new PropertyValueFactory<>("mail"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        actionsColumn.setCellFactory(createButtonCellFactory());
        adminTableView.setItems(admins);

    }
    public Callback<TableColumn<Admin, Void>, TableCell<Admin, Void>> createButtonCellFactory() {
        return new Callback<TableColumn<Admin, Void>, TableCell<Admin, Void>>() {
            @Override
            public TableCell<Admin, Void> call(final TableColumn<Admin, Void> param) {
                return new TableCell<Admin, Void>() {

                    final Button deleteButton = createButton("Delete");
                    final Button editButton = createButton("Edit");

                    {
                        // Set actions for the buttons
                        deleteButton.setOnAction(event -> {
                            Admin admin = getTableView().getItems().get(getIndex());
                            System.out.println("Delete: " + admin.getId());
                            // Add your delete action here
                        });

                        editButton.setOnAction(event -> {
                            Admin admin = getTableView().getItems().get(getIndex());
                            System.out.println("Edit: " + admin.getId());
                            // Add your edit action here
                        });

                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null);
                        } else {
                            // Set buttons as graphic in the cell
                            setGraphic(createButtonPane());
                        }
                    }
                    private Button createButton(String buttonText) {
                        Button button = new Button(buttonText);
                        button.setMinSize(60, 20);
                        return button;
                    }
                    private HBox createButtonPane() {
                        HBox buttonPane = new HBox(5); // spacing between buttons
                        buttonPane.getChildren().addAll(deleteButton, editButton);
                        return buttonPane;
                    }
                };
            }
        };
    }



}