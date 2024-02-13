package services;

import java.sql.SQLException;
import java.util.List;
import entity.Product;
import javafx.collections.ObservableList;

public interface IProductService<Product> {

    void ajouter(Product product) throws SQLException;
    void modifier(Product product) throws SQLException;
    void supprimer(int id) throws SQLException;
    ObservableList<entity.Product> afficher() throws SQLException;
    ObservableList<entity.Product> search(String name) throws SQLException;
    public entity.Product getProductById(int id) throws SQLException;


}
