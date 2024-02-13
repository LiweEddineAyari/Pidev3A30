package main.projet;

import entity.Exercice;
import entity.Product;
import interfaces.ExerciceListener;
import interfaces.ProductListener;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import services.ExerciseService;
import services.ProductService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import org.apache.pdfbox.pdmodel.PDDocument;

public class ProductUserController implements Initializable, ProductListener, ExerciceListener {

    //interfaces
    @FXML
    VBox ProductsInterfaceuser,ExercicesInterfaceuser,MyFavoriteExercicesPage,WorkoutPPLPage,WorkoutSplitPage;
    @FXML
    Pane ProductDetailsInterfaceuser,ExerciceDetailsInterfaceuser,WorkoutPlanPage;
    @FXML
    TextField searchProductfield,searchExerciceField;


    //list container
    @FXML
     GridPane productGrid,exerciceGrid,exerciceGriddetails,myFavoriteGrid;
    @FXML
     ScrollPane productScroll,myfavoriteScroll;

    //detailspage
    @FXML
     Label productnameDetail,refproductDetail,quantityproductDetail,poidsproductdetail,priceproductDetail,Description_area,exercicenamedetail,exercicetargetdetail,exercicetypedetail,exerciceproductdetail,descex,exerciceeqdetail,exerciceintensitydetail;

    @FXML
     ImageView productImgView,eximgview;
    @FXML
    ToggleGroup type1,targett1;


