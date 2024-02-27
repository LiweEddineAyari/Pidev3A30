package services;


import entity.Account;
import javafx.collections.ObservableList;
import java.sql.SQLException;

public interface InterfaceAccount<Account> {

    void ajouter(Account account) throws SQLException;
    void modifier(Account account) throws SQLException;
    void supprimer(int id) throws SQLException;
    ObservableList<entity.Account> afficher() throws SQLException;
    ObservableList<entity.Account> search(String name) throws SQLException;
    public ObservableList<entity.Account> searchU(String name) throws SQLException;
    public ObservableList<entity.Account> getAccountByAccountId(int AccountId) throws SQLException ;
    public ObservableList<entity.Account> getAccountByRole(Enum role, int count) throws SQLException;
    public entity.Account authenticate(String email, String password) throws SQLException;
}
