package main.projet;

import entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import services.CommandeService;
import services.PaimentService;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class PaimentController implements Initializable {



    //interfaces
    @FXML
    VBox Paimentaffichage,Commandeaffichage;
    @FXML
    Pane EditCommandePage,EditPaimentPage;

    private static PaimentController instance = new PaimentController();
    public  static PaimentController getInstance(){return  instance;};
     int selectedPaimentId;
     int iduser= AppController.getInstance().account.getId();

     Commande selectedCommande;

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


   PaimentService paimentService = new PaimentService();
   CommandeService commandeService = new CommandeService();
    ObservableList<Paiment> paiments;

    {
        try {
            paiments = paimentService.afficher();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    TextField cardcodePaimentField,cardnamePaimentField,montantPaimentField;




    @FXML
     TextField montantCommandeField;
    @FXML
     ToggleGroup type1;

    ObservableList<Commande> commandes;

    {
        try {
            commandes = commandeService.afficher();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


// navigation methods

    @FXML
    void paimentinterfaceload(){

        {
            try {
                paiments = paimentService.afficher();
                paimentTableView.setItems(null);
                paimentTableView.setItems(paiments);
                paimentTableView.refresh();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

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
        {
            try {
                commandes = commandeService.afficher();
                commandeTableView.setItems(null);
                commandeTableView.setItems(commandes);
                commandeTableView.refresh();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

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

                            try {
                                paimentService.supprimer(paiment.getId());
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            paimentinterfaceload();
                            // Add your delete action here
                        });

                        editButton.setOnAction(event -> {
                            Paiment paiment = getTableView().getItems().get(getIndex());
                            System.out.println("Edit: " + paiment.getId());

                            instance.selectedPaimentId=paiment.getId();
                            editpaimentInerfaceLoad();
                            fillAdminInputs(paiment);
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
                  //  final Button PanierDetails = createButton("PanierDetails");

                    {
                        // Set actions for the buttons
                        deleteButton.setOnAction(event -> {
                            Commande commande = getTableView().getItems().get(getIndex());
                            System.out.println("Delete: " + commande.getId());
                            try {
                                // Supprimer commande
                                commandeService.supprimerCommande(commande.getId());

                                // Supprimer PanierProducts associés à la commande
                                ObservableList<PanierProduct> panierProductsList = commandeService.afficherPanierProductList();

                                // Filtrer PanierProducts qui ont le même idpanier que la commande
                                List<PanierProduct> filteredList = panierProductsList.stream()
                                        .filter(panierProduct -> panierProduct.getIdpanier() == commande.getIdpanier())
                                        .collect(Collectors.toList());

                                // Supprimer chaque PanierProduct filtré
                                filteredList.forEach(panierProduct -> {
                                    try {
                                        commandeService.supprimerPanierProduit(panierProduct.getId());
                                    } catch (SQLException e) {
                                        throw new RuntimeException(e);
                                    }
                                });

                                // Enfin, supprimer le Panier associé à la commande
                                commandeService.supprimerPanier(commande.getIdpanier());

                            } catch (SQLException e) {
                                e.printStackTrace(); // Handle the exception appropriately in your application
                            }
                            commandeinterfaceload();


                        });


                        editButton.setOnAction(event -> {
                            Commande commande = getTableView().getItems().get(getIndex());
                            System.out.println("Edit: " + commande.getId());
                            initAdminInputs();
                            editcommandeInerfaceLoad();
                            fillCAdminInputs(commande);
                            // Add your edit action here
                            instance.selectedCommande=commande;

                        });

                      /*  PanierDetails.setOnAction(event -> {
                            Commande commande = getTableView().getItems().get(getIndex());
                            System.out.println("PanierDetails: " + commande.getId());
                            // Add your edit action here
                        });*/


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
                        buttonPane.getChildren().addAll(deleteButton, editButton/*,PanierDetails*/);
                        return buttonPane;
                    }
                };
            }
        };
    }



    void initAdminInputs(){
        montantPaimentField.setText("");
        cardnamePaimentField.setText("");
        cardcodePaimentField.setText("");
        montantCommandeField.setText("");
    }

    void fillAdminInputs(Paiment paiment){
        montantPaimentField.setText(String.valueOf(paiment.getMontant()));
        cardnamePaimentField.setText(paiment.getCartname());
        cardcodePaimentField.setText(paiment.getCartCode());
    }


    @FXML
    public void handleEditPaiment() {

            // Retrieve values from the text fields
            String cardcode = cardcodePaimentField.getText();
            String cardname = cardnamePaimentField.getText();
            String montantString = montantPaimentField.getText().replaceAll("[^0-9.]+", "");
            // Parse the float
            float montant = Float.parseFloat(montantString);


            Paiment selectedPaiment = new Paiment(instance.selectedPaimentId, instance.iduser,montant,cardname,cardcode,getCurrentDate());

            try {
                // Update the Paiment record in the database
                paimentService.modifier(selectedPaiment);
            } catch (SQLException e) {
                // Handle the exception (e.g., show an error message)
                e.printStackTrace();
            }

            initAdminInputs();
            paimentinterfaceload();


    }




    @FXML
    TextField paimentSearchField,minmontant,maxmontant;
    @FXML
    ToggleGroup type11;

    @FXML
    public void handleSearchCommande(){

        String status = getSelectedValue(type1);
        String minmontantText = minmontant.getText();
        String maxmontantText = maxmontant.getText();

        try {
            commandes =commandeService.searchCommande(status,minmontantText,maxmontantText);
            commandeTableView.setItems(null);
            commandeTableView.setItems(commandes);
            commandeTableView.refresh();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }







    private Date getCurrentDate() {
        java.util.Date today = Calendar.getInstance().getTime();
        return new Date(today.getTime());
    }


    public String getSelectedValue(ToggleGroup group) {
        if (group.getSelectedToggle() != null) {
            RadioButton selectedRadio = (RadioButton) group.getSelectedToggle();
            return selectedRadio.getText();
        }
        return "";
    }


    void fillCAdminInputs(Commande commande){
        montantCommandeField.setText(String.valueOf(commande.getMontant()));
    }
    @FXML
    public void handleEditcommande() {

        float montant = Float.parseFloat(montantCommandeField.getText());
        String status = getSelectedValue(type1);

        Commande selectedcommande= new Commande(instance.selectedCommande.getId(),instance.selectedCommande.getIduser(),instance.selectedCommande.getIdpanier(),montant,status);
        try {
            // Update the Paiment record in the database
            commandeService.modifier(selectedcommande);
        } catch (SQLException e) {
            // Handle the exception (e.g., show an error message)
            e.printStackTrace();
        }

        initAdminInputs();
        commandeinterfaceload();

    }



    @FXML
    VBox SatisticsVbox;
    @FXML
    Pane Satistics;

    @FXML
    void StaisticsPage(){
        System.out.println("stat");
        //graphic
        Paimentaffichage.setVisible(false);
        Paimentaffichage.setManaged(false);
        EditPaimentPage.setVisible(false);
        EditPaimentPage.setManaged(false);
        Commandeaffichage.setVisible(false);
        Commandeaffichage.setManaged(false);
        EditCommandePage.setVisible(false);
        EditCommandePage.setManaged(false);

        Satistics.setVisible(true);
        Satistics.setManaged(true);

        SatisticsVbox.getChildren().clear();
        Label titleLabel = new Label("Income of every month");
        titleLabel.getStyleClass().add("label-style");
        titleLabel.getStyleClass().add("title");



        Map<String,Float> map = getIncomeDataByMonth();


        // Create BarChart Data
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        for (Map.Entry<String, Float> entry : map.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        ObservableList<XYChart.Series<String, Number>> barChartData = FXCollections.observableArrayList();
        barChartData.add(series);

        barChart.setData(barChartData);
        SatisticsVbox.getChildren().add(titleLabel);
        SatisticsVbox.getChildren().add(barChart);

    }

    private Map<String, Float> getIncomeDataByMonth() {
        Map<String, Float> incomeData = new TreeMap<>(Comparator.comparing(this::getMonthOrder));

        // Replace this with actual logic to fetch payment data from your database or service

        for (Paiment payment : paiments) {
            LocalDate paymentDate = payment.getDate().toLocalDate(); // Assuming you have a method to get payment date
            Month month = paymentDate.getMonth();
            String monthName = month.toString();

            // Update the income for the corresponding month
            incomeData.put(monthName, incomeData.getOrDefault(monthName, 0.f) + payment.getMontant());
        }

        return incomeData;
    }


    private int getMonthOrder(String monthName) {
        return Month.valueOf(monthName.toUpperCase()).getValue();
    }

}
