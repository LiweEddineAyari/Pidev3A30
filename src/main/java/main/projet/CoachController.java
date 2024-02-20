package main.projet;

import entity.Account;
import entity.Exercice;
import entity.Planning;
import entity.Product;
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

public class CoachController implements Initializable {

    private static CoachController instance =new CoachController();
    public static CoachController getInstance() {
        return instance;
    }

    private int SelectedCoachid;

     //coach interfaces
    @FXML
      VBox Coachaffichage,Planningaffichage;
    @FXML
      Pane AddCoachtPage,AddPlanningtPage,EditUserPage;


    //table view coach
    @FXML
    TableView<Account> coachTableView;
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

    //table view planning
    @FXML
    TableView<Planning> planningTableView;
    @FXML
    TableColumn<?, ?> idplaningColumn;
    @FXML
    TableColumn<?, ?> idcoachColumn;
    @FXML
    TableColumn<?, ?> dateColumn;
    @FXML
    TableColumn<?, ?> heure_debutColumn;
    @FXML
    TableColumn<?, ?> heure_finColumn;
    @FXML
    TableColumn<Planning,Void> actionsColumnplanning;


    AccountService accountService =new AccountService();
    ObservableList<Account> coaches;
    {
        try {
            coaches = accountService.afficher();
            coaches  =coaches.stream()
                    .filter(account -> account.getTitle().equals(Account.Title.coach))
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
            coaches  =coaches.stream()
                    .filter(account -> account.getTitle().equals(Account.Title.coach))
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    //coach navigation methods
    @FXML
    public void addCoachInterface(){
        //hide interfaces
        Coachaffichage.setVisible(false);
        Coachaffichage.setManaged(false);
        AddPlanningtPage.setVisible(false);
        AddPlanningtPage.setManaged(false);
        EditUserPage.setVisible(false);
        EditUserPage.setManaged(false);

        //show addCoachPage interface

        AddCoachtPage.setVisible(true);
        AddCoachtPage.setManaged(true);
    }

    @FXML
    public void GoToCoachAffichagePage(){
        //hide interfaces
        AddCoachtPage.setVisible(false);
        AddCoachtPage.setManaged(false);
        AddPlanningtPage.setVisible(false);
        AddPlanningtPage.setManaged(false);
        EditUserPage.setVisible(false);
        EditUserPage.setManaged(false);

        //show Coachaffichage interface

        Coachaffichage.setVisible(true);
        AddCoachtPage.setManaged(true);
    }



//planing navigation
    @FXML
    public  void GoToPlanning(){
        //hide interfaces
        Coachaffichage.setVisible(false);
        Coachaffichage.setManaged(false);
        AddCoachtPage.setVisible(false);
        AddCoachtPage.setManaged(false);
        AddPlanningtPage.setVisible(false);
        AddPlanningtPage.setManaged(false);
        EditUserPage.setVisible(false);
        EditUserPage.setManaged(false);
        //show addCoachPage interface

        Planningaffichage.setVisible(true);
        Planningaffichage.setManaged(true);
    }


@FXML
public void addPlanningInterface(){
    Coachaffichage.setVisible(false);
    Coachaffichage.setManaged(false);
    AddCoachtPage.setVisible(false);
    AddCoachtPage.setManaged(false);
    Planningaffichage.setVisible(false);
    Planningaffichage.setManaged(false);
    EditUserPage.setVisible(false);
    EditUserPage.setManaged(false);
    //show addCoachPage interface
    AddPlanningtPage.setVisible(true);
    AddPlanningtPage.setManaged(true);

}



void GoToEditCoach(){

    Coachaffichage.setVisible(false);
    Coachaffichage.setManaged(false);
    AddCoachtPage.setVisible(false);
    AddCoachtPage.setManaged(false);
    Planningaffichage.setVisible(false);
    Planningaffichage.setManaged(false);
    AddPlanningtPage.setVisible(false);
    AddPlanningtPage.setManaged(false);
    //show edit coach interface


    EditUserPage.setVisible(true);
    EditUserPage.setManaged(true);
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
        coachTableView.setItems(coaches);

        //initialisation de tableview planning
       /* idplaningColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idcoachColumn.setCellValueFactory(new PropertyValueFactory<>("id_coach"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        heure_debutColumn.setCellValueFactory(new PropertyValueFactory<>("heure_debut"));
        heure_finColumn.setCellValueFactory(new PropertyValueFactory<>("heure_fin"));
        actionsColumnplanning.setCellFactory(createButtonCellFactoryPlanning());
        planningTableView.setItems(plannings);
        */



    }

    //action column for product table
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
                            Account account = getTableView().getItems().get(getIndex());
                            System.out.println("Delete: " + account.getId());
                            try {
                                accountService.supprimer(account.getId());
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            reload_page();
                            // Add your delete action here
                        });

                        editButton.setOnAction(event -> {
                            Account coach = getTableView().getItems().get(getIndex());
                            System.out.println("Edit: " + coach.getId());
                            instance.SelectedCoachid= coach.getId();
                            GoToEditCoach();
                            fillUsnputs(coach);
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
    TextField coachNameField,coachFirstNameField,coachAgeField,coachMailField,coachPasswordField;
    @FXML
    TextField coachNameField1,coachFirstNameField1,coachAgeField1,coachMailField1,coachPasswordField1;

    void fillUsnputs(Account account){
        coachNameField1.setText(account.getNom());
        coachFirstNameField1.setText(account.getPrenom());
        coachAgeField1.setText(String.valueOf(account.getAge()));
        coachMailField1.setText(account.getMail());
        coachPasswordField1.setText(account.getPassword());

    }
    @FXML
    public void handleEditCoach(){

        String name = coachNameField1.getText();
        String prenom =  coachFirstNameField1.getText();
        int age =  Integer.parseInt(coachAgeField1.getText());
        String mail = coachMailField1.getText();
        String password =  coachPasswordField1.getText();

        Account account = new Account(instance.SelectedCoachid, name, prenom, age, mail, password,Account.Title.coach);
        AccountService accountService =new AccountService();

        try {
            accountService.modifier(account);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        initAdminInputs();
        reload_page();
    }



    @FXML
    public void handleAddCoach(){

        String name = coachNameField.getText();
        String prenom =  coachFirstNameField.getText();
        int age =  Integer.parseInt(coachAgeField.getText());
        String mail = coachMailField.getText();
        String password =  coachPasswordField.getText();

        Account account = new Account(-1, name, prenom, age, mail, password,Account.Title.coach);
        AccountService accountService =new AccountService();

        try {
            accountService.ajouter(account);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        initAdminInputs();
        reload_page();
    }




    //action column for planing table
     Callback<TableColumn<Planning, Void>, TableCell<Planning, Void>> createButtonCellFactoryPlanning() {
        return new Callback<TableColumn<Planning, Void>, TableCell<Planning, Void>>() {
            @Override
            public TableCell<Planning, Void> call(final TableColumn<Planning, Void> param) {
                return new TableCell<Planning, Void>() {

                    final Button deleteButton = createButton("Delete");
                    final Button editButton = createButton("Edit");

                    {
                        // Set actions for the buttons
                        deleteButton.setOnAction(event -> {
                            Planning planning = getTableView().getItems().get(getIndex());
                            System.out.println("Delete: " + planning.getId());
                            // Add your delete action here
                        });

                        editButton.setOnAction(event -> {
                            Planning planning = getTableView().getItems().get(getIndex());
                            System.out.println("Edit: " + planning.getId());
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
    public void reload_page(){

        {
            try {
                coaches = accountService.afficher();
                coaches  =coaches.stream()
                        .filter(account -> account.getTitle().equals(Account.Title.coach))
                        .collect(Collectors.toCollection(FXCollections::observableArrayList));
                coachTableView.setItems(null);
                coachTableView.setItems(coaches);
                coachTableView.refresh();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


        AddCoachtPage.setVisible(false);
        AddCoachtPage.setManaged(false);
        AddPlanningtPage.setVisible(false);
        AddPlanningtPage.setManaged(false);

        //show Coachaffichage interface

        Coachaffichage.setVisible(true);
        AddCoachtPage.setManaged(true);

    }








    void initAdminInputs(){
        coachNameField.setText("");
        coachFirstNameField.setText("");
        coachAgeField.setText("");
        coachMailField.setText("");
        coachPasswordField.setText("");


       /* userNameField1.setText("");
        userFirstNameField1.setText("");
        userAgeField1.setText("");
        userMailField1.setText("");
        userPasswordField1.setText("");*/

    }







}





