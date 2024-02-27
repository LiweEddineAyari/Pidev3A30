package main.projet;

import entity.Account;
import entity.ChatConversation;
import entity.chatSession;
import interfaces.UserListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import services.AccountService;
import services.ChatConversationService;
import services.ChatSessionService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class userCoachInterfaceController implements Initializable, UserListener {


    private  static  userCoachInterfaceController instance=new userCoachInterfaceController();
    public  static  userCoachInterfaceController getInstance(){return instance;};

    AppController appControllerinstance=AppController.getInstance();

    Account coach; //sender
    Account user;// reciever

    ChatSessionService chatSessionService = new ChatSessionService(); //controller crud
    chatSession chatSession = new chatSession(-1, -1, -1);

    ChatConversationService chatConversationService = new ChatConversationService();


    ChatConversation selectedMessage ;
    ObservableList<ChatConversation> chatConversationsList;





    @FXML
    GridPane userGrid;
    @FXML
    Label ChatTitle;

    @FXML
    Pane chatPage;
    @FXML
    VBox userInterface;

    AccountService accountService =new AccountService();
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        editMsgbtn.setVisible(false);
        editMsgbtn.setManaged(false);
        intitialisationCoachesList();
    }

    void intitialisationCoachesList(){
        int column = 1, row = 0;
        System.out.println(users);

        try {
            for (Account user : users) {
                System.out.println(user);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("userItemCard.fxml"));
                VBox usercard = loader.load();

                if (column == 4) {
                    row++;
                    column = 0;
                }

                usertemCard itemCardController = loader.getController();
                itemCardController.setData(user,this);

                userGrid.add(usercard, column++, row);
                GridPane.setMargin(usercard, new Insets(20));
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

    @FXML
    Button editMsgbtn;

    @Override
    public void onChatView(Account user) {
        messagesContainer.getChildren().clear();
        ChatTitle.setText("Chat With "+user.getNom()+" "+user.getPrenom());

        //set user id
        instance.user=user;
        instance.chatSession.setId_user2(instance.user.getId());
        //set coach id
        instance.coach=AppController.getInstance().account;
        instance.chatSession.setId_user(instance.coach.getId());
        //taw session wallet haka session={-1,user.id,coach.id}
        CoachChatScrollPane.setContent(messagesContainer);

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


        userInterface.setVisible(false);
        userInterface.setManaged(false);

        chatPage.setVisible(true);
        chatPage.setManaged(true);

    }
    @FXML
    void handleSendMessage() {
        String message = messageField.getText();
        ChatConversation chatConversation = new ChatConversation(-1,instance.chatSession.getId(),instance.coach.getId(),instance.user.getId(),-1,message);

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

                if (sender == instance.coach.getId()) {
                    messageLabel = new Label("you: " + message);
                    messageLabel.getStyleClass().add("user-message");

                    // hbox
                    messageHBox = new HBox();
                    messageHBox.setAlignment(Pos.CENTER_RIGHT);
                    messageHBox.getStyleClass().add("user-message-hbox");


                    // button
                    Button actionButton = new Button("");
                    actionButton.getStyleClass().add("editmsgBtnStyle");

                    actionButton.setOnAction(event -> {
                        handleButtonAction(chatConversation);
                        editMsgbtn.setVisible(true);
                        editMsgbtn.setManaged(true);

                    }); // Replace handleButtonAction with your actual method

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
        editMsgbtn.setVisible(false);
        editMsgbtn.setManaged(false);

    }


    @FXML
    public void GoToUsers() {
        userInterface.setVisible(true);
        userInterface.setManaged(true);

        chatPage.setVisible(false);
        chatPage.setManaged(false);
    }



}
