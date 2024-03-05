package main.projet;

import entity.Exercice;
import entity.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import services.ExerciseService;
import services.ProductService;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

public class productController implements Initializable {

    //interfaces
    @FXML
    VBox ProductsInterface,ExercicesInterface,SatisticsVbox;
    @FXML
    Pane AddProductPage,AddExercicePage,Satistics;
    @FXML
    Pane ProductDetailsInterface,ExerciceDetailsInterface;

    //btn
    @FXML
    Button AddProductBtn,EditProductBtn,AddExerciceBtn,EditExerciceBtn;


    //detailproduct page
    @FXML
     Label productnameDetail,refproductDetail,quantityproductDetail,poidsproductdetail,priceproductDetail,Description_area,exerciceNameError,exerciceproductError,exerciceimageError,pnameError,prefError,ppriceError,pquantError,pweightError,pimgError;
    @FXML
    ImageView productImgView;

    //detail ex page
    @FXML
    Label exercicenamedetail,exercicetargetdetail,exercicetypedetail,exerciceproductdetail,descex,addexintTitle,addproductintTitle,exerciceintensitydetail,exerciceeqdetail;
    @FXML
    ImageView eximgview;

// table view product
    @FXML
    TableView<Product> productTableView;
    @FXML
    TableColumn<?, ?> imageColumn;
    @FXML
    TableColumn<?, ?> nameColumn;
    @FXML
    TableColumn<?, ?> refColumn;
    @FXML
    TableColumn<?, ?> priceColumn;
    @FXML
    TableColumn<Product,Void> actionsColumn;

    ///////////////////////////////////////////
    //table view exercice
    @FXML
    TableView <Exercice> exerciceTableView;

    @FXML
    TableColumn<?, ?> nameexerciceColumn;
    @FXML
    TableColumn<Exercice, Integer> productnColumn;
    @FXML
    TableColumn<Exercice,Void> actionsColumnex;
    List<String> productNames ;






    ProductService productService =new ProductService();
    ExerciseService exerciseService = new ExerciseService();


    private static productController instance =new productController();


    public static productController getInstance() {
        return instance;
    }

    private int SelectedProductid;
    private int SelectedExerciceid;

