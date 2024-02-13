package main.projet;

import entity.Avis;
import entity.Evennement;
import entity.Participants;
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

public class EvennementController implements Initializable {


    //interfaces
    @FXML
    VBox EventsInterface,ParticipantsPage,AvisPage;
    @FXML
    Pane AddEventPage;

    // table view Evennement
    @FXML
    TableView<Evennement>evennementTableView;
    @FXML
    TableColumn<?, ?> idColumn;
    @FXML
    TableColumn<?, ?> nomColumn;
    @FXML
    TableColumn<?, ?> dateColumn;
    @FXML
    TableColumn<?, ?> typeColumn;
    @FXML
    TableColumn<?, ?> descriptionColumn;
    @FXML
    TableColumn<Evennement,Void> actionsColumn;

    // table view Participants
    @FXML
    TableView<Participants>participantsTableView;
    @FXML
    TableColumn<?, ?> idColumnParticipants;
    @FXML
    TableColumn<?, ?> ideventcolumnn;
    @FXML
    TableColumn<?, ?> iduserrColumn;
    @FXML
    TableColumn<Participants,Void>  actionsColumnP   ;

    // table view avis
    @FXML
    TableView<Avis> AvisTableView;
    @FXML
    TableColumn<?, ?> idAvisColumn;
    @FXML
    TableColumn<?, ?> ideventcolumnnA;
    @FXML
    TableColumn<?, ?> iduserrColumnA;
    @FXML
    TableColumn<?, ?> idcommentaireColumn;
    @FXML
    TableColumn<Avis,Void>  actionsColumnA   ;

    ObservableList<Avis> aviss= FXCollections.observableArrayList(
            new Avis(1, 1, 101, "Great event!"),
            new Avis(2, 1, 102, "Could be better."),
            new Avis(3, 2, 101, "Amazing experience!"),
            new Avis(4, 3, 103, "Not satisfied."),
            new Avis(5, 2, 102, "Well organized.")
    );

    ObservableList<Participants> participants= FXCollections.observableArrayList(
            new Participants(1, 1, 101),
            new Participants(2, 1, 102),
            new Participants(3, 2, 101),
            new Participants(4, 3, 103),
            new Participants(5, 2, 102)
    );

    ObservableList<Evennement> evennements= FXCollections.observableArrayList(
            new Evennement(1, "Event 1", "2024-02-01", "Type 1", "Description 1"),
            new Evennement(2, "Event 2", "2024-02-15", "Type 2", "Description 2"),
            new Evennement(3, "Event 3", "2024-03-05", "Type 1", "Description 3"),
            new Evennement(4, "Event 4", "2024-03-20", "Type 3", "Description 4"),
            new Evennement(5, "Event 5", "2024-04-10", "Type 2", "Description 5")
    );




    //navigation method
    @FXML
    public void GoToAddEventPagee(){
        EventsInterface.setVisible(false);
        EventsInterface.setManaged(false);


        AddEventPage.setVisible(true);
        AddEventPage.setManaged(true);
    }


    @FXML
    public  void goToParticipantPage(){
        EventsInterface.setVisible(false);
        EventsInterface.setManaged(false);
        AvisPage.setVisible(false);
        AvisPage.setManaged(false);
        AddEventPage.setVisible(false);
        AddEventPage.setManaged(false);
        ParticipantsPage.setVisible(true);
        ParticipantsPage.setManaged(true);
    }

    @FXML
    public  void goToAvisPage(){
        EventsInterface.setVisible(false);
        EventsInterface.setManaged(false);
        AddEventPage.setVisible(false);
        AddEventPage.setManaged(false);
        ParticipantsPage.setVisible(false);
        ParticipantsPage.setManaged(false);

        AvisPage.setVisible(true);
        AvisPage.setManaged(true);
    }


    @FXML
    public void GoToEventPagee(){
        AddEventPage.setVisible(false);
        AddEventPage.setManaged(false);

        EventsInterface.setVisible(true);
        EventsInterface.setManaged(true);


    }








    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //intialisation table view evennement
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        actionsColumn.setCellFactory(createButtonCellFactory());
        evennementTableView.setItems(evennements);

        //intialisation table view participants

        idColumnParticipants.setCellValueFactory(new PropertyValueFactory<>("id"));
        ideventcolumnn.setCellValueFactory(new PropertyValueFactory<>("idevennement"));
        iduserrColumn.setCellValueFactory(new PropertyValueFactory<>("iduser"));
        actionsColumnP.setCellFactory(createButtonCellFactoryParticipants());
        participantsTableView.setItems(participants);

        //intialisation table view avis

        idAvisColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        ideventcolumnnA.setCellValueFactory(new PropertyValueFactory<>("idevennement"));
        iduserrColumnA.setCellValueFactory(new PropertyValueFactory<>("iduser"));
        actionsColumnA.setCellFactory(createButtonCellFactoryAvis());
        AvisTableView.setItems(aviss);
    }


    Callback<TableColumn<Evennement, Void>, TableCell<Evennement, Void>> createButtonCellFactory() {
        return new Callback<TableColumn<Evennement, Void>, TableCell<Evennement, Void>>() {
            @Override
            public TableCell<Evennement, Void> call(final TableColumn<Evennement, Void> param) {
                return new TableCell<Evennement, Void>() {

                    final Button deleteButton = createButton("Delete");
                    final Button editButton = createButton("Edit");

                    {
                        // Set actions for the buttons
                        deleteButton.setOnAction(event -> {
                            Evennement evennement = getTableView().getItems().get(getIndex());
                            System.out.println("Delete: " + evennement.getId());
                            // Add your delete action here
                        });

                        editButton.setOnAction(event -> {
                            Evennement evennement = getTableView().getItems().get(getIndex());
                            System.out.println("Edit: " + evennement.getId());
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



    Callback<TableColumn<Participants, Void>, TableCell<Participants, Void>> createButtonCellFactoryParticipants() {
        return new Callback<TableColumn<Participants, Void>, TableCell<Participants, Void>>() {
            @Override
            public TableCell<Participants, Void> call(final TableColumn<Participants, Void> param) {
                return new TableCell<Participants, Void>() {

                    final Button deleteButton = createButton("Delete");

                    {
                        // Set actions for the buttons
                        deleteButton.setOnAction(event -> {
                            Participants participant = getTableView().getItems().get(getIndex());
                            System.out.println("Delete: " + participant.getId());
                            // Add your delete action here
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
                        buttonPane.getChildren().addAll(deleteButton);
                        return buttonPane;
                    }
                };
            }
        };
    }

    Callback<TableColumn<Avis, Void>, TableCell<Avis, Void>> createButtonCellFactoryAvis() {
        return new Callback<TableColumn<Avis, Void>, TableCell<Avis, Void>>() {
            @Override
            public TableCell<Avis, Void> call(final TableColumn<Avis, Void> param) {
                return new TableCell<Avis, Void>() {

                    final Button deleteButton = createButton("Delete");

                    {
                        // Set actions for the buttons
                        deleteButton.setOnAction(event -> {
                            Avis avis1 = getTableView().getItems().get(getIndex());
                            System.out.println("Delete: " + avis1.getId());
                            // Add your delete action here
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
                        buttonPane.getChildren().addAll(deleteButton);
                        return buttonPane;
                    }
                };
            }
        };
    }




}
