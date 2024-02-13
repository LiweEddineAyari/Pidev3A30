package main.projet;

import entity.Abonnement;
import entity.Category;
import entity.Coach;
import entity.Planning;
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

public class AbonnementController implements Initializable {

    //table view Abonnement

    @FXML
    TableView<Abonnement> abonnementTableView;
    @FXML
    TableColumn<?, ?> idColumn;
    @FXML
    TableColumn<?, ?> iduserColumn;
    @FXML
    TableColumn<?, ?> idcategoryColumn;
    @FXML
    TableColumn<?, ?> nomColumn;
    @FXML
    TableColumn<?, ?> dureeColumn;
    @FXML
    TableColumn<?, ?> prixColumn;
    @FXML
    TableColumn<?, ?> fideliteColumn;
    @FXML
    TableColumn<Abonnement,Void> actionsColumn;


    //interfaces
    @FXML
    VBox AbonnementsPage;
    @FXML
    Pane CategoryPage,AddCategoryPage;



    //table view Abonnement

    @FXML
    TableView<Category> CategoryTableView;
    @FXML
    TableColumn<?, ?> idColumncategory;
    @FXML
    TableColumn<?, ?> nomColumncategory;
    @FXML
    TableColumn<Category,Void> actionsColumncategory;



     ObservableList<Abonnement> Abonnements= FXCollections.observableArrayList(
            new Abonnement(1, 1, 101, "Gold Membership", 3, 49.99f, true),
            new Abonnement(2, 2, 102, "Silver Membership", 6, 29.99f, false),
            new Abonnement(3, 3, 103, "Bronze Membership", 12, 19.99f, true),
            new Abonnement(4, 4, 104, "Platinum Membership", 1, 99.99f, false),
            new Abonnement(5, 5, 105, "Basic Membership", 24, 9.99f, true)
    );

    ObservableList<Category> categories= FXCollections.observableArrayList(
            new Category(1, "Fitness"),
            new Category(2, "Cardio"),
            new Category(3, "Weightlifting")
    );





// navigation Abonnement
    @FXML
    public void GoToCategoriesPage(){
        AbonnementsPage.setVisible(false);
        AbonnementsPage.setManaged(false);

        AddCategoryPage.setVisible(false);
        AddCategoryPage.setManaged(false);

        CategoryPage.setVisible(true);
        CategoryPage.setManaged(true);


    }


     @FXML
     public  void GoToAddCategoryPaage(){
         CategoryPage.setVisible(false);
         CategoryPage.setManaged(false);

         AbonnementsPage.setVisible(false);
         AbonnementsPage.setManaged(false);

         AddCategoryPage.setVisible(true);
         AddCategoryPage.setManaged(true);

     }








    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //initialisation de tableview abonnement
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        iduserColumn.setCellValueFactory(new PropertyValueFactory<>("iduser"));
        idcategoryColumn.setCellValueFactory(new PropertyValueFactory<>("idcategory"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        dureeColumn.setCellValueFactory(new PropertyValueFactory<>("duree"));
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
        fideliteColumn.setCellValueFactory(new PropertyValueFactory<>("fidelite"));
        actionsColumn.setCellFactory(createButtonCellFactory());
        abonnementTableView.setItems(Abonnements);


        //initialisation de tableview category
        idColumncategory.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumncategory.setCellValueFactory(new PropertyValueFactory<>("nom"));
        actionsColumncategory.setCellFactory(createButtonCellFactoryCategory());
        CategoryTableView.setItems(categories);



    }





    public Callback<TableColumn<Abonnement, Void>, TableCell<Abonnement, Void>> createButtonCellFactory() {
        return new Callback<TableColumn<Abonnement, Void>, TableCell<Abonnement, Void>>() {
            @Override
            public TableCell<Abonnement, Void> call(final TableColumn<Abonnement, Void> param) {
                return new TableCell<Abonnement, Void>() {

                    final Button deleteButton = createButton("Delete");
                    final Button editButton = createButton("Edit");

                    {
                        // Set actions for the buttons
                        deleteButton.setOnAction(event -> {
                            Abonnement abonnement = getTableView().getItems().get(getIndex());
                            System.out.println("Delete: " + abonnement.getId());
                            // Add your delete action here
                        });

                        editButton.setOnAction(event -> {
                            Abonnement abonnement = getTableView().getItems().get(getIndex());
                            System.out.println("Edit: " + abonnement.getId());
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


    public Callback<TableColumn<Category, Void>, TableCell<Category, Void>> createButtonCellFactoryCategory() {
        return new Callback<TableColumn<Category, Void>, TableCell<Category, Void>>() {
            @Override
            public TableCell<Category, Void> call(final TableColumn<Category, Void> param) {
                return new TableCell<Category, Void>() {

                    final Button deleteButton = createButton("Delete");
                    final Button editButton = createButton("Edit");

                    {
                        // Set actions for the buttons
                        deleteButton.setOnAction(event -> {
                            Category category = getTableView().getItems().get(getIndex());
                            System.out.println("Delete: " + category.getId());
                            // Add your delete action here

                        });

                        editButton.setOnAction(event -> {
                            Category category = getTableView().getItems().get(getIndex());
                            System.out.println("Edit: " + category.getId());
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