    ProductService productService =new ProductService();
    ExerciseService exerciseService = new ExerciseService();
    ObservableList<Product> productList ;
    {
        try {
            productList = productService.afficher();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private ObservableList<Exercice> exerciceList ;
    {
        try {
            exerciceList = exerciseService.afficher();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private ShoppingCart shoppingCart=ShoppingCart.getInstance();








    //navigation methods


    @FXML
    public  void GotoProducts(){
        ExercicesInterfaceuser.setVisible(false);
        ExercicesInterfaceuser.setManaged(false);
        ProductDetailsInterfaceuser.setVisible(false);
        ProductDetailsInterfaceuser.setManaged(false);
        ExerciceDetailsInterfaceuser.setVisible(false);
        ExerciceDetailsInterfaceuser.setManaged(false);
        MyFavoriteExercicesPage.setVisible(false);
        MyFavoriteExercicesPage.setManaged(false);
        WorkoutPlanPage.setVisible(false);
        WorkoutPlanPage.setManaged(false);

        WorkoutPPLPage.setVisible(false);
        WorkoutPPLPage.setManaged(false);
        WorkoutSplitPage.setVisible(false);
        WorkoutSplitPage.setManaged(false);



        ProductsInterfaceuser.setVisible(true);
        ProductsInterfaceuser.setManaged(true);
    }
    @FXML
    public  void GotodetailsProduct(){
        ProductsInterfaceuser.setVisible(false);
        ProductsInterfaceuser.setManaged(false);
        ExercicesInterfaceuser.setVisible(false);
        ExercicesInterfaceuser.setManaged(false);
        ExerciceDetailsInterfaceuser.setVisible(false);
        ExerciceDetailsInterfaceuser.setManaged(false);
        MyFavoriteExercicesPage.setVisible(false);
        MyFavoriteExercicesPage.setManaged(false);
        WorkoutPlanPage.setVisible(false);
        WorkoutPlanPage.setManaged(false);
        WorkoutPPLPage.setVisible(false);
        WorkoutPPLPage.setManaged(false);
        WorkoutSplitPage.setVisible(false);
        WorkoutSplitPage.setManaged(false);

        ProductDetailsInterfaceuser.setVisible(true);
        ProductDetailsInterfaceuser.setManaged(true);
    }

    @FXML
    public  void GotoExerciceInterface(){
        ProductsInterfaceuser.setVisible(false);
        ProductsInterfaceuser.setManaged(false);
        ProductDetailsInterfaceuser.setVisible(false);
        ProductDetailsInterfaceuser.setManaged(false);
        ExerciceDetailsInterfaceuser.setVisible(false);
        ExerciceDetailsInterfaceuser.setManaged(false);
        MyFavoriteExercicesPage.setVisible(false);
        MyFavoriteExercicesPage.setManaged(false);
        WorkoutPlanPage.setVisible(false);
        WorkoutPlanPage.setManaged(false);

        WorkoutPPLPage.setVisible(false);
        WorkoutPPLPage.setManaged(false);
        WorkoutSplitPage.setVisible(false);
        WorkoutSplitPage.setManaged(false);

        ExercicesInterfaceuser.setVisible(true);
        ExercicesInterfaceuser.setManaged(true);
    }

    @FXML
    public  void GotodetailsExercice(){
        ProductsInterfaceuser.setVisible(false);
        ProductsInterfaceuser.setManaged(false);
        ExercicesInterfaceuser.setVisible(false);
        ExercicesInterfaceuser.setManaged(false);
        ProductDetailsInterfaceuser.setVisible(false);
        ProductDetailsInterfaceuser.setManaged(false);
        MyFavoriteExercicesPage.setVisible(false);
        MyFavoriteExercicesPage.setManaged(false);
        WorkoutPlanPage.setVisible(false);
        WorkoutPlanPage.setManaged(false);

        WorkoutPPLPage.setVisible(false);
        WorkoutPPLPage.setManaged(false);
        WorkoutSplitPage.setVisible(false);
        WorkoutSplitPage.setManaged(false);

        ExerciceDetailsInterfaceuser.setVisible(true);
        ExerciceDetailsInterfaceuser.setManaged(true);


    }
    @FXML
    public  void GotoExercices(){
        ProductDetailsInterfaceuser.setVisible(false);
        ProductDetailsInterfaceuser.setManaged(false);
        ExerciceDetailsInterfaceuser.setVisible(false);
        ExerciceDetailsInterfaceuser.setManaged(false);
        ProductsInterfaceuser.setVisible(false);
        ProductsInterfaceuser.setManaged(false);
        MyFavoriteExercicesPage.setVisible(false);
        MyFavoriteExercicesPage.setManaged(false);
        WorkoutPlanPage.setVisible(false);
        WorkoutPlanPage.setManaged(false);

        WorkoutPPLPage.setVisible(false);
        WorkoutPPLPage.setManaged(false);
        WorkoutSplitPage.setVisible(false);
        WorkoutSplitPage.setManaged(false);

        ExercicesInterfaceuser.setVisible(true);
        ExercicesInterfaceuser.setManaged(true);
    }
    @FXML
    public  void GoToWorkoutPlanPage(){
        ProductDetailsInterfaceuser.setVisible(false);
        ProductDetailsInterfaceuser.setManaged(false);
        ExerciceDetailsInterfaceuser.setVisible(false);
        ExerciceDetailsInterfaceuser.setManaged(false);
        ProductsInterfaceuser.setVisible(false);
        ProductsInterfaceuser.setManaged(false);
        MyFavoriteExercicesPage.setVisible(false);
        MyFavoriteExercicesPage.setManaged(false);
        ExercicesInterfaceuser.setVisible(false);
        ExercicesInterfaceuser.setManaged(false);

        WorkoutPPLPage.setVisible(false);
        WorkoutPPLPage.setManaged(false);
        WorkoutSplitPage.setVisible(false);
        WorkoutSplitPage.setManaged(false);

        WorkoutPlanPage.setVisible(true);
        WorkoutPlanPage.setManaged(true);
    }

    @FXML
    public  void GotoMyFavorite(){
        ProductDetailsInterfaceuser.setVisible(false);
        ProductDetailsInterfaceuser.setManaged(false);
        ExerciceDetailsInterfaceuser.setVisible(false);
        ExerciceDetailsInterfaceuser.setManaged(false);
        ProductsInterfaceuser.setVisible(false);
        ProductsInterfaceuser.setManaged(false);
        ExercicesInterfaceuser.setVisible(false);
        ExercicesInterfaceuser.setManaged(false);
        WorkoutPlanPage.setVisible(false);
        WorkoutPlanPage.setManaged(false);

        WorkoutPPLPage.setVisible(false);
        WorkoutPPLPage.setManaged(false);
        WorkoutSplitPage.setVisible(false);
        WorkoutSplitPage.setManaged(false);

        MyFavoriteExercicesPage.setVisible(true);
        MyFavoriteExercicesPage.setManaged(true);

        intitialisationMyFavoriteExerciceList();

    }

    public  void  GoToWorkoutPPLPage(){
        ExercicesInterfaceuser.setVisible(false);
        ExercicesInterfaceuser.setManaged(false);
        ProductDetailsInterfaceuser.setVisible(false);
        ProductDetailsInterfaceuser.setManaged(false);
        ExerciceDetailsInterfaceuser.setVisible(false);
        ExerciceDetailsInterfaceuser.setManaged(false);
        MyFavoriteExercicesPage.setVisible(false);
        MyFavoriteExercicesPage.setManaged(false);
        WorkoutPlanPage.setVisible(false);
        WorkoutPlanPage.setManaged(false);
        ProductsInterfaceuser.setVisible(false);
        ProductsInterfaceuser.setManaged(false);
        WorkoutSplitPage.setVisible(false);
        WorkoutSplitPage.setManaged(false);

        WorkoutPPLPage.setVisible(true);
        WorkoutPPLPage.setManaged(true);

    }

    public  void  GoToWorkoutSplitPage(){
        ExercicesInterfaceuser.setVisible(false);
        ExercicesInterfaceuser.setManaged(false);
        ProductDetailsInterfaceuser.setVisible(false);
        ProductDetailsInterfaceuser.setManaged(false);
        ExerciceDetailsInterfaceuser.setVisible(false);
        ExerciceDetailsInterfaceuser.setManaged(false);
        MyFavoriteExercicesPage.setVisible(false);
        MyFavoriteExercicesPage.setManaged(false);
        WorkoutPlanPage.setVisible(false);
        WorkoutPlanPage.setManaged(false);
        ProductsInterfaceuser.setVisible(false);
        ProductsInterfaceuser.setManaged(false);
        WorkoutPPLPage.setVisible(false);
        WorkoutPPLPage.setManaged(false);

        WorkoutSplitPage.setVisible(true);
        WorkoutSplitPage.setManaged(true);


    }


    @Override
    public void onViewDetails(Product product) {
        GotodetailsProduct();
        //lister exercises pour ce produit
        ObservableList<Exercice> recommandedExercices;
        try {
            recommandedExercices = exerciseService.getExercisesByProductId(product.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(recommandedExercices!=null){
            intitialisationExerciceListDetails(recommandedExercices);
        }

        productnameDetail.setText(product.getName());
        refproductDetail.setText(product.getRef());
        priceproductDetail.setText(String.valueOf(product.getPrice()) + " TND");
        quantityproductDetail.setText(String.valueOf(product.getQuantity()));
        poidsproductdetail.setText(String.valueOf(product.getWeight())+ " KG");
        Description_area.setText(product.getDescription());
        Image image = new Image(product.getImageUrl());
        productImgView.setImage(image);
    }

    @Override
    public void onViewDetailsExercice(Exercice exercice) {
       GotodetailsExercice();
       exercicenamedetail.setText(exercice.getName());
       exercicetargetdetail.setText(exercice.getTarget());
       exercicetypedetail.setText(exercice.getType());
        String productnamedet="none";
        if(exercice.getProductid()!=-1)
        {
            System.out.println("ex pr id : "+exercice.getProductid());
            try {
                if(productService!=null){
                    System.out.println("product name : "+productService.getProductById(exercice.getProductid()).getName());
                    productnamedet = productService.getProductById(exercice.getProductid()).getName();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        exerciceproductdetail.setText(productnamedet);
        exerciceeqdetail.setText(exercice.getEquipmentNeeded());
        exerciceintensitydetail.setText(exercice.getIntensity());
       descex.setText(exercice.getDescription());
        Image image = new Image(exercice.getImg());
        eximgview.setImage(image);
    }

    @Override
    public void onLikeExercice(Exercice exercice) {
        System.out.println(exercice);
    }

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

        intitialisationProductList();
        if (exerciceList != null) {
            intitialisationExerciceList();
        }

        //initialisation ui for split workout labels
        chestLabels = new Label[]{chex1, chex2, chex3, chex4, chex5, chex6};

         backLabels = new Label[]{backex1, backex2, backex3, backex4, backex5, backex6};

         shoulderLabels = new Label[]{shex1, shex2, shex3, shex4, shex5, shex6};

         legLabels = new Label[]{legex1, legex2, legex3, legex4, legex5, legex6};

         armLabels = new Label[]{armex1, armex2, armex3};

         //initialisation ui for ppl workout labels
          pushLabels = new Label[]{pushex1, pushex2, pushex3, pushex4, pushex5, pushex6,pushex7};

          pullLabels = new Label[]{pullex1, pullex2, pullex3, pullex4, pullex5, pullex6};

          legpplLabels = new Label[]{legpplex1, legpplex2, legpplex3, legpplex4, legpplex5, legpplex6};
    }

    void intitialisationProductList(){
        int column = 0, row = 0;

        try {
            for (Product product : productList) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("productItemCard.fxml"));
                VBox productCard = loader.load();

                if (column == 4) {
                    row++;
                    column = 0;
                }

                ProductItemCard itemCardController = loader.getController();
                itemCardController.setData(product,this);
                itemCardController.setShoppingCart(shoppingCart);

                productGrid.add(productCard, column++, row);
                GridPane.setMargin(productCard, new Insets(20));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void intitialisationExerciceList(){
            int column = 0, row = 0;

            try {
                for (Exercice exercice : exerciceList) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("exerciceItemCard.fxml"));
                    VBox exerciceCard = loader.load();

                    if (column == 5) {
                        row++;
                        column = 0;
                    }

                    ExerciceItemCard itemCardController = loader.getController();
                    itemCardController.setDataa(exercice,this);

                    exerciceGrid.add(exerciceCard, column++, row);
                    GridPane.setMargin(exerciceCard, new Insets(15));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    void intitialisationExerciceListDetails(ObservableList<Exercice> list){
        int column = 0, row = 0;

        try {
            for (Exercice exercice : list) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("exerciceItemCard.fxml"));
                VBox exerciceCard = loader.load();
                if (column == 3) {
                    row++;
                    column = 0;
                }

                ExerciceItemCard itemCardController = loader.getController();
                itemCardController.setDataa(exercice,this);

                exerciceGriddetails.add(exerciceCard,column++, row);
                GridPane.setMargin(exerciceCard, new Insets(15));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void intitialisationMyFavoriteExerciceList(){
        int column = 0, row = 0;

        try {
            for (Exercice exercice : exerciceList) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("exerciceItemCard.fxml"));
                VBox exerciceCard = loader.load();

                if (column == 5) {
                    row++;
                    column = 0;
                }

                ExerciceItemCard itemCardController = loader.getController();
                itemCardController.setDataa(exercice,this);

                myFavoriteGrid.add(exerciceCard, column++, row);
                GridPane.setMargin(exerciceCard, new Insets(15));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



    }





    @FXML
    public void handleSearchProduct() {
        String productName = searchProductfield.getText();

        try {
            productList = productService.search(productName);
            productGrid.getChildren().clear();
            intitialisationProductList();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @FXML
    public void handleSearchExercice(){

         String target=getSelectedValue(targett1);
         String  type=getSelectedValue(type1);
        String exercicenameSearch = searchExerciceField.getText();
        try {

            exerciceList = exerciseService.searchU(exercicenameSearch,target,type);
            exerciceGrid.getChildren().clear();
            intitialisationExerciceList();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }




    public String getSelectedValue(ToggleGroup group) {
        if (group.getSelectedToggle() != null) {
            RadioButton selectedRadio = (RadioButton) group.getSelectedToggle();
            return selectedRadio.getText();
        }
        return "";
    }





    //workout plan

    @FXML
    ToggleGroup problems,place,fitnesslevel,typeworkout,goal;

    @FXML
    Label chex1,chex2,chex3,chex4,chex5,chex6;//chest exercices name labels
    @FXML
    Label backex1,backex2,backex3,backex4,backex5,backex6;//back exercices name labels
    @FXML
    Label shex1,shex2,shex3,shex4,shex5,shex6;//shoulder exercices name labels
    @FXML
    Label legex1,legex2,legex3,legex4,legex5,legex6;//leg exercices name labels
    @FXML
    Label armex1,armex2,armex3;//arm exercices name labels

    @FXML
    Label pushex1,pushex2,pushex3,pushex4,pushex5,pushex6,pushex7;//push day exercices name labels
    @FXML
    Label pullex1,pullex2,pullex3,pullex4,pullex5,pullex6;//pull day exercices name labels
    @FXML
    Label legpplex1,legpplex2,legpplex3,legpplex4,legpplex5,legpplex6;//leg day exercices name labels




    Label[] pushLabels = new Label[]{pushex1, pushex2, pushex3, pushex4, pushex5, pushex6,pushex7};

    Label[] pullLabels = new Label[]{pullex1, pullex2, pullex3, pullex4, pullex5, pullex6};

    Label[] legpplLabels = new Label[]{legpplex1, legpplex2, legpplex3, legpplex4, legpplex5, legpplex6};


    Label[] chestLabels = new Label[]{chex1, chex2, chex3, chex4, chex5, chex6};

    Label[] backLabels = new Label[]{backex1, backex2, backex3, backex4, backex5, backex6};

    Label[] shoulderLabels = new Label[]{shex1, shex2, shex3, shex4, shex5, shex6};

    Label[] legLabels = new Label[]{legex1, legex2, legex3, legex4, legex5, legex6};

    Label[] armLabels = new Label[]{armex1, armex2, armex3};


    @FXML
    public void handleWorkoutPlan(){

        String workoutType = getSelectedValue(typeworkout);

        String goall = getSelectedValue(goal);
        String fitnesslevell = getSelectedValue(fitnesslevel);
        String placee = getSelectedValue(place);
        String problemss = getSelectedValue(problems);

        String type = goall,intensity,EquipmentNeeded;

        if(fitnesslevell.equals("Beginner"))
        {
            intensity="Easy";
        }
        else if(fitnesslevell.equals("Intermediate")){
            intensity="Medium";
            if(problemss.equals("Yes")){intensity="Easy";}
        }
        else if(fitnesslevell.equals("Advanced")){
            intensity="Advanced";
            if(problemss.equals("Yes")){intensity="Medium";}
        }
        else {intensity="medium";}

        if(placee.equals("Gym")){EquipmentNeeded="Yes";}
        else if(placee.equals("Home")){EquipmentNeeded="No";}
        else {EquipmentNeeded="Yes";}



        if(workoutType.equals("Split")){

                GoToWorkoutSplitPage();
                try {
                    handleSplitWorkout(type,intensity,EquipmentNeeded);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

        }
        else if(workoutType.equals("Push Pull Leg")){
            GoToWorkoutPPLPage();
            try {
                handlePPlWorkout(type,intensity,EquipmentNeeded);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            System.out.println("errorrrr!");
        }
    }


    public void  handleSplitWorkout(String type,String intensity,String EquipmentNeeded) throws SQLException {
        ObservableList<Exercice> chestDay = exerciseService.getExercisesByCriteria("Chest", type, intensity, EquipmentNeeded, 3);
        ObservableList<Exercice> backDay = exerciseService.getExercisesByCriteria("Back", type, intensity, EquipmentNeeded, 3);
        ObservableList<Exercice> shoulderDay = exerciseService.getExercisesByCriteria("Shoulder", type, intensity, EquipmentNeeded, 3);
        ObservableList<Exercice> legDay = exerciseService.getExercisesByCriteria("Leg", type, intensity, EquipmentNeeded, 3);
        ObservableList<Exercice> armDay = exerciseService.getExercisesByCriteria("Arm", type, intensity, EquipmentNeeded, 3);
        ObservableList<Exercice> abdDay = exerciseService.getExercisesByCriteria("Abs", type, intensity, EquipmentNeeded, 3);

        chestDay.addAll(armDay);
        backDay.addAll(armDay);
        shoulderDay.addAll(armDay);
        legDay.addAll(abdDay);


        // Modify the UI labels
        updateLabels(chestDay, chestLabels);
        updateLabels(backDay, backLabels);
        updateLabels(shoulderDay, shoulderLabels);
        updateLabels(legDay, legLabels);
        updateLabels(armDay, armLabels);

    }

    public void  handlePPlWorkout(String type,String intensity,String EquipmentNeeded) throws SQLException {
        ObservableList<Exercice> chestDay = exerciseService.getExercisesByCriteria("Chest", type, intensity, EquipmentNeeded, 3);
        ObservableList<Exercice> shoulderDay = exerciseService.getExercisesByCriteria("Shoulder", type, intensity, EquipmentNeeded, 3);
        ObservableList<Exercice> backDay = exerciseService.getExercisesByCriteria("Back", type, intensity, EquipmentNeeded, 3);
        ObservableList<Exercice> legDay = exerciseService.getExercisesByCriteria("Leg", type, intensity, EquipmentNeeded, 3);
        ObservableList<Exercice> armDay = exerciseService.getExercisesByCriteria("Arm", type, intensity, EquipmentNeeded, 3);
        ObservableList<Exercice> absDay = exerciseService.getExercisesByCriteria("Abs", type, intensity, EquipmentNeeded, 3);


        ObservableList<Exercice> pushDay = FXCollections.observableArrayList();
        pushDay.addAll(chestDay);
        pushDay.addAll(shoulderDay);
        pushDay.addAll(armDay.subList(0, 1));

        ObservableList<Exercice> pullDay = FXCollections.observableArrayList();
        pullDay.addAll(backDay);
        pullDay.addAll(armDay);

        legDay.addAll(absDay);


        updateLabels(pushDay, pushLabels);
        updateLabels(pullDay, pullLabels);
        updateLabels(legDay, legpplLabels);


    }

    private void updateLabels(ObservableList<Exercice> exercises, Label[] labels) {
        for (int i = 0; i < labels.length && i < exercises.size(); i++) {
            labels[i].setText(exercises.get(i).getName());
        }
    }



    public String getSplitWorkoutDetails() {

        String pdf="This is Your WorkoutPlan : 5 Days SPLIT \n";

        pdf+="Chest Day : \n";
        for (int i = 0; i < chestLabels.length  ; i++) {
            pdf+="Exercise "+ i+1 +" : "+chestLabels[i].getText()+" 3 X 12\n";
        }

        pdf+="Back Day : \n";
        for (int i = 0; i < chestLabels.length  ; i++) {
            pdf+="Exercise "+ i+1 +" : "+chestLabels[i].getText()+" 3 X 12\n";
        }

        pdf+="Shoulder Day : \n";
        for (int i = 0; i < shoulderLabels.length  ; i++) {
            pdf+="Exercise "+ i+1 +" : "+shoulderLabels[i].getText()+" 3 X 12\n";
        }

        pdf+="Leg Day : \n";
        for (int i = 0; i < legLabels.length  ; i++) {
            pdf+="Exercise "+ i+1 +" : "+legLabels[i].getText()+" 3 X 12\n";
        }

        pdf+="Arm Day : \n";
        for (int i = 0; i < armLabels.length  ; i++) {
            pdf+="Exercise "+ i+1 +" : "+armLabels[i].getText()+" 3 X 12\n";
        }

     return  pdf;
    }


}







