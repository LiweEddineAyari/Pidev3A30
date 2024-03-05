package main.projet;

import entity.Account;
import entity.ChatConversation;
import entity.chatSession;
import interfaces.CoachListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.AccountService;
import services.ChatConversationService;
import services.ChatSessionService;
import utils.BadWords;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CoachUserController implements Initializable, CoachListener {

    private  static  CoachUserController instance=new CoachUserController();
    public  static  CoachUserController getInstance(){return instance;};

    private String imageURL=null;
    String destinationFolderPath = "src/main/java/uploads/chatImages/"; // Adjust the path accordingly
    AppController appControllerinstance=AppController.getInstance();

    Account user;// user
    Account coach;//coach null


    ChatSessionService chatSessionService = new ChatSessionService(); //controller crud
    chatSession chatSession = new chatSession(-1, -1 , -1);

    ChatConversationService chatConversationService = new ChatConversationService();//


    ChatConversation selectedMessage ;
    ObservableList<ChatConversation> chatConversationsList;

    @FXML
    Button editMsgbtn;


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
        editMsgbtn.setVisible(false);
        editMsgbtn.setManaged(false);
        intitialisationCoachesList();
        CoachChatScrollPane.setContent(messagesContainer);
    }

    void intitialisationCoachesList(){
        int column = 1, row = 0;

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

        messagesContainer.getChildren().clear();
        ChatTitle.setText("Chat With "+coach.getNom()+" "+coach.getPrenom());

        //coach set id
        instance.coach=coach;
        instance.chatSession.setId_user2(instance.coach.getId());
        //user set id
        instance.user=AppController.getInstance().account;
        instance.chatSession.setId_user(instance.user.getId());
        //taw session wallet haka session={-1,user.id,coach.id}

        CoachChatScrollPane.setContent(messagesContainer);

        if(instance.chatConversationService.getBlockedList(coach.getId()).isUserBlocked(instance.user.getId())){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("You are blocked from sending message to this coach");
            alert.setContentText("You are blocked from sending message to this coach");
            alert.showAndWait();
        }
        else{
            try {
                int idSession = chatSessionService.getChatSession(instance.chatSession);

                if (idSession == -1) {
                    // if chatsession doesnt exist add a new chat session between the sender(user) and reciever (coach)
                    chatSessionService.ajouter(instance.chatSession);
                    // after adding the new chat session , get the session id from data base
                    idSession = chatSessionService.getChatSession(instance.chatSession);
                    instance.chatSession.setId(idSession);
                    System.out.println(chatSession);


                } else {
                    //if chatsession exist get the id of the session
                    instance.chatSession.setId(idSession);
                    System.out.println(instance.chatSession);

                }


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            //load conversation btw the user and the coach
            try {
                chatConversationsList = chatConversationService.getChatConversationsList(instance.chatSession.getId());
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
        }
        else {

            ChatConversation chatConversation = new ChatConversation(-1, instance.chatSession.getId(),instance.user.getId(), instance.coach.getId(), -1, message);
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

                    actionButton.setOnAction( event -> {
                        handleButtonAction(chatConversation);
                        editMsgbtn.setVisible(true);
                        editMsgbtn.setManaged(true);
                    }); // Replace handleButtonAction with your actual method
                    imageURL = chatConversation.getImageUrl();

                    System.out.println("image li jebneha "+imageURL);

                    if(imageURL!=null){
                        try {
                            String absolutePath = new File(destinationFolderPath+imageURL).getAbsolutePath();
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
                            String absolutePath = new File(destinationFolderPath+imageURL).getAbsolutePath();
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
                    // Add label and button to hbox

                } else {
                    messageLabel = new Label("coach : " + message);
                    messageLabel.getStyleClass().add("user-message");

                    // hbox
                    messageHBox = new HBox();
                    messageHBox.setAlignment(Pos.CENTER_LEFT);
                    messageHBox.getStyleClass().add("coach-message-hbox");
                    imageURL = chatConversation.getImageUrl();
                    if(imageURL!=null){
                        String absolutePath = new File(destinationFolderPath+imageURL).getAbsolutePath();
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
        imageURL=null;

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



    //image
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
