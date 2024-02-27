package main.projet;

import entity.Abonnement;
import entity.Account;
import entity.Category;
import entity.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import services.AbonnementService;
import services.categoryService;

import services.AccountService;
import services.ProductService;

import java.lang.reflect.Field;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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
    VBox AbonnementsPage,CategoryPage;
    @FXML
    Pane AddAbonnementPagee,AddCategoryPage;



    //table view Abonnement

    @FXML
    TableView<Category> CategoryTableView;
    @FXML
    TableColumn<?, ?> idColumncategory;
    @FXML
    TableColumn<?, ?> nomColumncategory;
    @FXML
    TableColumn<Category,Void> actionsColumncategory;


    AbonnementService abonnementService =new AbonnementService();
    categoryService categoryService = new categoryService();


    ObservableList<Abonnement> abonnements;
    {
        try {
            abonnements = abonnementService.afficher();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }





    ObservableList<Category> categories;
    {
        try {
            categories = categoryService.afficher();
            System.out.println(categories);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }





// navigation Abonnement
@FXML
public void GoToabonnementPage(){
    AbonnementsPage.setVisible(true);
    AbonnementsPage.setManaged(true);

    AddCategoryPage.setVisible(false);
    AddCategoryPage.setManaged(false);

    CategoryPage.setVisible(false);
    CategoryPage.setManaged(false);

    AddAbonnementPagee.setVisible(false);
    AddAbonnementPagee.setManaged(false);


}


    @FXML
    public void GoToCategoriesPage(){
        AbonnementsPage.setVisible(false);
        AbonnementsPage.setManaged(false);

        AddCategoryPage.setVisible(false);
        AddCategoryPage.setManaged(false);

        CategoryPage.setVisible(true);
        CategoryPage.setManaged(true);

        AddAbonnementPagee.setVisible(false);
        AddAbonnementPagee.setManaged(false);




    }


     @FXML
     public  void GoToAddCategoryPaage(){
         addcattitle.setText("Add cat page");
         AddcattBtn.setVisible(true);
         AddcattBtn.setManaged(true);

         EditCatBtn1.setVisible(false);
         EditCatBtn1.setManaged(false);


         CategoryPage.setVisible(false);
         CategoryPage.setManaged(false);

         AbonnementsPage.setVisible(false);
         AbonnementsPage.setManaged(false);

         AddCategoryPage.setVisible(true);
         AddCategoryPage.setManaged(true);

         AddAbonnementPagee.setVisible(false);
         AddAbonnementPagee.setManaged(false);



     }

     @FXML
     Button addSubbtn,EditSubBtn,EditCatBtn1,AddcattBtn;
      @FXML
      Label addsubtitle,addcattitle;

    @FXML
    public  void GoToAddabonnementPaage(){
        addsubtitle.setText("add sub page");
        addSubbtn.setVisible(true);
        addSubbtn.setManaged(true);
        EditSubBtn.setVisible(false);
        EditSubBtn.setManaged(false);


        CategoryPage.setVisible(false);
        CategoryPage.setManaged(false);

        AbonnementsPage.setVisible(false);
        AbonnementsPage.setManaged(false);

        AddCategoryPage.setVisible(false);
        AddCategoryPage.setManaged(false);

        AddAbonnementPagee.setVisible(true);
        AddAbonnementPagee.setManaged(true);

    }

    public void showEditAbonnementbtn(){
        addsubtitle.setText("Edit sub page");
        addSubbtn.setVisible(false);
        addSubbtn.setManaged(false);

        EditSubBtn.setVisible(true);
        EditSubBtn.setManaged(true);

        AbonnementsPage.setVisible(false);
        AbonnementsPage.setManaged(false);

        CategoryPage.setVisible(false);
        CategoryPage.setManaged(false);

        AddCategoryPage.setVisible(false);
        AddCategoryPage.setManaged(false);

        AddAbonnementPagee.setVisible(true);
        AddAbonnementPagee.setManaged(true);

    }

    public void showEditcatbtn(){
        addcattitle.setText("Edit cat page");
        AddcattBtn.setVisible(false);
        AddcattBtn.setManaged(false);

        EditCatBtn1.setVisible(true);
        EditCatBtn1.setManaged(true);

        AbonnementsPage.setVisible(false);
        AbonnementsPage.setManaged(false);

        CategoryPage.setVisible(false);
        CategoryPage.setManaged(false);


        AddCategoryPage.setVisible(true);
        AddCategoryPage.setManaged(true);

        AddAbonnementPagee.setVisible(false);
        AddAbonnementPagee.setManaged(false);

    }






    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //initialisation de tableview abonnement
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idcategoryColumn.setCellValueFactory(new PropertyValueFactory<>("idcategory"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        dureeColumn.setCellValueFactory(new PropertyValueFactory<>("duree"));
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
        fideliteColumn.setCellValueFactory(new PropertyValueFactory<>("fidelite"));
        actionsColumn.setCellFactory(createButtonCellFactory());
        abonnementTableView.setItems(abonnements);


        //initialisation de tableview category
        idColumncategory.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumncategory.setCellValueFactory(new PropertyValueFactory<>("nom"));
        actionsColumncategory.setCellFactory(createButtonCellFactoryCategory());
        CategoryTableView.setItems(categories);



    }


    private static AbonnementController instance =new AbonnementController();
    public static AbonnementController getInstance() {
        return instance;
    }


    private int SelectedSub;


    private int Selectedcategory;




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
                            try {
                                abonnementService.supprimer(abonnement.getId());
                               // reload_page();

                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }

                            reload_page();

                            // Add your delete action here
                        });

                        editButton.setOnAction(event -> {
                            Abonnement abonnement = getTableView().getItems().get(getIndex());
                            System.out.println("Edit: " + abonnement.getId());
                            showEditAbonnementbtn();
                            instance.SelectedSub=abonnement.getId();
                            fillSubinputs(abonnement);
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
                            try {
                                categoryService.supprimer(category.getId());
                                // reload_page();

                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            reload_pageC();
                        });

                        editButton.setOnAction(event -> {
                            Category category = getTableView().getItems().get(getIndex());
                            System.out.println("Edit: " + category.getId());

                            showEditcatbtn();
                            instance.Selectedcategory=category.getId();
                            fillcatinputs(category);
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


    @FXML
    TextField id_categoryField,nomField,dureeField,prixField;

    @FXML
    public void handleAddAbonnement(){

        int idcategory =Integer.parseInt(id_categoryField.getText());
        String nom =  nomField.getText();
        int duree = Integer.parseInt(dureeField.getText());
        float prix = Float.parseFloat(prixField.getText()) ;

        Abonnement abonnement = new Abonnement(-1, idcategory, nom, duree, prix,false);
        AbonnementService abonnementService =new AbonnementService();
        System.out.println(abonnement);

        try {
            abonnementService.ajouter(abonnement);
            System.out.println("check A handle add");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        initAbonnementInputs();
        reload_page();
    }

    @FXML
    public void handleAddCategory(){


        String nom = nomcField.getText();


        Category category = new Category(-1, nom);
        categoryService categoryService =new categoryService();

        try {
            categoryService.ajouter(category);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        initcategoryInputs();
        reload_pageC();
    }
    @FXML
   TextField nomcField;


    private void initcategoryInputs() {
        nomcField.setText("");
        idColumncategory.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumncategory.setCellValueFactory(new PropertyValueFactory<>("nom"));


    }

    @FXML
    public void handleEditAbonnement(){
        int idcategory =Integer.parseInt(id_categoryField.getText());
        String nom =  nomField.getText();
        int duree = Integer.parseInt(dureeField.getText());
        float prix = Float.parseFloat(prixField.getText()) ;
        boolean fidelite = true ;

        Abonnement abonnement = new Abonnement( instance.SelectedSub,  idcategory, nom, duree, prix,fidelite);
        AbonnementService abonnementService =new AbonnementService();
        System.out.println(abonnement);

        try {
            abonnementService.modifier(abonnement);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        initAbonnementInputs();
        reload_page();

    }



    @FXML
    public void handleEditCat(){

        String nom =  nomcField.getText();

        Category category = new Category( instance.Selectedcategory, nom);

        categoryService cat =new categoryService();

        try {
            System.out.println(category);
           cat.modifier(category);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        initcategoryInputs();
        reload_pageC();

    }

    void initAbonnementInputs(){
        id_categoryField.setText("");
        nomField.setText("");
        dureeField.setText("");
        prixField.setText("");



    }

    public void GoToAccountAbonnementPagee(MouseEvent mouseEvent) {
    }
    void fillSubinputs(Abonnement abonnement){
        id_categoryField.setText(String.valueOf(abonnement.getIdcategory()));
        nomField.setText(abonnement.getNom());
        dureeField.setText(String.valueOf(abonnement.getDuree()));
        prixField.setText(String.valueOf(abonnement.getPrix()));
       // prixField.setText(String.valueOf(abonnement.getPrix()));


    }
    void fillcatinputs(Category category){
        nomField.setText(category.getNom());


    }

    @FXML
    public void reload_page(){

        {
            try {
                abonnements = abonnementService.afficher();
                abonnementTableView.setItems(null);
                abonnementTableView.setItems(abonnements);
                abonnementTableView.refresh();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        GoToabonnementPage();

    }
    @FXML
    public void reload_pageC(){

        {
            try {
               categories = categoryService.afficher();
                CategoryTableView.setItems(null);
                CategoryTableView.setItems(categories);
                CategoryTableView.refresh();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        GoToCategoriesPage();

    }

}
