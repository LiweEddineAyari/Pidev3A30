
package services;

import entity.Account;
import entity.Exercice;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.MyDataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountService implements InterfaceAccount <Account>{

    private Connection connection;

    public AccountService() {
        connection= MyDataBase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Account account) throws SQLException {
        String query = "INSERT INTO account (name, prenom, age, mail, motpasse ,title) VALUES (?, ?, ?, ?, ? ,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, account.getNom());
            preparedStatement.setString(2,account.getPrenom());
            preparedStatement.setInt(3, account.getAge());
            preparedStatement.setString(4, account.getMail());
            preparedStatement.setString(5, account.getPassword());
            preparedStatement.setString(6, String.valueOf(account.getTitle()));


            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error adding account: " + e.getMessage());
        }
    }


    @Override
    public void modifier(Account account) throws SQLException {
        String query = "UPDATE account SET name=?, prenom=?, age=?, mail=?, motpasse=?, title=? WHERE id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, account.getNom());
            preparedStatement.setString(2, account.getPrenom());
            preparedStatement.setInt(3, account.getAge());
            preparedStatement.setString(4, account.getMail());
            preparedStatement.setString(5, account.getPassword());
            preparedStatement.setString(6, String.valueOf(account.getTitle()));
            preparedStatement.setInt(7, account.getId());  // Assuming id is the 7th parameter

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error editing account: " + e.getMessage());
        }
    }


    @Override
    public void supprimer(int id) throws SQLException {

        String query = "DELETE FROM account WHERE id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error deleting account: " + e.getMessage());
        }

    }

    @Override
    public ObservableList<Account> afficher() throws SQLException {
        ObservableList<Account> accountList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM `account`";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String prenom = resultSet.getString("prenom");
                int age = resultSet.getInt("age");
                String mail = resultSet.getString("mail");
                String motpasse = resultSet.getString("motpasse");
                String titleString= resultSet.getString("title"); // Retrieve the title as a String

                Account account= new Account();
                if(titleString.equals("user")){
                     account = new Account(id, name, prenom, age, mail, motpasse, Account.Title.user);
                }
                if(titleString.equals("admin")){
                     account = new Account(id, name, prenom, age, mail, motpasse, Account.Title.admin);

                }
                if(titleString.equals("coach")){
                     account = new Account(id, name, prenom, age, mail, motpasse, Account.Title.coach);
                }

                accountList.add(account);
            }
        }
        return accountList;
    }

    @Override
    public ObservableList<Account> search(String name) throws SQLException {
        ObservableList<Account> accounts = FXCollections.observableArrayList();
        String query = "SELECT * FROM account WHERE name LIKE ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String prenom = resultSet.getString("prenom");
                int age = resultSet.getInt("age");
                String mail = resultSet.getString("mail");
                String motpasse = resultSet.getString("motpasse");
                String titleString= resultSet.getString("title"); // Retrieve the title as a String

                // Convert the String to the Title enum using a method in your Title enum
                Account.Title title = Account.Title.fromString(titleString);

                Account account = new Account(id, name, prenom, age, mail, motpasse,title );
                accounts.add(account);
            }
        } catch (SQLException e) {
            throw new SQLException("Error searching for exercise: " + e.getMessage());
        }

        return accounts;
    }

    @Override
    public ObservableList<Account> searchU(String name) throws SQLException {
        return null;
    }

    @Override
    public ObservableList<Account> getAccountByAccountId(int AccountId) throws SQLException {
        return null;
    }

    @Override
    public ObservableList<Account> getAccountByRole(Enum role, int count) throws SQLException {
        return null;
    }

    public Account authenticate(String email, String password) throws SQLException {
        String sql = "SELECT * FROM `account` WHERE `mail` = ? AND `motpasse` = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String prenom = resultSet.getString("prenom");
                int age = resultSet.getInt("age");
                String mail = resultSet.getString("mail");
                String motpasse = resultSet.getString("motpasse");
                String titleString = resultSet.getString("title"); // Retrieve the title as a String

                Account account;
                switch (titleString) {
                    case "user":
                        account = new Account(id, name, prenom, age, mail, motpasse, Account.Title.user);
                        break;
                    case "admin":
                        account = new Account(id, name, prenom, age, mail, motpasse, Account.Title.admin);
                        break;
                    case "coach":
                        account = new Account(id, name, prenom, age, mail, motpasse, Account.Title.coach);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid account title: " + titleString);
                }

                return account;
            }
        }
        return null; // No account found for the given credentials
    }

}
