package main.projet;

import entity.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

import main.projet.ShoppingCartItemCard;
import services.CommandeService;
import services.PaimentService;

public class ShoppingCart implements Initializable {

    @FXML
    Pane PaymentPage,ShopPage;

    @FXML
    VBox ShoppingcartVbox;
    @FXML
    GridPane shoppingcartGrid;
    @FXML
    Label totallabel,totalPaymentPage;
    @FXML
    TextField cardNameField,cardCodeField;

    private static ShoppingCart instance = new ShoppingCart();

    AppController appControllerinstance=AppController.getInstance();

    Account user=appControllerinstance.account;
    float montantPaiment;

    CommandeService commandeService = new CommandeService();
    PaimentService paimentService = new PaimentService();



    private List<Product> cartItems = new ArrayList<>();



    public void addToCart(Product product) {

        if(!cartItems.contains(product)){
            cartItems.add(product);
        }
    }

    public static ShoppingCart getInstance() {
        return instance;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(!instance.cartItems.isEmpty()){
            intitialisationProductList();
            updateTotalLabel();
        }
    }
    void intitialisationProductList(){
        int column = 0, row = 0;

        try {
            for (Product product : instance.cartItems) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("shoppingcartItemCard.fxml"));
                HBox shoppingcartCard = loader.load();

                ShoppingCartItemCard itemCardController = loader.getController();
                itemCardController.setData(product);
                itemCardController.setShoppingCart(this); // Set the reference to the main ShoppingCart controller

                shoppingcartGrid.add(shoppingcartCard, 0, row++);
                GridPane.setMargin(shoppingcartCard, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public int getProductQuantity(Product product) {
        for (Node node : shoppingcartGrid.getChildren()) {
            if (node instanceof HBox) {
                HBox hbox = (HBox) node;
                ShoppingCartItemCard itemCard = (ShoppingCartItemCard) hbox.getProperties().get("controller");
                if (itemCard != null && itemCard.getProduct().equals(product)) {
                    return itemCard.getQuantity();
                }
            }
        }
        return 0; // Return 0 if product not found (you can handle this case differently)
    }

    public float calculateTotal() {
        float total = 0.0f;
        for (Product product : instance.cartItems) {
            total += product.getPrice() * getProductQuantity(product);
        }
        return total;
    }

    public void updateTotalLabel() {
        double total = calculateTotal();
        // Assuming you have a Label named 'totalLabel' in your FXML
        totallabel.setText("Total : " + String.format("%.2f", total)+" TND");
    }



    void GoToPaymentPage(){
        ShopPage.setVisible(false);
        ShopPage.setManaged(false);
        PaymentPage.setVisible(true);
        PaymentPage.setManaged(true);
    }

    @FXML
    void GoToShopPage(){
        PaymentPage.setVisible(false);
        PaymentPage.setManaged(false);

        ShopPage.setVisible(true);
        ShopPage.setManaged(true);
    }









@FXML// ajouter(panier,panierproduit,commande)
 public  void handleSubmitShoppingCart() throws SQLException {
    GoToPaymentPage();

    float total = 0f;
    Map<Integer, Product> productQuantityMap = new HashMap<>();

    for (Product product : instance.cartItems) {
        int quantity = getProductQuantity(product);
        productQuantityMap.put(quantity, product);
        total += quantity * product.getPrice();
    }

    //Add f table panier

    Panier panier= new Panier(-1,instance.user.getId());

    int idpanier=  commandeService.ajouterPanier(panier);

    //Add f table panierproduit produit mtaek

    PanierProduct panierProduct ;
    Product product;
    int quantite=1;

    if(!productQuantityMap.isEmpty()){
        for (Map.Entry<Integer,Product> entry : productQuantityMap.entrySet()){

            quantite = entry.getKey();
            product = entry.getValue();

            panierProduct = new PanierProduct(-1,idpanier,product.getId(),quantite,product.getPrice());

            commandeService.ajouterPanierProduit(panierProduct);
        }
    }

    //add f table commande

    Commande commande = new Commande(-1,instance.user.getId(),idpanier,total,"Encours");
    commandeService.ajouterCommande(commande);



    totalPaymentPage.setText(String.valueOf(total)+" TND");
    instance.montantPaiment=total;

}



     @FXML  //ajouter ll paiment
     public void handleSubmitPaiment() throws SQLException {
       String cardName = cardNameField.getText();
       String cardCode = cardCodeField.getText();

       Paiment paiment = new Paiment(-1,instance.user.getId(),instance.montantPaiment,cardName,cardCode,getCurrentDate());
       paimentService.ajouter(paiment);//ajout
         GoToShopPage();

         instance.cartItems.clear();
         shoppingcartGrid.getChildren().clear();
         ProductItemCard.getInstance().counter=0;



     }




    @Override
    public String toString() {
        return "ShoppingCart{" +
                "cartItems=" + cartItems +
                '}';
    }


    private java.sql.Date getCurrentDate() {
        java.util.Date today = Calendar.getInstance().getTime();
        return new java.sql.Date(today.getTime());
    }
}
