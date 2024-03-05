package main.projet;

import entity.Account;

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
import services.AccountService;


import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


public class AccountController implements Initializable {
    @FXML
    VBox AccountAffichagee;
    @FXML
    Pane AddAdminPagee;





    //table view admin
    @FXML
    TableView<Account> accountTableView;
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
    TableColumn<Account,Void> actionsColumn;

    AccountService accountService = new AccountService();


        ObservableList<Account> admins;
    {
        try {
            admins = accountService.afficher();
            admins  =admins.stream()
                    .filter(account -> account.getTitle().equals(Account.Title.admin))
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




// methods





    @FXML
    public void addAccountInterfacee(){
        //hide interfaces
        AccountAffichagee.setVisible(false);
        AccountAffichagee.setManaged(false);



        //show addAdminPage interface

        AddAdminPagee.setVisible(true);
        AddAdminPagee.setManaged(true);
    }

    @FXML
    public void GoToAccountAffichagePagee(){
        //hide interfaces
        AddAdminPagee.setVisible(false);
        AddAdminPagee.setManaged(false);


        //show Adminaffichage interface

        AccountAffichagee.setVisible(true);
        AccountAffichagee.setManaged(true);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        mailColumn.setCellValueFactory(new PropertyValueFactory<>("mail"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        actionsColumn.setCellFactory(createButtonCellFactory());


        {
            try {
                admins = accountService.afficher();
                admins  =admins.stream()
                        .filter(account -> account.getTitle().equals(Account.Title.admin))
                        .collect(Collectors.toCollection(FXCollections::observableArrayList));

                accountTableView.setItems(admins);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }
    public Callback<TableColumn<Account, Void>, TableCell<Account, Void>> createButtonCellFactory() {
        return new Callback<TableColumn<Account, Void>, TableCell<Account, Void>>() {
            @Override
            public TableCell<Account, Void> call(final TableColumn<Account, Void> param) {
                return new TableCell<Account, Void>() {

                    final Button deleteButton = createButton("Delete");

                    {
                        // Set actions for the buttons
                        deleteButton.setOnAction(event -> {
                            Account account = getTableView().getItems().get(getIndex());
                            System.out.println("Delete: " + account.getId());
                            try {
                                accountService.supprimer(account.getId());
                                reload_page();

                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            // Add your delete action here
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
                        buttonPane.getChildren().addAll(deleteButton);

                        return buttonPane;
                    }
                };
            }
        };
    }

    @FXML
    TextField adminNameField,adminPrenomField,adminAgeField,adminMailField1,adminPasswordField;


    void initAdminInputs(){
        adminNameField.setText("");
        adminPrenomField.setText("");
        adminAgeField.setText("");
        adminMailField1.setText("");
        adminPasswordField.setText("");

    }


    @FXML
    public void handleAddAdmin(){

        String name = adminNameField.getText();
        String prenom =  adminPrenomField.getText();
        int age =  Integer.parseInt(adminAgeField.getText());
        String mail = adminMailField1.getText();
        String password =  adminPasswordField.getText();

        Account account = new Account(-1, name, prenom, age, mail, password,Account.Title.admin);
        AccountService accountService =new AccountService();

        try {
            accountService.ajouter(account);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        initAdminInputs();
        reload_page();
    }








    @FXML
    public void reload_page(){

        {
            try {
                admins = accountService.afficher();
                admins  =admins.stream()
                        .filter(account -> account.getTitle().equals(Account.Title.admin))
                        .collect(Collectors.toCollection(FXCollections::observableArrayList));
                accountTableView.setItems(null);
                accountTableView.setItems(admins);
                accountTableView.refresh();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


        AccountAffichagee.setVisible(true);
        AccountAffichagee.setManaged(true);



        //show addAdminPage interface

        AddAdminPagee.setVisible(false);
        AddAdminPagee.setManaged(false);
        AddAdminPagee.setManaged(false);

    }
}