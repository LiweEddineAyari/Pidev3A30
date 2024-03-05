package main.projet;

import entity.Account;
import entity.ChatConversation;
import interfaces.UserListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import services.ChatConversationService;

public class usertemCard  {

    @FXML
     VBox coachCardItem;

    @FXML
     Label coachfirstname;

    @FXML
     Label coachname;

    Account user;
    UserListener listener;
    ChatConversationService chatConversationService = new ChatConversationService();
    @FXML
    Button UnblockBtn,blockBtn;

    @FXML
    void handleViewChat(ActionEvent event) {
        if (listener != null) {
            listener.onChatView(user);
        }
    }


    public void setData(Account user,UserListener listener ) {
        this.user=user;
        this.listener=listener;

        coachname.setText(user.getNom());
        coachfirstname.setText(user.getPrenom());

        int idcoach=AppController.getInstance().account.getId(),iduser= user.getId();
        if(chatConversationService.getBlockedList( idcoach ).isUserBlocked( iduser )){
          blockBtn.setVisible(false);
          blockBtn.setManaged(false);
            UnblockBtn.setVisible(true);
            UnblockBtn.setManaged(true);
        }
        else {
            blockBtn.setVisible(true);
            blockBtn.setManaged(true);
            UnblockBtn.setVisible(false);
            UnblockBtn.setManaged(false);
        }

    }


    @FXML
    private void handleViewChat() {
        if (listener != null) {
            listener.onChatView(user);
        }
    }

    @FXML
    void handleBlock(ActionEvent event) {
        int idcoach=AppController.getInstance().account.getId(),iduser= user.getId();
         chatConversationService.addToBlockedList(idcoach,iduser);


        blockBtn.setVisible(false);
        blockBtn.setManaged(false);

        UnblockBtn.setVisible(true);
        UnblockBtn.setManaged(true);
    }

    @FXML
    void handleUnBlock(ActionEvent event) {
        int idcoach=AppController.getInstance().account.getId(),iduser= user.getId();
        chatConversationService.removeBlock(idcoach,iduser);

        blockBtn.setVisible(true);
        blockBtn.setManaged(true);

        UnblockBtn.setVisible(false);
        UnblockBtn.setManaged(false);
    }

}
