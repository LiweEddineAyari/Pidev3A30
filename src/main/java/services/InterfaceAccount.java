package services;


import entity.Account;
import javafx.collections.ObservableList;
import java.sql.SQLException;
import java.util.Map;

public interface InterfaceAccount<Account> {

    void ajouter(Account account) throws SQLException;
    void modifier(Account account) throws SQLException;

    void modifierP(String mail, String newPassword) throws SQLException;

    void supprimer(int id) throws SQLException;
    ObservableList<entity.Account> afficher() throws SQLException;
    ObservableList<entity.Account> search(String name, int minage, int maxage) throws SQLException;
    public ObservableList<entity.Account> searchU(String name) throws SQLException;

    Map<String, Integer> getUserTypeStatistics() throws SQLException;

    public entity.Account getAccountByAccountId(int AccountId) throws SQLException ;
    public ObservableList<entity.Account> getAccountByRole(Enum role, int count) throws SQLException;
    public entity.Account authenticate(String email, String password) throws SQLException;
}
