package main.projet;

import entity.Account;
import entity.notif;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import javafx.event.ActionEvent;
import entity.gym;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javafx.util.Callback;
import services.AccountService;
import services.gymService;
import services.notifService;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class GymController implements Initializable {

    @FXML
    private VBox AbonnementInterfaces;

    @FXML
    private VBox AddAdminCard;

    @FXML
    private Pane AddGymPage;

    @FXML
    private Button EditSubBtn;

    @FXML
    private VBox GymPage;

    @FXML
    private Pane ListContainer;

    @FXML
    private Pane UpperSection;

    @FXML
    private TableView<gym> abonnementTableView;



    @FXML
    private Button addSubbtn;

    @FXML
    private Button addgym;

    @FXML
    private Label addsubtitle;

    @FXML
    private TableColumn<?, ?> clientnumberColumn;

    @FXML
    private TextField clnField;

    @FXML
    private TableColumn<?, ?> coachesnumberColumn;

    @FXML
    TableColumn<gym,Void> actionsColumn;

    @FXML
    private TextField conField;

    @FXML
    private TableColumn<?, ?> idColumn;

    @FXML
    private TableColumn<?, ?> nomColumn;

    @FXML
    private TextField nomField;

    @FXML
    private Button productSearchBtn;

    private static GymController instance =new GymController();
    public static GymController getInstance() {
        return instance;
    }

    services.notifService notifService = new notifService();
    AccountService accountService = new AccountService();


    private Account currentAccount;


    private int SelectedGym;

    gymService gymService =new gymService();

    ObservableList<gym> gyms;
    {
        try {
            gyms = gymService.afficher();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void GoToAccountAbonnementPagee(MouseEvent event) {

    }

    @FXML
    void GoToEditGymPaage() {
        GymPage.setVisible(false);
        GymPage.setManaged(false);
        AddGymPage.setVisible(true);
        AddGymPage.setVisible(true);
        EditSubBtn.setVisible(true);
        EditSubBtn.setManaged(true);
        addgym.setVisible(false);
        addgym.setManaged(false);
    }

    @FXML
    void GoToAddGymPaage() {
        GymPage.setVisible(false);
        GymPage.setManaged(false);
        AddGymPage.setVisible(true);
        AddGymPage.setVisible(true);
        EditSubBtn.setVisible(false);
        EditSubBtn.setManaged(false);
        addgym.setVisible(true);
        addgym.setManaged(true);
    }

    @FXML
    void GoToGympage(ActionEvent event) {
        GymPage.setVisible(true);
        GymPage.setManaged(true);
        AddGymPage.setVisible(false);
        AddGymPage.setVisible(false);
    }



    @FXML
    void handleAddGym(ActionEvent event) {

        String nom = nomField.getText();
        String clnText = clnField.getText();
        String conText = conField.getText();

        // Validate if the input fields are not empty
        if (nom.isEmpty() || clnText.isEmpty() || conText.isEmpty()) {
            showAlert("Please fill in all the fields", AlertType.WARNING);
        } else {
            try {
                int clientnbr = Integer.parseInt(clnText);
                int coachnbr = Integer.parseInt(conText);

                if (clientnbr < 0 || coachnbr < 0) {
                    showAlert("Client number and Coach number must be non-negative", AlertType.WARNING);
                } else {
                    gym g = new gym(-1,0, nom, clientnbr, coachnbr);
                    gymService gymService = new gymService();

                    try {
                        gymService.ajouter(g);
                        showAlert("Gym added successfully", AlertType.INFORMATION);
                    } catch (SQLException e) {
                        showAlert("Error adding gym: " + e.getMessage(), AlertType.ERROR);
                    }

                    notif n = new notif(-1,currentAccount.getNom(),currentAccount.getNom() +" add a new gym  ","admin");
                    try {
                        notifService.ajouter(n);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    // Uncomment the following lines if needed
                    // initAbonnementInputs();
                    // reload_page();
                }
            } catch (NumberFormatException e) {
                showAlert("Please enter valid numbers for Client number and Coach number", AlertType.ERROR);
            }
        }
    }

    private void showAlert(String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML

    void handleEditGym(ActionEvent event) {
        String nom = nomField.getText();

        try {
            int clientnbr = Integer.parseInt(clnField.getText());
            int coachnbr = Integer.parseInt(conField.getText());

            gym g = new gym(instance.SelectedGym, nom, clientnbr, coachnbr);
            gymService gymService = new gymService();

            try {
                gymService.modifier(g);
                showAlert("Gym edited successfully", AlertType.INFORMATION);
            } catch (SQLException e) {
                showAlert("Error editing gym: " + e.getMessage(), AlertType.ERROR);
            }
            notif n = new notif(-1,currentAccount.getNom(),currentAccount.getNom() +" modified a new gym  ","admin");
            try {
                notifService.ajouter(n);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Uncomment the following lines if needed
            // initAbonnementInputs();
            // reload_page();
        } catch (NumberFormatException e) {
            showAlert("Please enter valid numbers for Client number and Coach number", AlertType.ERROR);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        clientnumberColumn.setCellValueFactory(new PropertyValueFactory<>("client_number"));
        coachesnumberColumn.setCellValueFactory(new PropertyValueFactory<>("coach_number"));
        actionsColumn.setCellFactory(createButtonCellFactory());
        abonnementTableView.setItems(gyms);
        try {
            currentAccount = accountService.getAccountByAccountId(Account.getCurrentid());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



    public Callback<TableColumn<gym, Void>, TableCell<gym, Void>> createButtonCellFactory() {
        return new Callback<TableColumn<gym, Void>, TableCell<gym, Void>>() {
            @Override
            public TableCell<gym, Void> call(final TableColumn<gym, Void> param) {
                return new TableCell<gym, Void>() {

                    final Button deleteButton = createButton("Delete");
                    final Button editButton = createButton("Edit");

                    {
                        // Set actions for the buttons
                        deleteButton.setOnAction(event -> {
                            gym g = getTableView().getItems().get(getIndex());
                            System.out.println("Delete: " + g.getId());
                            try {
                                gymService.supprimer(g.getId());
                                notif n = new notif(-1,currentAccount.getNom(),currentAccount.getNom() +" deleted a gym  ","admin");
                                try {
                                    notifService.ajouter(n);
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                // reload_page();

                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }



                            // Add your delete action here
                        });

                        editButton.setOnAction(event -> {
                            gym g = getTableView().getItems().get(getIndex());
                            System.out.println("Edit: " + g.getId());
                            GoToEditGymPaage();
                            instance.SelectedGym=g.getId();
                            fillSubinputs(g);
                            // Add your edit action here
                        });

                    }

                    private void fillSubinputs(gym g) {
                        nomField.setText(g.getNom());
                        clnField.setText(String.valueOf(g.getClient_number()));

                        conField.setText(String.valueOf(g.getCoach_number()));
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
}
