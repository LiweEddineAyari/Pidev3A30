package main.projet;

import entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class PaimentController implements Initializable {


    //interfaces
    @FXML
    VBox Paimentaffichage,Commandeaffichage;
    @FXML
    Pane EditCommandePage,EditPaimentPage;



    //Paiment Table view
    @FXML
    TableView<Paiment> paimentTableView;
    @FXML
    TableColumn<?, ?> idColumn;
    @FXML
    TableColumn<?, ?> iduserColumn;
    @FXML
    TableColumn<?, ?> montantColumn;
    @FXML
    TableColumn<?, ?> cartnameColumn;
    @FXML
    TableColumn<?, ?> cartecodeColumn;
    @FXML
    TableColumn<Paiment,Void> actionsColumn;

    //commande Table view
    @FXML
    TableView<Commande> commandeTableView;
    @FXML
    TableColumn<?, ?> idcommandeColumn;
    @FXML
    TableColumn<?, ?> iduserColumnC;
    @FXML
    TableColumn<?, ?> idpanierColumn;
    @FXML
    TableColumn<?, ?> montantColumnC;
    @FXML
    TableColumn<?, ?> statutColumn;
    @FXML
    TableColumn<Commande,Void> actionsColumnC;

    ObservableList<Paiment> paiments= FXCollections.observableArrayList(
            new Paiment(1, 101, 50.0f, "Credit Card", "1234-5678-9012-3456"),
            new Paiment(2, 102, 30.5f, "PayPal", "1234-5678-9012-3456"),
            new Paiment(3, 103, 75.25f, "Bank Transfer", "1234-5678-9012-3456"),
            new Paiment(4, 104, 20.0f, "Cash", "1234-5678-9012-3456"),
            new Paiment(5, 105, 42.8f, "Credit Card", "1234-5678-9012-3456")
    );
    ObservableList<Commande> commandes= FXCollections.observableArrayList(
             new Commande(1, 101, 201, 75.0f, "Pending"),
             new Commande(2, 102, 202, 120.5f, "Processing"),
             new Commande(3, 103, 203, 50.75f, "Shipped"),
             new Commande(4, 104, 204, 90.0f, "Delivered"),
             new Commande(5, 105, 205, 30.8f, "Cancelled")
    );





// navigation methods

    @FXML
    void paimentinterfaceload(){

        EditPaimentPage.setVisible(false);
        EditPaimentPage.setManaged(false);
        Commandeaffichage.setVisible(false);
        Commandeaffichage.setManaged(false);
        EditCommandePage.setVisible(false);
        EditCommandePage.setManaged(false);

        Paimentaffichage.setVisible(true);
        Paimentaffichage.setManaged(true);
    }



    @FXML
    void commandeinterfaceload(){
        Paimentaffichage.setVisible(false);
        Paimentaffichage.setManaged(false);
        EditCommandePage.setVisible(false);
        EditCommandePage.setManaged(false);
        EditPaimentPage.setVisible(false);
        EditPaimentPage.setManaged(false);

        Commandeaffichage.setVisible(true);
        Commandeaffichage.setManaged(true);
    }

    @FXML
    void editpaimentInerfaceLoad(){
        Paimentaffichage.setVisible(false);
        Paimentaffichage.setManaged(false);
        EditCommandePage.setVisible(false);
        EditCommandePage.setManaged(false);
        Commandeaffichage.setVisible(false);
        Commandeaffichage.setManaged(false);

        EditPaimentPage.setVisible(true);
        EditPaimentPage.setManaged(true);
    }

    @FXML
    void editcommandeInerfaceLoad(){
        Paimentaffichage.setVisible(false);
        Paimentaffichage.setManaged(false);
        EditPaimentPage.setVisible(false);
        EditPaimentPage.setManaged(false);
        Commandeaffichage.setVisible(false);
        Commandeaffichage.setManaged(false);

        EditCommandePage.setVisible(true);
        EditCommandePage.setManaged(true);
    }








    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //initialisation paiment table
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        iduserColumn.setCellValueFactory(new PropertyValueFactory<>("iduser"));
        montantColumn.setCellValueFactory(new PropertyValueFactory<>("montant"));
        cartnameColumn.setCellValueFactory(new PropertyValueFactory<>("cartname"));
        cartecodeColumn.setCellValueFactory(new PropertyValueFactory<>("CartCode"));
        actionsColumn.setCellFactory(createButtonCellFactory());
        paimentTableView.setItems(paiments);

        //initialisation paiment table
        idcommandeColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        iduserColumnC.setCellValueFactory(new PropertyValueFactory<>("iduser"));
        idpanierColumn.setCellValueFactory(new PropertyValueFactory<>("idpanier"));
        montantColumnC.setCellValueFactory(new PropertyValueFactory<>("montant"));
        statutColumn.setCellValueFactory(new PropertyValueFactory<>("statut"));
        actionsColumnC.setCellFactory(createButtonCellFactoryCommande());
        commandeTableView.setItems(commandes);
    }

    Callback<TableColumn<Paiment, Void>, TableCell<Paiment, Void>> createButtonCellFactory() {
        return new Callback<TableColumn<Paiment, Void>, TableCell<Paiment, Void>>() {
            @Override
            public TableCell<Paiment, Void> call(final TableColumn<Paiment, Void> param) {
                return new TableCell<Paiment, Void>() {

                    final Button deleteButton = createButton("Delete");
                    final Button editButton = createButton("Edit");

                    {
                        // Set actions for the buttons
                        deleteButton.setOnAction(event -> {
                            Paiment paiment = getTableView().getItems().get(getIndex());
                            System.out.println("Delete: " + paiment.getId());
                            // Add your delete action here
                        });

                        editButton.setOnAction(event -> {
                            Paiment paiment = getTableView().getItems().get(getIndex());
                            System.out.println("Edit: " + paiment.getId());
                            editpaimentInerfaceLoad();
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


    Callback<TableColumn<Commande, Void>, TableCell<Commande, Void>> createButtonCellFactoryCommande() {
        return new Callback<TableColumn<Commande, Void>, TableCell<Commande, Void>>() {
            @Override
            public TableCell<Commande, Void> call(final TableColumn<Commande, Void> param) {
                return new TableCell<Commande, Void>() {

                    final Button deleteButton = createButton("Delete");
                    final Button editButton = createButton("Edit");
                    final Button PanierDetails = createButton("PanierDetails");

                    {
                        // Set actions for the buttons
                        deleteButton.setOnAction(event -> {
                            Commande commande = getTableView().getItems().get(getIndex());
                            System.out.println("Delete: " + commande.getId());
                            // Add your delete action here
                        });

                        editButton.setOnAction(event -> {
                            Commande commande = getTableView().getItems().get(getIndex());
                            System.out.println("Edit: " + commande.getId());
                            editcommandeInerfaceLoad();
                            // Add your edit action here
                        });

                        PanierDetails.setOnAction(event -> {
                            Commande commande = getTableView().getItems().get(getIndex());
                            System.out.println("PanierDetails: " + commande.getId());
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
                        buttonPane.getChildren().addAll(deleteButton, editButton,PanierDetails);
                        return buttonPane;
                    }
                };
            }
        };
    }


}
