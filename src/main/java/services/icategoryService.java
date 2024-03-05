package services;


import entity.Category;
import javafx.collections.ObservableList;
import java.sql.SQLException;

public interface icategoryService<Category> {

    void ajouter(Category category) throws SQLException;
    void modifier(Category category) throws SQLException;
    void supprimer(int id) throws SQLException;
    ObservableList<entity.Category> afficher() throws SQLException;

    ObservableList<String> GetCategoriesNames() throws SQLException;

    int getCategoryIdByName(String categoryName) throws SQLException;

    ObservableList<entity.Category> search(String name) throws SQLException;
    public ObservableList<entity.Category> searchU(String name) throws SQLException;
    public ObservableList<entity.Category> getAccountByAccountId(int AccountId) throws SQLException ;
    public ObservableList<entity.Category> getAccountByRole(Enum role, int count) throws SQLException;
    public entity.Category authenticate(String email, String password) throws SQLException;
}
