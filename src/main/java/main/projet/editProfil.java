package main.projet;

import entity.Account;

import entity.notif;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import services.AccountService;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import services.notifService;


public class editProfil implements Initializable {

    @FXML
    VBox editAdminAffichage;

    @FXML
    TextField adminENameField, adminEPrenomField, adminEAgeField, adminEMailField1, adminEPasswordField;

    private Account currentAccount;
    notifService notifService = new notifService();


    @FXML
    public void handleEditAccount() {
        try {
            String name = adminENameField.getText();
            String prenom = adminEPrenomField.getText();
            int age = Integer.parseInt(adminEAgeField.getText());
            String mail = adminEMailField1.getText();
            String password = adminEPasswordField.getText();

            if (!name.equals(currentAccount.getNom()) ||
                    !prenom.equals(currentAccount.getPrenom()) ||
                    age != currentAccount.getAge() ||
                    !mail.equals(currentAccount.getMail()) ||
                    !password.equals(currentAccount.getPassword())) {

                Account account = new Account(
                        currentAccount.getId(),
                        name,
                        prenom,
                        age,
                        mail,
                        password,
                        Account.Title.admin
                );

                AccountService accountService = new AccountService();
                accountService.modifier(account);

                notif n = new notif(-1,currentAccount.getNom(),currentAccount.getNom() +" edited his account  ","admin");
                try {
                    notifService.ajouter(n);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                reload_page();
            } else {
                // No changes were made
                showAlert(AlertType.INFORMATION, "No Changes", "No changes made.", null);
            }
        } catch (SQLException | NumberFormatException e) {
            showAlert(AlertType.ERROR, "Error", "Error editing account", e.getMessage());
        }
    }

    private void showAlert(AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    @FXML
    public void reload_page() {
        // Add code to reload the page if needed
    }

    private void fillInputs(Account account) {
        adminENameField.setText(account.getNom());
        adminEPrenomField.setText(account.getPrenom());
        adminEAgeField.setText(String.valueOf(account.getAge()));
        adminEMailField1.setText(account.getMail());
        adminEPasswordField.setText(account.getPassword());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            AccountService accountService = new AccountService();
            currentAccount = accountService.getAccountByAccountId(Account.getCurrentid());
            fillInputs(currentAccount);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        editAdminAffichage.setVisible(true);
        editAdminAffichage.setManaged(true);
        // Optionally, you can initialize the inputs here if needed
    }
}
