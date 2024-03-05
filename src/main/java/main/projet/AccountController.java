package main.projet;

import entity.Account;
import entity.notif;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
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
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;



public class AccountController implements Initializable {
    @FXML
    VBox AccountAffichagee,SatisticsVbox;
    @FXML
    Pane AddAdminPagee,Satistics;
    private Account currentAccount;




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
    notifService notifService = new notifService();


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
        Satistics.setVisible(false);
        Satistics.setManaged(false);
        //show addAdminPage interface

        AddAdminPagee.setVisible(true);
        AddAdminPagee.setManaged(true);


    }

    @FXML
    public void GoToAccountAffichagePagee(){
        //hide interfaces
        AddAdminPagee.setVisible(false);
        AddAdminPagee.setManaged(false);
        Satistics.setVisible(false);
        Satistics.setManaged(false);

        //show Adminaffichage interface

        AccountAffichagee.setVisible(true);
        AccountAffichagee.setManaged(true);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        // idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        mailColumn.setCellValueFactory(new PropertyValueFactory<>("mail"));
        //  passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        actionsColumn.setCellFactory(createButtonCellFactory());
        try {
            currentAccount = accountService.getAccountByAccountId(Account.getCurrentid());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


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
                                notif n = new notif(-1,currentAccount.getNom(),currentAccount.getNom() +" has deleted an admin ","admin");
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
    public void handleAddAdmin() {
        if (validateInput()) {
            String name = adminNameField.getText();
            String prenom = adminPrenomField.getText();
            int age = Integer.parseInt(adminAgeField.getText());
            String mail = adminMailField1.getText();
            String password = adminPasswordField.getText();

            notif n = new notif(-1,currentAccount.getNom(),currentAccount.getNom() +" has added a new admin ","admin");
            try {
                notifService.ajouter(n);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            Account account = new Account(-1, name, prenom, age, mail, password, Account.Title.admin);
            AccountService accountService = new AccountService();

            try {
                accountService.ajouter(account);
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Error adding admin", e.getMessage());
            }

            initAdminInputs();
            reload_page();
        }
    }

    private boolean validateInput() {
        if (adminNameField.getText().isEmpty()
                || adminPrenomField.getText().isEmpty()
                || adminAgeField.getText().isEmpty()
                || adminMailField1.getText().isEmpty()
                || adminPasswordField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "All fields are required", "Please fill in all required fields.");
            return false;
        }

        try {
            int age = Integer.parseInt(adminAgeField.getText());
            if (age < 0) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Invalid Age", "Age must be a non-negative integer.");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Invalid Age Format", "Age must be a valid integer.");
            return false;
        }

        // Add more validation checks as needed.

        return true;
    }

    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }




    private int getCurrentUserId(Account account) {
        return account.getId();
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

    }










    //search
    @FXML
    Button AdminSearchBtn;

    @FXML
    TextField minAge,maxAge,nameSearchField;



    @FXML
    void handleSearch(){
        String name ="--";

        if(nameSearchField.getText() != null && !nameSearchField.getText().isEmpty()){
            name = nameSearchField.getText();
        }

        int minage = -1;
        int maxage = -1;

        if (minAge.getText() != null && !minAge.getText().isEmpty()) {
            minage = Integer.parseInt(minAge.getText());
        }

        if (maxAge.getText() != null && !maxAge.getText().isEmpty()) {
            maxage = Integer.parseInt(maxAge.getText());
        }


        {
            try {
                admins = accountService.search(name,minage,maxage);
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


    }



    //satistic


    @FXML
    public void GoToSatistics(){
        //hide interfaces
        AddAdminPagee.setVisible(false);
        AddAdminPagee.setManaged(false);

        AccountAffichagee.setVisible(false);
        AccountAffichagee.setManaged(false);

        //show Adminaffichage interface
        SatisticsVbox.getChildren().clear();


        ObservableList<PieChart.Data> pieChartData= FXCollections.observableArrayList();

        try {
            Map<String, Integer> userTypeStatistics = accountService.getUserTypeStatistics();

            // Create PieChart data
            for (Map.Entry<String, Integer> entry : userTypeStatistics.entrySet()) {
                String userType = entry.getKey();
                int count = entry.getValue();
                pieChartData.add(new PieChart.Data(userType, count));
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately in your application
        }



            PieChart pieChart = new PieChart(pieChartData);
            pieChart.setClockwise(true);
            pieChart.setLabelLineLength(50);
            pieChart.setLabelsVisible(true);
            pieChart.setStartAngle(180);
            pieChart.setStyle("-fx-font-size: 20px; -fx-text-fill: #f16602;");


            // Create a label
            Label titleLabel = new Label("title");
            titleLabel.getStyleClass().add("label-style");
            titleLabel.getStyleClass().add("title");

            SatisticsVbox.getChildren().add(titleLabel);
            SatisticsVbox.getChildren().add(pieChart);

    }




}