    ObservableList<Product> products;
    {
        try {
            products = productService.afficher();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    ObservableList<Exercice>  exercices;
    {
        try {
            exercices = exerciseService.afficher();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


//products navigation methods

    public void showEditProductbtn(){
        addproductintTitle.setText("Edit Product");
        AddProductBtn.setVisible(false);
        AddProductBtn.setManaged(false);

        EditProductBtn.setVisible(true);
        EditProductBtn.setManaged(true);
        ProductDetailsInterface.setVisible(false);
        ProductDetailsInterface.setManaged(false);
        ProductsInterface.setVisible(false);
        ProductsInterface.setManaged(false);

        ExercicesInterface.setVisible(false);
        ExercicesInterface.setManaged(false);

        AddExercicePage.setVisible(false);
        AddExercicePage.setManaged(false);

        ExerciceDetailsInterface.setVisible(false);
        ExerciceDetailsInterface.setManaged(false);
        Satistics.setVisible(false);
        Satistics.setManaged(false);

        AddProductPage.setVisible(true);
        AddProductPage.setManaged(true);

    }
    public void showAddProductbtn(){
        addproductintTitle.setText("Add a new Product");
        EditProductBtn.setVisible(false);
        EditProductBtn.setManaged(false);

        AddProductBtn.setVisible(true);
        AddProductBtn.setManaged(true);
    }

    public void showEditExercicebtn(){
        addexintTitle.setText("Edit Exercise");
        AddProductBtn.setVisible(false);
        AddProductBtn.setManaged(false);

        ExercicesInterface.setVisible(false);
        ExercicesInterface.setManaged(false);

        ExerciceDetailsInterface.setVisible(false);
        ExerciceDetailsInterface.setManaged(false);
        Satistics.setVisible(false);
        Satistics.setManaged(false);

        AddExercicePage.setVisible(true);
        AddExercicePage.setManaged(true);
        EditExerciceBtn.setVisible(true);
        EditExerciceBtn.setManaged(true);
    }
    public void showAddExercicebtn(){
        addexintTitle.setText("Add a new Exercise");
        EditExerciceBtn.setVisible(false);
        EditExerciceBtn.setManaged(false);

        AddProductBtn.setVisible(true);
        AddProductBtn.setManaged(true);
    }

@FXML
    public void addproductint(){
        showAddProductbtn();
        ProductDetailsInterface.setVisible(false);
        ProductDetailsInterface.setManaged(false);
        ProductsInterface.setVisible(false);
        ProductsInterface.setManaged(false);

        ExercicesInterface.setVisible(false);
        ExercicesInterface.setManaged(false);

        AddExercicePage.setVisible(false);
        AddExercicePage.setManaged(false);

        ExerciceDetailsInterface.setVisible(false);
        ExerciceDetailsInterface.setManaged(false);
        Satistics.setVisible(false);
        Satistics.setManaged(false);

        AddProductPage.setVisible(true);
        AddProductPage.setManaged(true);
    }



    @FXML
    public void prodpageload(){
        {
            try {
                products = productService.afficher();
                productTableView.setItems(null);
                productTableView.setItems(products);
                productTableView.refresh();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        initProductinputs();
        AddProductPage.setVisible(false);
        AddProductPage.setManaged(false);

        ProductDetailsInterface.setVisible(false);
        ProductDetailsInterface.setManaged(false);

        ExercicesInterface.setVisible(false);
        ExercicesInterface.setManaged(false);

        AddExercicePage.setVisible(false);
        AddExercicePage.setManaged(false);

        ExerciceDetailsInterface.setVisible(false);
        ExerciceDetailsInterface.setManaged(false);
        Satistics.setVisible(false);
        Satistics.setManaged(false);


        ProductsInterface.setVisible(true);
        ProductsInterface.setManaged(true);

    }

//exercice navigation methods

    @FXML
    public void GoToExerices(){

        {
            try {
                exercices = exerciseService.afficher();
                exerciceTableView.setItems(null);
                exerciceTableView.setItems(exercices);
                exerciceTableView.refresh();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        initExerciseinputs();

        ProductDetailsInterface.setVisible(false);
        ProductDetailsInterface.setManaged(false);
        ProductsInterface.setVisible(false);
        ProductsInterface.setManaged(false);
        AddProductPage.setVisible(false);
        AddProductPage.setManaged(false);


        AddExercicePage.setVisible(false);
        AddExercicePage.setManaged(false);

        ExerciceDetailsInterface.setVisible(false);
        ExerciceDetailsInterface.setManaged(false);
        Satistics.setVisible(false);
        Satistics.setManaged(false);

        ExercicesInterface.setVisible(true);
        ExercicesInterface.setManaged(true);

    }
    @FXML
    public void GoToSatistics(){
        ProductDetailsInterface.setVisible(false);
        ProductDetailsInterface.setManaged(false);
        ProductsInterface.setVisible(false);
        ProductsInterface.setManaged(false);
        AddProductPage.setVisible(false);
        AddProductPage.setManaged(false);


        AddExercicePage.setVisible(false);
        AddExercicePage.setManaged(false);

        ExerciceDetailsInterface.setVisible(false);
        ExerciceDetailsInterface.setManaged(false);
        ExercicesInterface.setVisible(false);
        ExercicesInterface.setManaged(false);

        Satistics.setVisible(true);
        Satistics.setManaged(true);

        handleSatistics();

    }


    @FXML
     public void addExerciceInt(){
         showAddExercicebtn();
         ExercicesInterface.setVisible(false);
         ExercicesInterface.setManaged(false);

         ExerciceDetailsInterface.setVisible(false);
         ExerciceDetailsInterface.setManaged(false);
        Satistics.setVisible(false);
        Satistics.setManaged(false);

         AddExercicePage.setVisible(true);
         AddExercicePage.setManaged(true);
     }

    public void productDetails(Product product){
        AddProductPage.setVisible(false);
        AddProductPage.setManaged(false);
        ProductsInterface.setVisible(false);
        ProductsInterface.setManaged(false);

        ProductDetailsInterface.setVisible(true);
        ProductDetailsInterface.setManaged(true);

        productnameDetail.setText(product.getName());
        refproductDetail.setText(product.getRef());
        priceproductDetail.setText(String.valueOf(product.getPrice())+" TND");
        quantityproductDetail.setText(String.valueOf(product.getQuantity()));
        poidsproductdetail.setText(String.valueOf(product.getWeight())+" KG");
        Description_area.setText(product.getDescription());
         Image image = new Image(product.getImageUrl());
         productImgView.setImage(image);
    }


   public void exercicesdetails(Exercice exercice){
       ProductDetailsInterface.setVisible(false);
       ProductDetailsInterface.setManaged(false);
       ProductsInterface.setVisible(false);
       ProductsInterface.setManaged(false);
       AddProductPage.setVisible(false);
       AddProductPage.setManaged(false);


       AddExercicePage.setVisible(false);
       AddExercicePage.setManaged(false);

       ExercicesInterface.setVisible(false);
       ExercicesInterface.setManaged(false);

       ExerciceDetailsInterface.setVisible(true);
       ExerciceDetailsInterface.setManaged(true);

       exercicenamedetail.setText(exercice.getName());
       exercicetargetdetail.setText(exercice.getTarget());
       exercicetypedetail.setText(exercice.getType());

       String productnamedet="none";
       if(exercice.getProductid()!=-1)
       {
           try {
               if(productService!=null){
                   if(productService.getProductById(exercice.getProductid())!=null){
                       productnamedet = productService.getProductById(exercice.getProductid()).getName();
                   }
                   else{
                       productnamedet = "none";
                   }
               }
           } catch (SQLException e) {
               throw new RuntimeException(e);
           }
       }

       exerciceproductdetail.setText(productnamedet);

       descex.setText(exercice.getDescription());
       exerciceeqdetail.setText(exercice.getEquipmentNeeded());
       exerciceintensitydetail.setText(exercice.getIntensity());
       Image image = new Image(exercice.getImg());
       eximgview.setImage(image);

   }

    public void handleSatistics(){
        ObservableList<PieChart.Data> pieChartData= FXCollections.observableArrayList();

        try {
            Map<String, Integer> exerciceStatistics = exerciseService.getExercicesWithGreatestScoret();

            // Now you can iterate over the map entries and use the data
            for (Map.Entry<String, Integer> entry : exerciceStatistics.entrySet()) {
                String type = entry.getKey();
                int score = entry.getValue();
                pieChartData.add(new PieChart.Data(type,score));

                System.out.println("Type: " + type + ", Score: " + score);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately in your application
        }



        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setClockwise(true);
        pieChart.setLabelLineLength(50);
        pieChart.setLabelsVisible(true);
        pieChart.setStartAngle(180);
        pieChart.setStyle("-fx-font-size: 20px; -fx-text-fill: #f16602;");


        // Create a label
        Label titleLabel = new Label("Most Liked Types Of Exercises");
        titleLabel.getStyleClass().add("label-style");
        titleLabel.getStyleClass().add("title");

        SatisticsVbox.getChildren().clear();
        SatisticsVbox.getChildren().add(titleLabel);
        SatisticsVbox.getChildren().add(pieChart);


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //tableview product
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        refColumn.setCellValueFactory(new PropertyValueFactory<>("ref"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        actionsColumn.setCellFactory(createButtonCellFactory());
        productTableView.setItems(products);

        //tableview exercice
        nameexerciceColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productnColumn.setCellValueFactory(new PropertyValueFactory<>("productid"));

        productnColumn.setCellFactory(column -> new TableCell<Exercice, Integer>() {
            @Override
            protected void updateItem(Integer productId, boolean empty) {
                super.updateItem(productId, empty);
                if (empty || productId == null) {
                    setText(null);
                } else {
                    // Assuming you have a method getProductById in your data layer
                    String productName = "none";
                   if(productId!=-1){
                       try {
                           if(productService.getProductById(productId)!=null){
                               productName = productService.getProductById(productId).getName();
                           }
                           else{
                               productName = "none";
                           }

                       } catch (SQLException e) {
                           throw new RuntimeException(e);

                       }
                   }
                    setText(productName);
                }
            }
        });

        actionsColumnex.setCellFactory(createButtonCellFactoryEX());
        exerciceTableView.setItems(exercices);


        try {
            productNames = productService.getAllProductNames();
            productComboBox.getItems().addAll(productNames);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }





    //action column for product table
    Callback<TableColumn<Product, Void>, TableCell<Product, Void>> createButtonCellFactory() {
        return new Callback<TableColumn<Product, Void>, TableCell<Product, Void>>() {
            @Override
            public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {
                return new TableCell<Product, Void>() {

                    final Button deleteButton = createButton("Delete");
                    final Button editButton = createButton("Edit");
                    final Button detailsButton = createButton("Details");

                    {
                        // Set actions for the buttons
                        deleteButton.setOnAction(event -> {
                            Product product = getTableView().getItems().get(getIndex());
                            System.out.println("Delete: " + product.getId());
                            try {
                                productService.supprimer(product.getId());
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            prodpageload();

                            // Add your delete action here
                        });

                        editButton.setOnAction(event -> {
                            Product product = getTableView().getItems().get(getIndex());
                            System.out.println("Edit: " + product.getId());
                            instance.SelectedProductid=product.getId();
                            showEditProductbtn();
                            fillProductinputs(product);
                            // Add your edit action here
                        });

                        detailsButton.setOnAction(event -> {
                            Product product = getTableView().getItems().get(getIndex());
                            System.out.println("Details: " + product.getId());
                            productDetails(product);

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
                        buttonPane.getChildren().addAll(deleteButton, editButton, detailsButton);
                        return buttonPane;
                    }
                };
            }
        };
    }



    Callback<TableColumn<Exercice, Void>, TableCell<Exercice, Void>> createButtonCellFactoryEX() {
        return new Callback<TableColumn<Exercice, Void>, TableCell<Exercice, Void>>() {
            @Override
            public TableCell<Exercice, Void> call(final TableColumn<Exercice, Void> param) {
                return new TableCell<Exercice, Void>() {

                    final Button deleteButton = createButton("Delete");
                    final Button editButton = createButton("Edit");
                    final Button detailsButton = createButton("Details");

                    {
                        // Set actions for the buttons
                        deleteButton.setOnAction(event -> {
                            Exercice exercice = getTableView().getItems().get(getIndex());
                            System.out.println("Delete: " + exercice.getId());

                            try {
                                exerciseService.supprimer(exercice.getId());
                                GoToExerices();
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            // Add your delete action here
                        });

                        editButton.setOnAction(event -> {
                            Exercice exercice = getTableView().getItems().get(getIndex());
                            System.out.println("Edit: " + exercice.getId());
                            instance.SelectedExerciceid=exercice.getId();
                            showEditExercicebtn();
                            fillExerciceinputs(exercice);
                            // Add your edit action here
                        });

                        detailsButton.setOnAction(event -> {
                            Exercice exercice = getTableView().getItems().get(getIndex());
                            System.out.println("Details: " + exercice.getId());
                            exercicesdetails(exercice);



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
                        buttonPane.getChildren().addAll(deleteButton, editButton, detailsButton);
                        return buttonPane;
                    }
                };
            }
        };
    }


    //handle Services

    @FXML
    TextField productNameField,productRefField,productPriceField,productQuantityField,productWeightField,productImgField,searchProductfield;
    @FXML
    TextArea productDescField;

    @FXML
    TextField exercicenamefield,exerciceimgfield,searchExerciceField;
    @FXML
    TextArea exercicedescfield;
    @FXML
    ToggleGroup target,type,intensity,equipmentneeded;

    @FXML
     ComboBox<String> productComboBox;


    void initProductinputs(){
        productNameField.setText("");
        productRefField.setText("");
        productPriceField.setText("");
        productQuantityField.setText("");
        productWeightField.setText("");
        productImgField.setText("");
        productDescField.setText("");
    }
    private void initErrorLabels() {
        exerciceNameError.setText("");
        exerciceproductError.setText("");
        exerciceimageError.setText("");
        pnameError.setText("");
        prefError.setText("");
        ppriceError.setText("");
        pquantError.setText("");
        pweightError.setText("");
        pimgError.setText("");
    }

    void fillProductinputs(Product product){
        productNameField.setText(product.getName());
        productRefField.setText(product.getRef());
        productPriceField.setText(String.valueOf(product.getPrice()));
        productQuantityField.setText(String.valueOf(product.getQuantity()));
        productWeightField.setText(String.valueOf(product.getWeight()));
        productImgField.setText(product.getImageUrl());
        productDescField.setText(product.getDescription());
    }



    @FXML
    private void browseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");

        // Set extension filters if needed
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show open file dialog
        Stage stage = (Stage) productImgField.getScene().getWindow();
        java.io.File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            // Convert the absolute file path to a URL
            String absolutePath = file.getAbsolutePath();
            String urlPath = "file:/" + absolutePath.replace("\\", "/");

            // Set the URL path to the text field
            productImgField.setText(urlPath);
        }
    }



    @FXML
    public void handleAddProduct(){
        initErrorLabels();

        if(controleDeSaisiForProduct()){
            String name = productNameField.getText();
            String ref =  productRefField.getText();
            float price =  Float.parseFloat(productPriceField.getText());
            int quantity = Integer.parseInt(productQuantityField.getText());
            int weight = Integer.parseInt(productWeightField.getText());
            String imageUrl = productImgField.getText();
            String description =  productDescField.getText();

            Product product = new Product(-1, name, ref, price, quantity, weight, imageUrl, description);
            ProductService productService =new ProductService();

            try {
                productService.ajouter(product);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            initProductinputs();
            initErrorLabels();
            prodpageload();
        }

    }

    private boolean controleDeSaisiForProduct() {
        // Validate product name
        String name = productNameField.getText();
        if (name.isEmpty()) {
            // Display an error message or handle the empty name case
            pnameError.setText("Product name is required.");
            return false;
        }

        // Validate product reference
        String ref = productRefField.getText();
        if (ref.isEmpty()) {
            // Display an error message or handle the empty reference case
            prefError.setText("Product reference is required.");

            return false;
        }

        // Validate product price
        float price;
        try {
            price = Float.parseFloat(productPriceField.getText());
        } catch (NumberFormatException e) {
            // Handle the case where the input is not a valid float
            ppriceError.setText("Product price must be a valid number.");

            return false;
        }

        // Validate product quantity
        int quantity;
        try {
            quantity = Integer.parseInt(productQuantityField.getText());
        } catch (NumberFormatException e) {
            // Handle the case where the input is not a valid integer
            pquantError.setText("Product quantity must be a valid number.");

            return false;
        }

        // Validate product weight
        int weight;
        try {
            weight = Integer.parseInt(productWeightField.getText());
        } catch (NumberFormatException e) {
            // Handle the case where the input is not a valid integer
            pweightError.setText("Product weight must be a valid number.");

            return false;
        }

        // Validate product image URL
        String imageUrl = productImgField.getText();
        if (imageUrl.isEmpty()) {
            // Display an error message or handle the empty image URL case
            pimgError.setText("Product image URL is required.");
            return false;
        }

        return true;
    }

    @FXML
    public void handleEditProduct(){
        initErrorLabels();
        if(controleDeSaisiForProduct()){
            String name = productNameField.getText();
            String ref =  productRefField.getText();
            float price =  Float.parseFloat(productPriceField.getText());
            int quantity = Integer.parseInt(productQuantityField.getText());
            int weight = Integer.parseInt(productWeightField.getText());
            String imageUrl = productImgField.getText();
            String description =  productDescField.getText();

            Product product = new Product( instance.SelectedProductid, name, ref, price, quantity, weight, imageUrl, description);
            ProductService productService =new ProductService();

            try {
                productService.modifier(product);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            initProductinputs();
            initErrorLabels();
            prodpageload();
        }


    }

    @FXML
    public void handleSearchProduct() {
       String productName = searchProductfield.getText();

        try {
            products = productService.search(productName);
            productTableView.setItems(null);
            productTableView.setItems(products);
            productTableView.refresh();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }




    //exercices methods

    void initExerciseinputs(){
        exercicenamefield.setText("");
        exercicedescfield.setText("");
        exerciceimgfield.setText("");

    }
    public String getSelectedValue(ToggleGroup group) {
        if (group.getSelectedToggle() != null) {
            RadioButton selectedRadio = (RadioButton) group.getSelectedToggle();
            return selectedRadio.getText();
        }
        return "";
    }





    void fillExerciceinputs(Exercice exercice) {
        exercicenamefield.setText(exercice.getName());
        exercicedescfield.setText(exercice.getDescription());
        exerciceimgfield.setText(exercice.getImg());

        // Assuming target, type, intensity, and equipmentneeded are ToggleGroups
        selectToggle(target, exercice.getTarget());
        selectToggle(type, exercice.getType());
        selectToggle(intensity, exercice.getIntensity());
        selectToggle(equipmentneeded, exercice.getEquipmentNeeded());
    }

    private void selectToggle(ToggleGroup toggleGroup, String value) {
        if (toggleGroup != null) {
            toggleGroup.getToggles().stream()
                    .filter(toggle -> Objects.equals(toggle.getUserData(), value))
                    .findFirst()
                    .ifPresent(toggle -> {
                        toggleGroup.selectToggle(toggle);
                        System.out.println("Selected Toggle: " + toggle.getUserData());
                    });
        }
    }

    @FXML
    private void browseImageExercice() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");

        // Set extension filters if needed
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show open file dialog
        Stage stage = (Stage) exerciceimgfield.getScene().getWindow();
        java.io.File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            // Convert the absolute file path to a URL
            String absolutePath = file.getAbsolutePath();
            String urlPath = "file:/" + absolutePath.replace("\\", "/");

            // Set the URL path to the text field
            exerciceimgfield.setText(urlPath);
        }
    }
    private boolean controleDeSaisi() {
        // Validate exercise name
        String exerciseName = exercicenamefield.getText();
        if (exerciseName.isEmpty()) {
            // Display an error message or handle the empty name case
            exerciceNameError.setText("Exercise name is required.");
            return false;
        }




        // Validate exercise image field
        String exerciceimgfieldText = exerciceimgfield.getText();
        if (exerciceimgfieldText.isEmpty()) {
            // Display an error message or handle the empty image field case
            System.out.println("Exercise image is required.");
            exerciceimageError.setText("Exercise image is required.");

            return false;
        }

        // If the code reaches here, all input is valid
        return true;
    }

    @FXML
    public void handleExerciceAdd(){
        initErrorLabels();
        String selectedProductName = productComboBox.getValue();
        if (controleDeSaisi()) {
            String exerciseName = exercicenamefield.getText();
            int exerciseProductID = -1;

            try {
                if(selectedProductName!=null){
                    exerciseProductID = productService.getProductIdByName(selectedProductName);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            String exerciseDescription = exercicedescfield.getText();
            String targetValue = getSelectedValue(target);
            String typeValue = getSelectedValue(type);
            String intensityValue = getSelectedValue(intensity);
            String equipmentNeededValue = getSelectedValue(equipmentneeded);
            String exerciceimgfieldText=exerciceimgfield.getText();

            Exercice exercice =new Exercice(-1,exerciseProductID,exerciseName,targetValue,typeValue,exerciseDescription,exerciceimgfieldText,intensityValue,equipmentNeededValue);

                try {
                    exerciseService.ajouter(exercice);
                    initExerciseinputs();
                    initErrorLabels();
                    GoToExerices();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
        }
    }

    @FXML
    public void handleExerciceEdit(){
        initErrorLabels();
        String selectedProductName = productComboBox.getValue();
        if (controleDeSaisi()) {
            String exerciseName = exercicenamefield.getText();
            int exerciseProductID = -1;

            try {
                if(!selectedProductName.equals("none")){
                    exerciseProductID = productService.getProductIdByName(selectedProductName);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }            String exerciseDescription = exercicedescfield.getText();
            String targetValue = getSelectedValue(target);
            String typeValue = getSelectedValue(type);
            String intensityValue = getSelectedValue(intensity);
            String equipmentNeededValue = getSelectedValue(equipmentneeded);
            String exerciceimgfieldText = exerciceimgfield.getText();

            Exercice exercice = new Exercice(instance.SelectedExerciceid, exerciseProductID, exerciseName, targetValue, typeValue, exerciseDescription, exerciceimgfieldText, intensityValue, equipmentNeededValue);

            try {
                exerciseService.modifier(exercice);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            initExerciseinputs();
            initErrorLabels();
            GoToExerices();
        }
    }

    @FXML
    public void handleSearchExercice(){
        String exercicenameSearch = searchExerciceField.getText();

        try {
            exercices = exerciseService.search(exercicenameSearch);
            exerciceTableView.setItems(null);
            exerciceTableView.setItems(exercices);
            exerciceTableView.refresh();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }







}






