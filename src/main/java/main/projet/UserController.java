package main.projet;

import entity.Account;

import entity.notif;
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
import services.notifService;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


public class UserController implements Initializable {

    private static UserController instance =new UserController();
    public static UserController getInstance() {
        return instance;
    }

    private int SelectedUserid;



    @FXML
    VBox UserAffichage;
    @FXML
    Pane AddUserPage,EditUserPage;

    services.notifService notifService = new notifService();
    AccountService accountService = new AccountService();


    private Account currentAccount;




    //table view admin
    @FXML
    TableView<Account> userTableView;
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





    ObservableList<Account> users;
    {
        try {
            users = accountService.afficher();
            users  =users.stream()
                    .filter(account -> account.getTitle().equals(Account.Title.user))
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


// methods

    @FXML
    public void addUserInterface(){
        //hide interfaces
        UserAffichage.setVisible(false);
        UserAffichage.setManaged(false);

        //hide interfaces
        EditUserPage .setVisible(false);
        EditUserPage .setManaged(false);

        //show addAdminPage interface

        AddUserPage.setVisible(true);
        AddUserPage.setManaged(true);
    }

    @FXML
    public void GoToUserAffichagePage(){
        //hide interfaces
        AddUserPage.setVisible(false);
        AddUserPage.setManaged(false);

        EditUserPage .setVisible(false);
        EditUserPage .setManaged(false);

        //show Adminaffichage interface



        UserAffichage.setVisible(true);
        UserAffichage.setManaged(true);
    }

    public void GoToUserEditPage(){
        //hide interfaces
        AddUserPage.setVisible(false);
        AddUserPage.setManaged(false);

        UserAffichage.setVisible(false);
        UserAffichage.setManaged(false);

        //show Adminaffichage interface


        EditUserPage .setVisible(true);
        EditUserPage .setManaged(true);

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
        try {
            currentAccount = accountService.getAccountByAccountId(Account.getCurrentid());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public Callback<TableColumn<Account, Void>, TableCell<Account, Void>> createButtonCellFactory() {
        return new Callback<TableColumn<Account, Void>, TableCell<Account, Void>>() {
            @Override
            public TableCell<Account, Void> call(final TableColumn<Account, Void> param) {
                return new TableCell<Account, Void>() {

                    final Button deleteButton = createButton("Delete");
                    final Button editButton = createButton("Edit");

                    {
                        // Set actions for the buttons
                        deleteButton.setOnAction(event -> {
                            Account user = getTableView().getItems().get(getIndex());
                            System.out.println("Delete: " + user.getId());
                            try {
                                accountService.supprimer(user.getId());
                                notif n = new notif(-1,currentAccount.getNom(),currentAccount.getNom() +" has deleted a user ","admin");
                                try {
                                    notifService.ajouter(n);
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                reload_page();
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            // Add your delete action here
                        });

                        editButton.setOnAction(event -> {
                            Account user = getTableView().getItems().get(getIndex());
                            System.out.println("Edit: " + user.getId());

                            instance.SelectedUserid= user.getId();
                            GoToUserEditPage();
                            fillUsnputs(user);

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



    @FXML
     TextField userPasswordField;

    @FXML
     TextField userFirstNameField;

    @FXML
     TextField userNameField;

    @FXML
     TextField userAgeField;

    @FXML
     TextField userMailField;


    void initAdminInputs(){
        userNameField.setText("");
        userFirstNameField.setText("");
        userAgeField.setText("");
        userMailField.setText("");
        userPasswordField.setText("");


        userNameField1.setText("");
        userFirstNameField1.setText("");
        userAgeField1.setText("");
        userMailField1.setText("");
        userPasswordField1.setText("");

    }


    @FXML
    public void handleAddUser(){

        String name = userNameField.getText();
        String prenom =  userFirstNameField.getText();
        int age =  Integer.parseInt(userAgeField.getText());
        String mail = userMailField.getText();
        String password =  userPasswordField.getText();

        Account account = new Account(-1, name, prenom, age, mail, password,Account.Title.user);
        AccountService accountService =new AccountService();

        try {
            accountService.ajouter(account);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        notif n = new notif(-1,currentAccount.getNom(),currentAccount.getNom() +" has added a user ","admin");
        try {
            notifService.ajouter(n);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        initAdminInputs();
        reload_page();
    }



    //modif user



    @FXML
    TextField userPasswordField1;

    @FXML
    TextField userFirstNameField1;

    @FXML
    TextField userNameField1;

    @FXML
    TextField userAgeField1;

    @FXML
    TextField userMailField1;


    void fillUsnputs(Account account){
        userNameField1.setText(account.getNom());
        userFirstNameField1.setText(account.getPrenom());
        userAgeField1.setText(String.valueOf(account.getAge()));
        userMailField1.setText(account.getMail());
        userPasswordField1.setText(account.getPassword());

    }


    @FXML
    public void handleEditUser(){

        String name = userNameField1.getText();
        String prenom =  userFirstNameField1.getText();
        int age =  Integer.parseInt(userAgeField1.getText());
        String mail = userMailField1.getText();
        String password =  userPasswordField1.getText();

        Account account = new Account(instance.SelectedUserid, name, prenom, age, mail, password,Account.Title.user);
        AccountService accountService =new AccountService();

        try {
            accountService.modifier(account);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        notif n = new notif(-1,currentAccount.getNom(),currentAccount.getNom() +" has edited a user ","admin");
        try {
            notifService.ajouter(n);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        initAdminInputs();
        reload_page();
    }








    @FXML
    public void reload_page(){

        {
            try {
                users = accountService.afficher();
                users  =users.stream()
                        .filter(account -> account.getTitle().equals(Account.Title.user))
                        .collect(Collectors.toCollection(FXCollections::observableArrayList));
                userTableView.setItems(null);
                userTableView.setItems(users);
                userTableView.refresh();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


        UserAffichage.setVisible(true);
        UserAffichage.setManaged(true);



        //show addAdminPage interface

        AddUserPage.setVisible(false);
        AddUserPage.setManaged(false);

    }

}