package main.projet;

import entity.Account;
import entity.ChatConversation;
import entity.Planning;
import entity.chatSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import services.AbonnementService;
import services.ChatConversationService;
import services.ChatSessionService;
import services.PlanningService;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PlanningController implements Initializable {

    private static  PlanningController instance = new PlanningController();

    public static PlanningController getInstance() {
        return instance;
    }

    int selectedadmin;
    Account coach = AppController.getInstance().account;


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

    @FXML
     VBox Planningaffichage;
    @FXML
    Pane chatPage;



    private PlanningService planningService =new PlanningService();
    private ChatConversationService chatConversationService = new ChatConversationService();
    ObservableList<Planning> plannings;

    {
        try {
            plannings = planningService.afficher();
            plannings= plannings.stream()
                    .filter(planning -> planning.getId_coach()==AppController.getInstance().account.getId())
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    ObservableList<ChatConversation> chatConversationsList;



    @FXML
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
        chatPage.setVisible(false);
        chatPage.setManaged(false);
        Planningaffichage.setVisible(true);
        Planningaffichage.setManaged(true);

    }





    Callback<TableColumn<Planning, Void>, TableCell<Planning, Void>> createButtonCellFactoryPlanning() {
        return new Callback<TableColumn<Planning, Void>, TableCell<Planning, Void>>() {
            @Override
            public TableCell<Planning, Void> call(final TableColumn<Planning, Void> param) {
                return new TableCell<Planning, Void>() {


                    final Button ChatButton = createButton("Chat");
                    final Button send = createButton("SendToUsers");

                    {

                        ChatButton.setOnAction(event -> {
                            Planning planning = getTableView().getItems().get(getIndex());
                            System.out.println("chat: " + planning.getId());
                             instance.selectedadmin = chatConversationService.getAdminIdByPlanningId(planning.getId(), planning.getId_coach());
                             instance.idplanning = planning.getId();
                            System.out.println("admin is : "+instance.selectedadmin);
                            System.out.println("coach is : "+planning.getId_coach());

                            GoChat();
                            onChatView(planning);

                        });
                        send.setOnAction(event -> {
                            Planning planning = getTableView().getItems().get(getIndex());
                            System.out.println("chat: " + planning.getId());
                            AbonnementService abonnementService = new AbonnementService();
                            String members;
                            try {
                               members = abonnementService.getMembersByCategoryName(planning.getTitre());
                                System.out.println("members : "+members);
                                String[] userIdsArray = members.split(",");
                                int iduser;
                                for (String userId : userIdsArray) {
                                    // Convert each user ID to an integer and add it to the list
                                    iduser = Integer.parseInt(userId);
                                    sendTousers(planning , iduser);
                                }


                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }


                          refreshPlanningPage();

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
                        buttonPane.getChildren().addAll(send,ChatButton);
                        return buttonPane;
                    }
                };
            }
        };
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
    Button editMsgbtn;


    void GoChat(){
        Planningaffichage.setVisible(false);
        Planningaffichage.setManaged(false);
        //show edit coach interface
        chatPage.setVisible(true);
        chatPage.setManaged(true);
    }



    AppController appControllerinstance=AppController.getInstance();

    Account admin;// user
    int idCoach;//coach null
    int idplanning;
    ChatSessionService chatSessionService = new ChatSessionService(); //controller crud
    entity.chatSession chatSession = new chatSession(-1, -1 , -1);



    ChatConversation selectedMessage ;

    public void onChatView(Planning planning) {
        messagesContainer.getChildren().clear();
        ChatTitle.setText("Chat With admin "+planning.getId_coach());

        //coach set id
        instance.idCoach= planning.getId_coach();
        instance.chatSession.setId_user2(instance.idCoach);
        //admin set id

        instance.chatSession.setId_user(instance.selectedadmin);
        //taw session wallet haka session={-1,user.id,coach.id}

        ChatScrollPane.setContent(messagesContainer);

        System.out.println("id session : "+ instance.chatSession);

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
        ChatConversation chatConversation = new ChatConversation(-1,instance.chatSession.getId(),instance.idCoach,instance.selectedadmin,instance.idplanning,message);

        try {
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

                if (sender == instance.idCoach) {
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
                    messageLabel = new Label(instance.selectedadmin+": " + message);
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








    void sendTousers(Planning planning, int user){
        //lawej ken fama chat session ma el user sinn naml wahda jdida
        instance.chatSession.setId_user(planning.getId_coach());
        instance.chatSession.setId_user2(user);



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

            //tw baed nabath planning f msg ll user hedhka
            String message = "new planning \n" +
                    "titre : "+planning.getTitre() +"\n" +
                    "date debut : "+planning.getHeure_debut() +"\n" +
                    "date fin : "+planning.getDate_fin() +"\n" +
                    "heure debut  : "+planning.getHeure_debut() +"\n" +
                    "heure fin : "+planning.getHeure_fin() +"\n" +
                    "description : "+planning.getDescription() +"\n" ;

            ChatConversation chatConversation = new ChatConversation(-1,instance.chatSession.getId(),planning.getId_coach(),user,planning.getId(),message);
            chatConversationService.ajouter(chatConversation);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }





}
