package main.projet;

import entity.Account;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.AccountService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class HelloController implements Initializable {
    public static String email;
    @FXML
    VBox Vboxlogin;
    @FXML
    AnchorPane loginpane;
    @FXML
    AnchorPane signuppane,newpass;
    @FXML
    ImageView imgview;

    AccountService accountService = new AccountService();


    @FXML
    TextField mailSignup, nameSignup, passwordSignup, ageSignup, firstnameSignup, maillogin
            , codeField , newpass1 , newpass2 ;

    @FXML
    PasswordField  passwordlogin ;

    void initAdminInputs() {
        nameSignup.setText("");
        firstnameSignup.setText("");
        ageSignup.setText("");
        mailSignup.setText("");
        passwordSignup.setText("");
    }

    @FXML
    void handleSignUp() {
        String name = nameSignup.getText();
        String prenom = firstnameSignup.getText();
        int age = Integer.parseInt(ageSignup.getText());
        String mail = mailSignup.getText();
        String password = passwordSignup.getText();

        // Validate email format
        if (!isValidEmail(mail)) {
            showAlert("Invalid Email", "Please enter a valid email address.");
            return;
        }

        // Additional validation for other fields if needed...

        Account account = new Account(-1, name, prenom, age, mail, password, Account.Title.user);
        try {
            accountService.ajouter(account);
            switchToLogin();
            initAdminInputs();
        } catch (SQLException e) {
            showAlert("Sign Up Failed", "Error adding the account. Please try again.");
            System.out.println(e.getMessage());
        }
    }


    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image image = new Image(getClass().getResourceAsStream("images/FitHubPro.png"));
        imgview.setImage(image);
        loginpane.setVisible(true);
        signuppane.setVisible(false);
    }

    public void switchToSignUp() {
        loginpane.setVisible(false);
        loginpane.setManaged(false);
        signuppane.setVisible(true);
        signuppane.setManaged(true);
        newpass.setManaged(false);
        newpass.setVisible(false);
    }

    public void switchToLogin() {
        signuppane.setVisible(false);
        signuppane.setManaged(false);
        loginpane.setVisible(true);
        loginpane.setManaged(true);
        newpass.setManaged(false);
        newpass.setVisible(false);
    }


    public void switchToverify() {


    }

    public void login(ActionEvent event) throws IOException, SQLException {
        String email = maillogin.getText();
        String password = passwordlogin.getText();

        Account account = accountService.authenticate(email, password);
        System.out.println(account);
        if (account != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("app.fxml"));
            Parent root = loader.load();

            AppController appController = loader.getController();
            String role = "";
            if (account.getTitle().equals(Account.Title.admin)) {
                role = "admin";
            }
            if (account.getTitle().equals(Account.Title.user)) {
                role = "user";
            }
            if (account.getTitle().equals(Account.Title.coach)) {
                role = "coach";
            }

            appController.sentUserData(role, account);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1600, 900);
            scene.getStylesheets().add("file:/C:/Users/JAXIM/IdeaProjects/projet/src/main/resources/main/projet/css/app.css");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } else {
            showAlert("Login Failed", "Invalid email or password. Please try again.");
        }


    }




// ...

    @FXML
    void envoyerEmail(ActionEvent event) {


        // Retrieve the email from the maillogin TextField
        HelloController.email = maillogin.getText();

        // Check if the email is null or not in a valid format
        if (HelloController.email == null || !isValidEmail(HelloController.email)) {
            // Show an alert for invalid email format
            showAlert("Error", "Invalid Email Format", "Please enter a valid email address.");
            return; // Stop further execution
        }


        signuppane.setVisible(false);
        signuppane.setManaged(false);
        loginpane.setVisible(false);
        loginpane.setManaged(false);
        newpass.setManaged(true);
        newpass.setVisible(true);

        // Send the email
        GMailer.EmailTest(HelloController.email);
        System.out.println("Email sent successfully.");
        String code = Account.getCodemail();

        System.out.println(code);
    }




    @FXML
    void submit(ActionEvent event) throws SQLException {
        String codeInput = codeField.getText();
        String generatedCode = Account.getCodemail();

        AccountService accountService = new AccountService();
        System.out.println("mmmmmail :"+HelloController.email);
        //Account account =  accountService.search(HelloController.email);

        if (HelloController.email != null) {
            if (codeInput.equals(generatedCode)) {

                String newPassword = newpass1.getText();
                String confirmPassword = newpass2.getText();


                if (newPassword.equals(confirmPassword)) {




                    accountService.modifierP(HelloController.email,newPassword);

                    System.out.println("Password successfully updated: " + newPassword);
                } else {

                    showAlert("Error", "Password Confirmation Error", "Passwords do not match.");
                }
            } else {

                showAlert("Error", "Code Mismatch", "Entered code does not match the generated code.");
            }
        } else {

            showAlert("Error", "Account Not Found", "Account not found for the given email.");
        }
        switchToLogin();
    }


    private void showAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }






}