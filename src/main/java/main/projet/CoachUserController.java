package main.projet;

import entity.Account;
import entity.ChatConversation;
import entity.chatSession;
import interfaces.CoachListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import services.AccountService;
import services.ChatConversationService;
import services.ChatSessionService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CoachUserController implements Initializable, CoachListener {

    private  static  CoachUserController instance=new CoachUserController();
    public  static  CoachUserController getInstance(){return instance;};


    AppController appControllerinstance=AppController.getInstance();

    Account user=appControllerinstance.account;// user
    Account coach;//coach null


    ChatSessionService chatSessionService = new ChatSessionService(); //controller crud
    chatSession chatSession = new chatSession(-1, user.getId(), -1);

    ChatConversationService chatConversationService = new ChatConversationService();//


    ChatConversation selectedMessage ;
    ObservableList<ChatConversation> chatConversationsList;



    @FXML
     VBox coachInterfaceuser;
    @FXML
    Pane chatPage;
     @FXML
    Label ChatTitle;
    @FXML
     ScrollPane coachScroll;
    @FXML
     GridPane coachGrid;



    AccountService accountService =new AccountService();
    ObservableList<Account> coaches;
    {
        try {
            coaches = accountService.afficher();
            coaches  =coaches.stream()
                    .filter(account -> account.getTitle().equals(Account.Title.coach))
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void GoToCoaches(){
        chatPage.setVisible(false);
        chatPage.setManaged(false);
        coachInterfaceuser.setVisible(true);
        coachInterfaceuser.setManaged(true);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        intitialisationCoachesList();
        CoachChatScrollPane.setContent(messagesContainer);
    }

    void intitialisationCoachesList(){
        int column = 0, row = 0;

        try {
            for (Account coach : coaches) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("coachItemCard.fxml"));
                VBox coachCard = loader.load();

                if (column == 4) {
                    row++;
                    column = 0;
                }

                CoachItemCard itemCardController = loader.getController();
                itemCardController.setData(coach,this);

                coachGrid.add(coachCard, column++, row);
                GridPane.setMargin(coachCard, new Insets(20));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }















    @FXML
    TextField messageField;
    @FXML
    ScrollPane CoachChatScrollPane;


    @FXML
    VBox messagesContainer ;


    @Override
    public void onChatView(Account coach) {
        ChatTitle.setText("Chat With "+coach.getNom()+" "+coach.getPrenom());


        instance.coach=coach;

        instance.chatSession.setId_user2(instance.coach.getId());

        CoachChatScrollPane.setContent(messagesContainer);


        try {
            int idSession= chatSessionService.getChatSession(instance.chatSession,"user interface");

            if(idSession==-1){
                // if chatsession doesnt exist add a new chat session between the sender(user) and reciever (coach)
                chatSessionService.ajouter(instance.chatSession);
                // after adding the new chat session , get the session id from data base
                idSession= chatSessionService.getChatSession(instance.chatSession,"user interface");
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


        coachInterfaceuser.setVisible(false);
        coachInterfaceuser.setManaged(false);
        chatPage.setVisible(true);
        chatPage.setManaged(true);
    }

    @FXML
    void handleSendMessage() {
        String message = messageField.getText();
        ChatConversation chatConversation = new ChatConversation(-1,instance.chatSession.getId(),instance.user.getId(),instance.coach.getId(),message);

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

                if (sender == instance.user.getId()) {
                    messageLabel = new Label("you: " + message);
                    messageLabel.getStyleClass().add("user-message");

                    // hbox
                    messageHBox = new HBox();
                    messageHBox.setAlignment(Pos.CENTER_RIGHT);
                    messageHBox.getStyleClass().add("user-message-hbox");


                    // button
                    Button actionButton = new Button("");
                    actionButton.setOnAction(event -> handleButtonAction(chatConversation)); // Replace handleButtonAction with your actual method

                    // Add label and button to hbox
                    messageHBox.getChildren().addAll(actionButton,messageLabel);


                } else {
                    messageLabel = new Label(instance.coach.getNom() + ": " + message);
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
        chatConversationService.modifier(instance.selectedMessage);
        messagesContainer.getChildren().clear();
        loadConversation(chatConversationsList);


    }


}
