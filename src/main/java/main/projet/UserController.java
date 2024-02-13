package main.projet;

import entity.User;

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



public class UserController implements Initializable {
    @FXML
    VBox UserAffichage;
    @FXML
    Pane AddUserPage;





    //table view admin
    @FXML
    TableView<User> userTableView;
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
    TableColumn<User,Void> actionsColumn;





    ObservableList<User> users= FXCollections.observableArrayList(
            new User(1, "Smith", "John", 35, "john.smith@email.com", 123456),
            new User(2, "Doe", "Jane", 28, "jane.doe@email.com", 654321),
            new User(3, "Williams", "Tom", 42, "tom.williams@email.com", 987654),
            new User(4, "Johnson", "Alice", 30, "alice.johnson@email.com", 111222),
            new User(5, "Brown", "Chris", 50, "chris.brown@email.com", 333444)
    );





// methods

    @FXML
    public void addUserInterface(){
        //hide interfaces
        UserAffichage.setVisible(false);
        UserAffichage.setManaged(false);



        //show addAdminPage interface

        AddUserPage.setVisible(true);
        AddUserPage.setManaged(true);
    }

    @FXML
    public void GoToUserAffichagePage(){
        //hide interfaces
        AddUserPage.setVisible(false);
        AddUserPage.setManaged(false);


        //show Adminaffichage interface

        UserAffichage.setVisible(true);
        UserAffichage.setManaged(true);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //initialisation de tableview user
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        mailColumn.setCellValueFactory(new PropertyValueFactory<>("mail"));
        actionsColumn.setCellFactory(createButtonCellFactory());
        userTableView.setItems(users);

    }
    public Callback<TableColumn<User, Void>, TableCell<User, Void>> createButtonCellFactory() {
        return new Callback<TableColumn<User, Void>, TableCell<User, Void>>() {
            @Override
            public TableCell<User, Void> call(final TableColumn<User, Void> param) {
                return new TableCell<User, Void>() {

                    final Button deleteButton = createButton("Delete");
                    final Button editButton = createButton("Edit");

                    {
                        // Set actions for the buttons
                        deleteButton.setOnAction(event -> {
                            User user = getTableView().getItems().get(getIndex());
                            System.out.println("Delete: " + user.getId());
                            // Add your delete action here
                        });

                        editButton.setOnAction(event -> {
                            User user = getTableView().getItems().get(getIndex());
                            System.out.println("Edit: " + user.getId());
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