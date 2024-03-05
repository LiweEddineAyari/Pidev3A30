package main.projet;

import entity.notif;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import services.notifService;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class notifController implements Initializable {

    @FXML
    private Pane ListContainer;

    @FXML
    private Pane UpperSection;

    @FXML
    private TableView<entity.notif> coachTableView;

    @FXML
    private TableColumn<?, ?> notif;

    @FXML
    private VBox notifinterface;

    @FXML
    private Button productSearchBtn;



    notifService notifService = new notifService();

    ObservableList<notif> messages;


    ObservableList<notif> notifs;
    {
        try {
            notifs = notifService.afficher();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        notif.setCellValueFactory(new PropertyValueFactory<>("description"));
        coachTableView.setItems(notifs);
    }



}
