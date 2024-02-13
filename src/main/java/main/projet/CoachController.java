package main.projet;

import entity.Coach;
import entity.Exercice;
import entity.Planning;
import entity.Product;
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

public class CoachController implements Initializable {

     //coach interfaces
    @FXML
      VBox Coachaffichage,Planningaffichage;
    @FXML
      Pane AddCoachtPage,AddPlanningtPage;


    //table view coach
    @FXML
    TableView<Coach> coachTableView;
    @FXML
    TableColumn<?, ?> idColumn;
    @FXML
    TableColumn<?, ?> nomColumn;
    @FXML
    TableColumn<?, ?> prenomColumn;
    @FXML
    TableColumn<?, ?> ageColumn;
    @FXML
    TableColumn<?, ?> mailColumn;
    @FXML
    TableColumn<?, ?> passwordColumn;
    @FXML
    TableColumn<Coach,Void> actionsColumn;

    //table view planning
    @FXML
    TableView<Planning> planningTableView;
    @FXML
    TableColumn<?, ?> idplaningColumn;
    @FXML
    TableColumn<?, ?> idcoachColumn;
    @FXML
    TableColumn<?, ?> dateColumn;
    @FXML
    TableColumn<?, ?> heure_debutColumn;
    @FXML
    TableColumn<?, ?> heure_finColumn;
    @FXML
    TableColumn<Planning,Void> actionsColumnplanning;


    ObservableList<Coach> coaches= FXCollections.observableArrayList(
            new Coach(1, "Smith", "John", 35, "john.smith@email.com", 123456),
            new Coach(2, "Doe", "Jane", 28, "jane.doe@email.com", 654321),
            new Coach(3, "Williams", "Tom", 42, "tom.williams@email.com", 987654),
            new Coach(4, "Johnson", "Alice", 30, "alice.johnson@email.com", 111222),
            new Coach(5, "Brown", "Chris", 50, "chris.brown@email.com", 333444)
    );


    ObservableList<Planning> plannings= FXCollections.observableArrayList(
            new Planning(1, 1, "2024-02-01", "10:00 AM", "11:30 AM"),
            new Planning(2, 2, "2024-02-02", "02:00 PM", "03:30 PM"),
            new Planning(3, 3, "2024-02-03", "08:30 AM", "10:00 AM"),
            new Planning(4, 4, "2024-02-04", "04:00 PM", "05:30 PM"),
            new Planning(5, 5, "2024-02-05", "11:00 AM", "12:30 PM")
    );



    //coach navigation methods
    @FXML
    public void addCoachInterface(){
        //hide interfaces
        Coachaffichage.setVisible(false);
        Coachaffichage.setManaged(false);
        AddPlanningtPage.setVisible(false);
        AddPlanningtPage.setManaged(false);


        //show addCoachPage interface

        AddCoachtPage.setVisible(true);
        AddCoachtPage.setManaged(true);
    }

    @FXML
    public void GoToCoachAffichagePage(){
        //hide interfaces
        AddCoachtPage.setVisible(false);
        AddCoachtPage.setManaged(false);
        AddPlanningtPage.setVisible(false);
        AddPlanningtPage.setManaged(false);

        //show Coachaffichage interface

        Coachaffichage.setVisible(true);
        AddCoachtPage.setManaged(true);
    }



//planing navigation
    @FXML
    public  void GoToPlanning(){
        //hide interfaces
        Coachaffichage.setVisible(false);
        Coachaffichage.setManaged(false);
        AddCoachtPage.setVisible(false);
        AddCoachtPage.setManaged(false);
        AddPlanningtPage.setVisible(false);
        AddPlanningtPage.setManaged(false);
        //show addCoachPage interface

        Planningaffichage.setVisible(true);
        Planningaffichage.setManaged(true);
    }


@FXML
public void addPlanningInterface(){
    Coachaffichage.setVisible(false);
    Coachaffichage.setManaged(false);
    AddCoachtPage.setVisible(false);
    AddCoachtPage.setManaged(false);
    Planningaffichage.setVisible(false);
    Planningaffichage.setManaged(false);
    //show addCoachPage interface
    AddPlanningtPage.setVisible(true);
    AddPlanningtPage.setManaged(true);

}
















    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //initialisation de tableview coach
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        mailColumn.setCellValueFactory(new PropertyValueFactory<>("mail"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        actionsColumn.setCellFactory(createButtonCellFactory());
        coachTableView.setItems(coaches);

        //initialisation de tableview planning
        idplaningColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idcoachColumn.setCellValueFactory(new PropertyValueFactory<>("id_coach"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        heure_debutColumn.setCellValueFactory(new PropertyValueFactory<>("heure_debut"));
        heure_finColumn.setCellValueFactory(new PropertyValueFactory<>("heure_fin"));
        actionsColumnplanning.setCellFactory(createButtonCellFactoryPlanning());
        planningTableView.setItems(plannings);


    }

    //action column for product table
    public Callback<TableColumn<Coach, Void>, TableCell<Coach, Void>> createButtonCellFactory() {
        return new Callback<TableColumn<Coach, Void>, TableCell<Coach, Void>>() {
            @Override
            public TableCell<Coach, Void> call(final TableColumn<Coach, Void> param) {
                return new TableCell<Coach, Void>() {

                    final Button deleteButton = createButton("Delete");
                    final Button editButton = createButton("Edit");

                    {
                        // Set actions for the buttons
                        deleteButton.setOnAction(event -> {
                            Coach coach = getTableView().getItems().get(getIndex());
                            System.out.println("Delete: " + coach.getId());
                            // Add your delete action here
                        });

                        editButton.setOnAction(event -> {
                            Coach coach = getTableView().getItems().get(getIndex());
                            System.out.println("Edit: " + coach.getId());
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






    //action column for planing table
     Callback<TableColumn<Planning, Void>, TableCell<Planning, Void>> createButtonCellFactoryPlanning() {
        return new Callback<TableColumn<Planning, Void>, TableCell<Planning, Void>>() {
            @Override
            public TableCell<Planning, Void> call(final TableColumn<Planning, Void> param) {
                return new TableCell<Planning, Void>() {

                    final Button deleteButton = createButton("Delete");
                    final Button editButton = createButton("Edit");

                    {
                        // Set actions for the buttons
                        deleteButton.setOnAction(event -> {
                            Planning planning = getTableView().getItems().get(getIndex());
                            System.out.println("Delete: " + planning.getId());
                            // Add your delete action here
                        });

                        editButton.setOnAction(event -> {
                            Planning planning = getTableView().getItems().get(getIndex());
                            System.out.println("Edit: " + planning.getId());
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


}





