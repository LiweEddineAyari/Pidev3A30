package main.projet;

import entity.Account;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import services.AccountService;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;



public class editProfil implements Initializable{

    @FXML
    VBox editAdminAffichage;


    @FXML
    TextField adminENameField,adminEPrenomField,adminEAgeField,adminEMailField1,adminEPasswordField;

    @FXML
    public void handleEditProduct(){
        String name = adminENameField.getText();
        String prenom =  adminEPrenomField.getText();
        int age =  Integer.parseInt(adminEAgeField.getText());
        String mail = adminEMailField1.getText();
        String password =  adminEPasswordField.getText();


        Account account = new Account( 5, name, prenom, age, mail, password,Account.Title.admin);
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
    public void reload_page(){


    }



    void initAdminInputs(){
        adminENameField.setText("");
        adminEPrenomField.setText("");
        adminEAgeField.setText("");
        adminEMailField1.setText("");
        adminEPasswordField.setText("");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        editAdminAffichage.setVisible(true);
        editAdminAffichage.setManaged(true);
    }
}
