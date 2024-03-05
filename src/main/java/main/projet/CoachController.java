package main.projet;

import entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import services.notifService;
import services.*;
import utils.BadWords;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CoachController implements Initializable {

    services.notifService notifService = new notifService();

    private static CoachController instance =new CoachController();
    public static CoachController getInstance() {
        return instance;
    }

    private int SelectedCoachid;

    ObservableList<ChatConversation> chatConversationsList;

    private String imageURL=null;
    String destinationFolderPath = "src/main/java/uploads/chatImages/"; // Adjust the path accordingly

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
    TableColumn<?,?>titreColumn;
    @FXML
    TableColumn<?, ?> dateDColumn;
    @FXML
    TableColumn<?, ?> dateFColumn;
    @FXML
    TableColumn<?, ?> heure_debutColumn;
    @FXML
    TableColumn<?, ?> heure_finColumn;
    @FXML
    TableColumn<Planning,Void> actionsColumnplanning;


    AccountService accountService =new AccountService();
    categoryService categoryService = new categoryService();

    @FXML
    ComboBox coachComboBox,categoryComboBox;






    private Account currentAccount;
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

    private PlanningService planningService =new PlanningService();


    //afficher plannings f table view
    ObservableList<Planning> plannings;

    {
        try {
            plannings = planningService.afficher();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    void refreshPlanningPage(){
        {
            try {
                plannings = planningService.afficher();
                planningTableView.setItems(null);
                planningTableView.setItems(plannings);
                planningTableView.refresh();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        GoToPlanning();
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
        EditPlanningBtn.setVisible(false);
        AddPlanningBtn.setVisible(true);
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
        idplaningColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idcoachColumn.setCellValueFactory(new PropertyValueFactory<>("id_coach"));
        titreColumn.setCellValueFactory(new PropertyValueFactory<>("titre"));
        dateDColumn.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
        dateFColumn.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
        heure_debutColumn.setCellValueFactory(new PropertyValueFactory<>("heure_debut"));
        heure_finColumn.setCellValueFactory(new PropertyValueFactory<>("heure_fin"));

        actionsColumnplanning.setCellFactory(createButtonCellFactoryPlanning());

        planningTableView.setItems(plannings);

        try {
            currentAccount = accountService.getAccountByAccountId(Account.getCurrentid());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        try {
            // Get user names and set as ComboBox items
            ObservableList<String> userNames = accountService.afficher().stream()
                    .filter(account -> account.getTitle().equals(Account.Title.coach))
                    .map(Account::getNom)  // Assuming there is a getName() method in the Account class
                    .collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList));

            ObservableList<String> titresnames = categoryService.GetCategoriesNames() ;

            coachComboBox.setItems(userNames);
            categoryComboBox.setItems(titresnames);
        } catch (SQLException e) {
            // Handle exception appropriately based on your application's error handling strategy
            e.printStackTrace();
        }

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
                            notif n = new notif(-1,currentAccount.getNom(),currentAccount.getNom() +" has deleted a coach ","admin");
                            try {
                                notifService.ajouter(n);
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
        notif n = new notif(-1,currentAccount.getNom(),currentAccount.getNom() +" has edited a coach ","admin");
        try {
            notifService.ajouter(n);
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
        notif n = new notif(-1,currentAccount.getNom(),currentAccount.getNom() +" has added a coach ","admin");
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




    //Crud planning


    @FXML
    TextField heureDField,heureFField;
    @FXML
    TextArea descField;
    @FXML
    DatePicker dateDField,dateFField;


    @FXML
    Button AddPlanningBtn,EditPlanningBtn;








    private Planning selectedPlanning;


    @FXML
    private void handleAddPlanning() {

        if (controleSaisi()) {
            try {
                // Get values from UI components
                int id_coach = getCoachIdByName(coachComboBox.getValue().toString());
                String date_debut = dateDField.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String date_fin = dateFField.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String heure_debut = heureDField.getText();
                String heure_fin = heureFField.getText();
                String titre = categoryComboBox.getValue().toString();
                String description = descField.getText();

                // Create a new Planning object
                Planning newPlanning = new Planning(0, id_coach, date_debut, date_fin, heure_debut, heure_fin, titre, description);

                // Add the new planning entry to the database
                int newIdPlanning= planningService.ajouter(newPlanning);

                // notif for adding planning  *****************************************

                notif n = new notif(-1,currentAccount.getNom(),currentAccount.getNom() +" has added a new planning ","admin");
                try {
                    notifService.ajouter(n);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }


                //planning ytbath f msg lel coach
                int idadmin = AppController.getInstance().account.getId();
                //1- naml chat session admin-coach
                instance.chatSession.setId_user(idadmin);
                instance.chatSession.setId_user2(id_coach);
                chatSessionService.ajouter(instance.chatSession);

                int  idSession= chatSessionService.getChatSession(instance.chatSession);

                instance.chatSession.setId(idSession);
                //1- nabath planning f chat li feha chat session admin-coach li deja amaltha taw


                String message = "new planning \n" +
                        "titre : "+newPlanning.getTitre() +"\n" +
                        "date debut : "+newPlanning.getHeure_debut() +"\n" +
                        "date fin : "+newPlanning.getDate_fin() +"\n" +
                        "heure debut  : "+newPlanning.getHeure_debut() +"\n" +
                        "heure fin : "+newPlanning.getHeure_fin() +"\n" +
                        "description : "+newPlanning.getDescription() +"\n" ;

                ChatConversation chatConversation = new ChatConversation(-1,instance.chatSession.getId(),idadmin,id_coach,newIdPlanning,message);
                chatConversationService.ajouter(chatConversation);

                // Clear input fields
                clearFields();
                refreshPlanningPage();

            } catch (NumberFormatException | SQLException e) {
                e.printStackTrace(); // Handle the exception as needed
            }
        }
    }

    private void setFields(Planning planning) {
        descField.setText(planning.getDescription());
        heureDField.setText(planning.getHeure_debut());
        heureFField.setText(planning.getHeure_fin());
        dateDField.setValue(LocalDate.parse(planning.getDate_debut()));
        dateFField.setValue(LocalDate.parse(planning.getDate_fin()));
    }


    @FXML
    private void handleModifyPlanning() {
        if (controleSaisi()) {
            try {
                // Get values from UI components
                int id_coach = getCoachIdByName(coachComboBox.getValue().toString());
                String date_debut = dateDField.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String date_fin = dateFField.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String heure_debut = heureDField.getText();
                String heure_fin = heureFField.getText();
                String titre = categoryComboBox.getValue().toString();
                String description = descField.getText();

                selectedPlanning = new Planning(selectedPlanning.getId(), id_coach, date_debut, date_fin, heure_debut, heure_fin, titre, description);
                // Modify the planning entry in the database

                planningService.modifier(selectedPlanning);

                // notif for modify *****************
                notif n = new notif(-1,currentAccount.getNom(),currentAccount.getNom() +" has edited a planing ","admin");
                try {
                    notifService.ajouter(n);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                // Clear input fields
                clearFields();
                refreshPlanningPage();

            } catch (NumberFormatException | SQLException e) {
                e.printStackTrace(); // Handle the exception as needed
            }
        }
    }

    private int getCoachIdByName(String coachName) {
        for (Account coach : coaches) {
            if (coach.getNom().equals(coachName)) {
                return coach.getId();
            }
        }
        // Return a default value or handle the case where the coach name is not found
        return -1; // You might want to choose a suitable default value or throw an exception
    }


    // Helper method to clear input fields
    private void clearFields() {
        descField.clear();
        heureDField.clear();
        heureFField.clear();
        dateDField.setValue(null);
        dateFField.setValue(null);
    }



    private boolean controleSaisi() {
        // Check if ComboBoxes are null
        if (coachComboBox.getValue() == null || categoryComboBox.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select a coach and category.");
            return false;
        }

        // You can add more specific validation based on your requirements
        String description = descField.getText();
        String heureDebut = heureDField.getText();
        String heureFin = heureFField.getText();
        LocalDate dateDebut = dateDField.getValue();
        LocalDate dateFin = dateFField.getValue();

        // Check if dateDebut is before dateFin
        if (dateDebut.isAfter(dateFin)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Start date must be before end date.");
            return false;
        }

        // Check if heureDebut is before heureFin
        if (LocalTime.parse(heureDebut).isAfter(LocalTime.parse(heureFin))) {
            showAlert(Alert.AlertType.ERROR, "Error", "Start time must be before end time.");
            return false;
        }

        // Check date format
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        try {
            // Convert LocalDate to String and then parse to check the format
            String formattedDateDebut = dateDebut.format(dateFormatter);
            LocalDate.parse(formattedDateDebut, dateFormatter);

            String formattedDateFin = dateFin.format(dateFormatter);
            LocalDate.parse(formattedDateFin, dateFormatter);

            // Print formatted dates for testing (you can remove this line in your actual code)
            System.out.println("Formatted Date Debut: " + formattedDateDebut);
            System.out.println("Formatted Date Fin: " + formattedDateFin);
        } catch (DateTimeException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid date format. Please use MM/dd/yyyy.");
            return false;
        }

        // Check time format
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        try {
            LocalTime.parse(heureDebut, timeFormatter);
            LocalTime.parse(heureFin, timeFormatter);
        } catch (DateTimeParseException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid time format. Please use HH:mm:ss");
            return false;
        }

        // You can add more specific validation rules here if needed

        return true;
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    //action column for planing table
    Callback<TableColumn<Planning, Void>, TableCell<Planning, Void>> createButtonCellFactoryPlanning() {
        return new Callback<TableColumn<Planning, Void>, TableCell<Planning, Void>>() {
            @Override
            public TableCell<Planning, Void> call(final TableColumn<Planning, Void> param) {
                return new TableCell<Planning, Void>() {

                    final Button deleteButton = createButton("Delete");
                    final Button editButton = createButton("Edit");

                    final Button ChatButton = createButton("Chat");

                    {
                        // Set actions for the buttons
                        deleteButton.setOnAction(event -> {
                            Planning planning = getTableView().getItems().get(getIndex());

                            System.out.println("Delete: " + planning.getId());

                            try {
                                planningService.supprimer(planning.getId());
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            refreshPlanningPage();
                            // Add your delete action here
                        });

                        editButton.setOnAction(event -> {
                            Planning planning = getTableView().getItems().get(getIndex());
                            System.out.println("Edit: " + planning.getId());
                            selectedPlanning=planning;
                            addPlanningInterface();
                            AddPlanningBtn.setVisible(false);
                            EditPlanningBtn.setVisible(true);
                            setFields(planning);

                            // Add your edit action here
                        });

                        ChatButton.setOnAction(event -> {
                            Planning planning = getTableView().getItems().get(getIndex());
                            System.out.println("chat: " + planning.getId());
                            instance.selectedadmin = chatConversationService.getAdminIdByPlanningId(planning.getId(), planning.getId_coach());
                            instance.idplanning = planning.getId();
                            System.out.println("id planning ajouter :"+ instance.idplanning);
                            onChatView(planning);
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
                        buttonPane.getChildren().addAll(deleteButton, editButton,ChatButton);
                        return buttonPane;
                    }
                };
            }
        };
    }















    //chat mabin admin wel coach

    @FXML
    TextField messageField;
    @FXML
    ScrollPane ChatScrollPane;


    @FXML
    VBox messagesContainer ;



    @FXML
    Label ChatTitle;

    @FXML
    Pane chatPage;
    @FXML
    Button editMsgbtn;


    void GoChat(){
        Coachaffichage.setVisible(false);
        Coachaffichage.setManaged(false);
        AddCoachtPage.setVisible(false);
        AddCoachtPage.setManaged(false);
        Planningaffichage.setVisible(false);
        Planningaffichage.setManaged(false);
        AddPlanningtPage.setVisible(false);
        AddPlanningtPage.setManaged(false);
        EditUserPage.setVisible(false);
        EditUserPage.setManaged(false);
        //show edit coach interface
        chatPage.setVisible(true);
        chatPage.setManaged(true);
    }



    AppController appControllerinstance=AppController.getInstance();

    int selectedadmin;// user
    int idCoach;//coach null
    int idplanning;

    ChatSessionService chatSessionService = new ChatSessionService(); //controller crud
    chatSession chatSession = new chatSession(-1, -1 , -1);

    ChatConversationService chatConversationService = new ChatConversationService();//


    ChatConversation selectedMessage ;
    public void onChatView(Planning planning) {
        messagesContainer.getChildren().clear();
        ChatTitle.setText("Chat With Coach ");

        //coach set id
        instance.idCoach= planning.getId_coach();
        instance.chatSession.setId_user2(instance.idCoach);
        //admin set id
        instance.selectedadmin=AppController.getInstance().account.getId();
        instance.chatSession.setId_user(instance.selectedadmin);
        //taw session wallet haka session={-1,user.id,coach.id}

        ChatScrollPane.setContent(messagesContainer);


        try {
            int idSession= chatSessionService.getChatSession(instance.chatSession);

            if(idSession==-1){
                // if chatsession doesnt exist add a new chat session between the sender(user) and reciever (coach)
                chatSessionService.ajouter(instance.chatSession);
                // after adding the new chat session , get the session id from data base
                idSession= chatSessionService.getChatSession(instance.chatSession);
                instance.chatSession.setId(idSession);
                System.out.println(chatSession);


            }
            else {
                //if chatsession exist get the id of the session
                instance.chatSession.setId(idSession);
                System.out.println(instance.chatSession);

            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //load conversation btw the user and the coach
        try {
            chatConversationsList= chatConversationService.getChatConversationsList( instance.chatSession.getId() );
            System.out.println(chatConversationsList);
            loadConversation(chatConversationsList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        GoChat();


    }

    @FXML
    void handleSendMessage() {
        String message = messageField.getText();
        if(!message.isEmpty() && !BadWords.canSend(message)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Mot interdit");
            alert.setHeaderText(null);
            alert.setContentText("Ce mot est interdit.");
            alert.showAndWait();
        }else {

            ChatConversation chatConversation = new ChatConversation(-1, instance.chatSession.getId(), instance.selectedadmin, instance.idCoach, instance.idplanning, message);
            if (imageURL != null) {
                chatConversation.setImageUrl(imageURL);
                imageURL = null;
            }
            try {
                System.out.println("id planning ajouter :" + chatConversation.getIdplanning());
                chatConversationService.ajouter(chatConversation);
                chatConversationsList = chatConversationService.getChatConversationsList(instance.chatSession.getId());
                messagesContainer.getChildren().clear();
                loadConversation(chatConversationsList);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    void loadConversation(ObservableList<ChatConversation> chatConversationsList) {
        if (chatConversationsList != null) {
            for (ChatConversation chatConversation : chatConversationsList) {

                String message = chatConversation.getMessage();
                int sender = chatConversation.getId_sender();
                String imageUrl=chatConversation.getImageUrl();

                // label
                Label messageLabel;
                HBox messageHBox;

                if (sender == instance.selectedadmin) {
                    messageLabel = new Label("you: " + message);
                    messageLabel.getStyleClass().add("user-message");

                    // hbox
                    messageHBox = new HBox();
                    messageHBox.setAlignment(Pos.CENTER_RIGHT);
                    messageHBox.getStyleClass().add("user-message-hbox");


                    // button
                    Button actionButton = new Button("");

                    actionButton.setOnAction( event -> {
                        handleButtonAction(chatConversation);
                        editMsgbtn.setVisible(true);
                        editMsgbtn.setManaged(true);
                    }); // Replace handleButtonAction with your actual method

                    if(imageUrl!=null){
                        try {
                            String absolutePath = new File(destinationFolderPath+imageUrl).getAbsolutePath();
                            System.out.println(absolutePath);
                            Image image = new Image("file:" + absolutePath);

                            ImageView imageView = new ImageView(image);
                            imageView.setFitWidth(100); // Adjust width as needed
                            imageView.setFitHeight(100); // Adjust height as needed

                            // hbox for image
                            HBox imageHBox = new HBox(imageView);
                            imageHBox.setAlignment(Pos.CENTER_RIGHT);

                            // Add label and button to hbox
                            messageHBox.getChildren().addAll(actionButton, messageLabel);
                            messagesContainer.getChildren().add(messageHBox);
                            messagesContainer.getChildren().add(imageHBox);
                        }catch (Exception e){
                            String absolutePath = new File(destinationFolderPath+imageUrl).getAbsolutePath();
                            System.out.println(absolutePath);
                            Image image = new Image("file:" + absolutePath);

                            ImageView imageView = new ImageView(image);
                            imageView.setFitWidth(100); // Adjust width as needed
                            imageView.setFitHeight(100); // Adjust height as needed

                            // hbox for image
                            HBox imageHBox = new HBox(imageView);
                            imageHBox.setAlignment(Pos.CENTER_RIGHT);

                            // Add label and button to hbox
                            messageHBox.getChildren().addAll(actionButton, messageLabel);
                            messagesContainer.getChildren().add(messageHBox);
                            messagesContainer.getChildren().add(imageHBox);
                        }
                    }else {
                        // Add label and button to hbox
                        messageHBox.getChildren().addAll(actionButton, messageLabel);
                        messagesContainer.getChildren().add(messageHBox);

                    }

                } else {
                    messageLabel = new Label("coach : " + message);
                    messageLabel.getStyleClass().add("user-message");

                    // hbox
                    messageHBox = new HBox();
                    messageHBox.setAlignment(Pos.CENTER_LEFT);
                    messageHBox.getStyleClass().add("coach-message-hbox");
                    // Add label and button to hbox
                    if(imageUrl!=null){
                        String absolutePath = new File(destinationFolderPath+imageUrl).getAbsolutePath();
                        System.out.println(absolutePath);
                        Image image = new Image("file:" + absolutePath);

                        ImageView imageView = new ImageView(image);
                        imageView.setFitWidth(100); // Adjust width as needed
                        imageView.setFitHeight(100); // Adjust height as needed

                        // hbox for image
                        HBox imageHBox = new HBox(imageView);
                        imageHBox.setAlignment(Pos.CENTER_LEFT);

                        // Add label and button to hbox
                        messageHBox.getChildren().addAll(messageLabel);
                        messagesContainer.getChildren().add(messageHBox);
                        messagesContainer.getChildren().add(imageHBox);

                        // Add imageHBox to vbox

                    }else {
                        // Add label and button to hbox
                        messageHBox.getChildren().addAll( messageLabel);
                        messagesContainer.getChildren().add(messageHBox);

                    }

                }



                // Add hbox to vbox

            }
        }
    }

    void handleButtonAction(ChatConversation chatConversation){
        instance.selectedMessage=chatConversation;
        messageField.setText(instance.selectedMessage.getMessage());

    }



    @FXML
    void handleClearConversation() throws SQLException {

        chatConversationService.deleteConversationsByChatSessionId(instance.chatSession.getId());
        messagesContainer.getChildren().clear();

    }


    @FXML
    void handleModifierMessage() throws SQLException {

        String message = messageField.getText();
        instance.selectedMessage.setMessage(message);

        instance.selectedMessage.setIdplanning(instance.idplanning);
        chatConversationService.modifier(instance.selectedMessage);
        messagesContainer.getChildren().clear();
        loadConversation(chatConversationsList);

        editMsgbtn.setVisible(false);
        editMsgbtn.setManaged(false);
    }


    @FXML
    public void handleAddImage(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();


        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files (*.png, *.jpg, *.gif)", "*.png", "*.jpg", "*.gif");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show open file dialog
        Stage stage = (Stage) editMsgbtn.getScene().getWindow(); // Replace 'yourNode' with the actual node from your FXML
        java.io.File file = fileChooser.showOpenDialog(stage);


        if (file != null) {
            // Process the selected file (e.g., display it in an ImageView)
            System.out.println("Selected Image: " + file.getAbsolutePath());
            String absolutePath=file.getAbsolutePath();
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String generatedID = "image_" + timestamp;
            String fileExtension = file.getName().substring(file.getName().lastIndexOf("."));
            File destinationFolder = new File(destinationFolderPath);
            if (!destinationFolder.exists()) {
                destinationFolder.mkdirs();
            }
            String finalFileName=generatedID + fileExtension;
            String destinationFilePath = destinationFolderPath + finalFileName;
            File destinationFile = new File(destinationFilePath);
            try {
                // Copy the selected file to the destination folder
                Files.copy(file.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                imageURL=finalFileName;

                // Process the selected file (e.g., display it in an ImageView)
                System.out.println("Selected Image: " + destinationFile.getAbsolutePath());
                // Update your ImageView or perform other actions
            } catch (IOException e) {
                e.printStackTrace();
                //showAlert(AlertType.ERROR, "Error", "Failed to copy the image to the destination folder.");
            }


            //copy to resources folder and save it with generatedID depdends on timestimp

            // Update your ImageView or perform other actions
        } else {
            // Display an error message if no file was selected
            //  showAlert(AlertType.ERROR, "Error", "No image selected.");
        }
    }

}





