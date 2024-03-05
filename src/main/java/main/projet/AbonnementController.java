package main.projet;

import entity.Abonnement;
import entity.Account;
import entity.Category;
import entity.notif;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import services.*;

import java.lang.reflect.Field;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AbonnementController implements Initializable {

    //table view Abonnement
    private Account currentAccount; // added by firas used for notif

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
    Pane AddAbonnementPagee,AddCategoryPage,Satistics,Ban;
    services.notifService notifService = new notifService();
    AccountService accountService = new AccountService();
    AbonnementService abonnementService =new AbonnementService();

     @FXML
     ComboBox abonnementCombo,userCombo;




    //table view Abonnement

    @FXML
    TableView<Category> CategoryTableView;
    @FXML
    TableColumn<?, ?> idColumncategory;
    @FXML
    TableColumn<?, ?> nomColumncategory;
    @FXML
    TableColumn<Category,Void> actionsColumncategory;



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

    ObservableList<String> categoriesnames;




// navigation Abonnement


    @FXML
    public void banPage(){
        AbonnementsPage.setVisible(false);
        AbonnementsPage.setManaged(false);

        AddCategoryPage.setVisible(false);
        AddCategoryPage.setManaged(false);

        CategoryPage.setVisible(false);
        CategoryPage.setManaged(false);

        AddAbonnementPagee.setVisible(false);
        AddAbonnementPagee.setManaged(false);

        Satistics.setVisible(false);
        Satistics.setManaged(false);

        Ban.setVisible(true);
        Ban.setManaged(true);

    }
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

    Satistics.setVisible(false);
    Satistics.setManaged(false);

    Ban.setVisible(false);
    Ban.setManaged(false);


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

        Satistics.setVisible(false);
        Satistics.setManaged(false);

        Ban.setVisible(false);
        Ban.setManaged(false);


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

         Satistics.setVisible(false);
         Satistics.setManaged(false);
         Ban.setVisible(false);
         Ban.setManaged(false);


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
        Ban.setVisible(false);
        Ban.setManaged(false);

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
        Ban.setVisible(false);
        Ban.setManaged(false);
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
        Ban.setVisible(false);
        Ban.setManaged(false);

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

        try {
            currentAccount = accountService.getAccountByAccountId(Account.getCurrentid());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }





        try {
             categoriesnames = categoryService.GetCategoriesNames();
            // Set the obtained category names to the ComboBox
            abonnementComboBox.setItems(categoriesnames);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



        try {
            // Get user names and set as ComboBox items
            ObservableList<String> abonnementsNames = abonnements.stream()
                    .map(Abonnement::getNom)  // Assuming there is a getName() method in the Account class
                    .collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList));

            ObservableList<String> userNames = accountService.afficher().stream()
                    .filter(account -> account.getTitle().equals(Account.Title.user))
                    .map(Account::getNom)  // Assuming there is a getName() method in the Account class
                    .collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList));



            userCombo.setItems(userNames);
            abonnementCombo.setItems(abonnementsNames);
        } catch (SQLException e) {
            // Handle exception appropriately based on your application's error handling strategy
            e.printStackTrace();
        }


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
                                notif n = new notif(-1,currentAccount.getNom(),currentAccount.getNom() +" has deleted a category ","admin");
                                try {
                                    notifService.ajouter(n);
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
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
    TextField nomField,dureeField,prixField;
    @FXML
    ComboBox abonnementComboBox;


    @FXML
    public void handleAddAbonnement() throws SQLException {
        String selectedCategory = (String) abonnementComboBox.getValue();
        int idcategory=-1;

        if (controleDeSaisi()) {
            if(selectedCategory!=null){
                idcategory =categoryService.getCategoryIdByName(selectedCategory);
            }
            else {
                idcategory=-1;
            }
            String nom = nomField.getText();
            int duree = Integer.parseInt(dureeField.getText());
            float prix = Float.parseFloat(prixField.getText());

            Abonnement abonnement = new Abonnement(-1, idcategory, nom, duree, prix, false);
            AbonnementService abonnementService = new AbonnementService();
            System.out.println(abonnement);

            try {
                abonnementService.ajouter(abonnement);
                System.out.println("check A handle add");

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            // notif t3 firas ****************
            notif n = new notif(-1, currentAccount.getNom(), currentAccount.getNom() + " has added a sub ", "admin");
            try {
                notifService.ajouter(n);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
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
        // notif t3 firas ****************
        notif n = new notif(-1,currentAccount.getNom(),currentAccount.getNom() +" has added a category ","admin");
        try {
            notifService.ajouter(n);
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
    private boolean isCategoryComboBoxValid() {
        String selectedCategory = abonnementComboBox.getValue().toString();
        return selectedCategory != null && !selectedCategory.isEmpty();
    }
    private boolean controleDeSaisi() {
        // Check if category ID is a positive integer
        if (!isCategoryComboBoxValid()) {
            showAlert("Please select a valid category.");
            return false;
        }

        // Check if duree is a positive integer
        if (!isPositiveInteger(dureeField.getText())) {
            showAlert("Duration should be a positive integer.");
            return false;
        }

        // Check if prix is a valid float value
        if (!isValidFloat(prixField.getText())) {
            showAlert("Price should be a valid numeric value.");
            return false;
        }
        // Additional checks if needed...
        // If all checks pass, return true
        return true;
    }

    // Helper method to check if a string represents a positive integer
    private boolean isPositiveInteger(String input) {
        try {
            int value = Integer.parseInt(input);
            return value > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Helper method to check if a string represents a valid float value
    private boolean isValidFloat(String input) {
        try {
            float value = Float.parseFloat(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Helper method to show an alert for validation messages
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    @FXML
    public void handleEditAbonnement() throws SQLException {
        int idcategory=-1;
        String selectedCategory = (String) abonnementComboBox.getValue();

        if (controleDeSaisi()) {
            if(selectedCategory!=null){
                idcategory =categoryService.getCategoryIdByName(selectedCategory);
            }
            else {
                idcategory=-1;
            }
            String nom = nomField.getText();
            int duree = Integer.parseInt(dureeField.getText());
            float prix = Float.parseFloat(prixField.getText());
            boolean fidelite = true;

            Abonnement abonnement = new Abonnement(instance.SelectedSub, idcategory, nom, duree, prix, fidelite);
            AbonnementService abonnementService = new AbonnementService();
            System.out.println(abonnement);

            try {
                abonnementService.modifier(abonnement);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            notif n = new notif(-1, currentAccount.getNom(), currentAccount.getNom() + " has edited a sub ", "admin");
            try {
                notifService.ajouter(n);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            initAbonnementInputs();
            reload_page();
        }
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
        // notif t3 firas ****************
        notif n = new notif(-1,currentAccount.getNom(),currentAccount.getNom() +" has edited a category ","admin");
        try {
            notifService.ajouter(n);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        initcategoryInputs();
        reload_pageC();

    }

    void initAbonnementInputs(){
        nomField.setText("");
        dureeField.setText("");
        prixField.setText("");



    }

    public void GoToAccountAbonnementPagee(MouseEvent mouseEvent) {
    }
    void fillSubinputs(Abonnement abonnement){
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







    @FXML
    VBox SatisticsVbox;

    @FXML
    void StaisticsPage(){

        //graphic
        AbonnementsPage.setVisible(false);
        AbonnementsPage.setManaged(false);

        AddCategoryPage.setVisible(false);
        AddCategoryPage.setManaged(false);

        CategoryPage.setVisible(false);
        CategoryPage.setManaged(false);

        AddAbonnementPagee.setVisible(false);
        AddAbonnementPagee.setManaged(false);

        Satistics.setVisible(true);
        Satistics.setManaged(true);

        SatisticsVbox.getChildren().clear();
        Label titleLabel = new Label("Number of Members");
        titleLabel.getStyleClass().add("label-style");
        titleLabel.getStyleClass().add("title");



        Map<String,Integer> map = getCountOfMembersByCategory();


        // Create BarChart Data
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        ObservableList<XYChart.Series<String, Number>> barChartData = FXCollections.observableArrayList();
        barChartData.add(series);

        barChart.setData(barChartData);
        SatisticsVbox.getChildren().add(titleLabel);
        SatisticsVbox.getChildren().add(barChart);

    }



    //satistics
    public Map<String, Integer> getCountOfMembersByCategory() {
        Map<String, Integer> categoryMemberCount = new HashMap<>();


        // Iterate through each Abonnement
        for (Abonnement abonnement : abonnements) {
            String category = null; // Replace with your actual method to get category
            try {
                category = abonnementService.getCategoryName(abonnement.getIdcategory());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            String members = abonnement.getMembres(); // Replace with your actual method to get members

            // Parse the members string and count the number of members
            int memberCount = members.split(",").length;

            // Update the count in the map
            categoryMemberCount.put(category, categoryMemberCount.getOrDefault(category, 0) + memberCount);
        }

        return categoryMemberCount;
    }





    @FXML
    public void handleBan() {
        try {
            // Get the selected user name from the ComboBox
            String selectedUserName = (String) userCombo.getValue();

            // Get the selected abonnement name from the ComboBox
            String selectedAbonnementName = (String) abonnementCombo.getValue();

            // Get the ID of the selected user using streams
            int selectedUserId = accountService.afficher().stream()
                    .filter(user -> user.getNom().equals(selectedUserName))
                    .findFirst()
                    .map(Account::getId)
                    .orElse(-1); // Replace -1 with a default value or handle appropriately

            // Get the ID of the selected abonnement using streams
            int selectedAbonnementId = abonnementService.afficher().stream()
                    .filter(abonnement -> abonnement.getNom().equals(selectedAbonnementName))
                    .findFirst()
                    .map(Abonnement::getId)
                    .orElse(-1); // Replace -1 with a default value or handle appropriately

            // Now you have the IDs of the selected user and abonnement
            System.out.println("Selected User ID: " + selectedUserId);
            System.out.println("Selected Abonnement ID: " + selectedAbonnementId);


            abonnementService.removeMember(selectedAbonnementId,selectedUserId);
            GoToabonnementPage();
            // Perform further actions as needed, such as blocking the user
            // accountService.blockUser(selectedUserId);

        } catch (SQLException e) {
            // Handle potential SQL exceptions
            e.printStackTrace();
            System.out.println("Error handling ban.");
        }
    }

}
