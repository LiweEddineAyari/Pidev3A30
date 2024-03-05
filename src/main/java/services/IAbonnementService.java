package services;


import entity.Abonnement;
import javafx.collections.ObservableList;
import java.sql.SQLException;

public interface IAbonnementService<Abonnement> {

    void ajouter(Abonnement account) throws SQLException;
    void modifier(Abonnement account) throws SQLException;

    void addnewMember(entity.Abonnement abonnement, int iduser) throws SQLException;

    Abonnement getAbonnementById(int id) throws SQLException;

    void removeMember(int idabonnement, int iduser) throws SQLException;

    void supprimer(int id) throws SQLException;
    ObservableList<entity.Abonnement> afficher() throws SQLException;
    ObservableList<entity.Abonnement> searchAbonnements(String minPriceText, String maxPriceText, String minDureeText, String maxDureeText,String nameText) throws SQLException;













    public ObservableList<entity.Abonnement> searchU(String name) throws SQLException;
    public ObservableList<entity.Abonnement> getAccountByAccountId(int AccountId) throws SQLException ;
    public ObservableList<entity.Abonnement> getAccountByRole(Enum role, int count) throws SQLException;
    public entity.Abonnement authenticate(String email, String password) throws SQLException;
}
