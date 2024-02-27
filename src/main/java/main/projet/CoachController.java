package main.projet;

import entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import services.AccountService;
import services.ChatConversationService;
import services.ChatSessionService;
import services.PlanningService;

import java.net.URL;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CoachController implements Initializable {

    private static CoachController instance =new CoachController();
    public static CoachController getInstance() {
        return instance;
    }

    private int SelectedCoachid;

    ObservableList<ChatConversation> chatConversationsList;

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
     TextField titreField,heureDField,heureFField,idcoachField;
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
                int id_coach = Integer.parseInt(idcoachField.getText());
                String date_debut = dateDField.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String date_fin = dateFField.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String heure_debut = heureDField.getText();
                String heure_fin = heureFField.getText();
                String titre = titreField.getText();
                String description = descField.getText();

                // Create a new Planning object
                Planning newPlanning = new Planning(0, id_coach, date_debut, date_fin, heure_debut, heure_fin, titre, description);

                // Add the new planning entry to the database
                   int newIdPlanning= planningService.ajouter(newPlanning);


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
        titreField.setText(planning.getTitre());
        descField.setText(planning.getDescription());
        heureDField.setText(planning.getHeure_debut());
        heureFField.setText(planning.getHeure_fin());
        idcoachField.setText(String.valueOf(planning.getId_coach()));
        dateDField.setValue(LocalDate.parse(planning.getDate_debut()));
        dateFField.setValue(LocalDate.parse(planning.getDate_fin()));
    }


    @FXML
    private void handleModifyPlanning() {
        if (controleSaisi()) {
            try {
                // Get values from UI components
                int id_coach = Integer.parseInt(idcoachField.getText());
                String date_debut = dateDField.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String date_fin = dateFField.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String heure_debut = heureDField.getText();
                String heure_fin = heureFField.getText();
                String titre = titreField.getText();
                String description = descField.getText();

                selectedPlanning = new Planning(selectedPlanning.getId(), id_coach, date_debut, date_fin, heure_debut, heure_fin, titre, description);
                // Modify the planning entry in the database

                planningService.modifier(selectedPlanning);

                // Clear input fields
                clearFields();
                refreshPlanningPage();

            } catch (NumberFormatException | SQLException e) {
                e.printStackTrace(); // Handle the exception as needed
            }
        }
    }

    // Helper method to clear input fields
    private void clearFields() {
        titreField.clear();
        descField.clear();
        heureDField.clear();
        heureFField.clear();
        idcoachField.clear();
        dateDField.setValue(null);
        dateFField.setValue(null);
    }



    private boolean controleSaisi() {
        // You can add more specific validation based on your requirements
        String titre = titreField.getText();
        String description = descField.getText();
        String heureDebut = heureDField.getText();
        String heureFin = heureFField.getText();
        String idCoach = idcoachField.getText();
        LocalDate dateDebut = dateDField.getValue();
        LocalDate dateFin = dateFField.getValue();

        // Check if any field is empty
        if (titre.isEmpty() || description.isEmpty() || heureDebut.isEmpty() || heureFin.isEmpty() ||
                idCoach.isEmpty() || dateDebut == null || dateFin == null) {
            // Show an error message or handle the validation failure accordingly
            System.out.println("Please fill in all fields.");
            showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all fields.");
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
        ChatTitle.setText("Chat With Coach "+planning.getId_coach());

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
        ChatConversation chatConversation = new ChatConversation(-1,instance.chatSession.getId(),instance.selectedadmin,instance.idCoach,instance.idplanning,message);

        try {
            System.out.println("id planning ajouter :"+chatConversation.getIdplanning());
            chatConversationService.ajouter(chatConversation);
            chatConversationsList= chatConversationService.getChatConversationsList( instance.chatSession.getId() );
            messagesContainer.getChildren().clear();
            loadConversation(chatConversationsList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    void loadConversation(ObservableList<ChatConversation> chatConversationsList) {
        if (chatConversationsList != null) {
            for (ChatConversation chatConversation : chatConversationsList) {

                String message = chatConversation.getMessage();
                int sender = chatConversation.getId_sender();

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


                    // Add label and button to hbox
                    messageHBox.getChildren().addAll(actionButton,messageLabel);


                } else {
                    messageLabel = new Label(instance.idCoach + ": " + message);
                    messageLabel.getStyleClass().add("user-message");

                    // hbox
                    messageHBox = new HBox();
                    messageHBox.setAlignment(Pos.CENTER_LEFT);
                    messageHBox.getStyleClass().add("coach-message-hbox");
                    // Add label and button to hbox
                    messageHBox.getChildren().addAll(messageLabel);
                }



                // Add hbox to vbox
                messagesContainer.getChildren().add(messageHBox);
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


}





