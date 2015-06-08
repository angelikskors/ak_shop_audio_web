package org.oa.tp.dao;

import com.google.gson.Gson;
import org.oa.tp.data.Author;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthorDao implements AbstractDao<Author> {
    private static final String PATH = "authors.txt";
    private Statement statement;
    private Connection connection;
    private Set<Author> items = new HashSet<>();
    AuthorDao(){}

    public AuthorDao(Statement statement, Connection connection) {
        this.statement = statement;
        this.connection = connection;
        try {

            String sql = "CREATE TABLE IF NOT EXISTS authors " +
                    "(id INTEGER AUTO_INCREMENT, " +
                    " firstName VARCHAR(255), " +
                    " lastName VARCHAR(255), " +
                    " age INTEGER(10), " +
                     " PRIMARY KEY ( id ))";

            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Author> loadAll() {
        List<Author> list = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM authors");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName=resultSet.getString("lastName");
                int age = resultSet.getInt("age");


                list.add(new Author(id, firstName,lastName,age));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;

    }

    @Override
    public Author findById(long authorId) {
        Author author = null;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM authors WHERE id=" + authorId);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName=resultSet.getString("lastName");
                int age = resultSet.getInt("age");

                author = new Author(id, firstName,lastName,age);
                break;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return author;
    }

    @Override
    public boolean delete(long authorId) {
        Author author = findById(authorId);

        try {
            statement.executeUpdate("DELETE FROM authors WHERE id"+ author.getId());
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean update(Author changed) {
        try {
            statement.executeUpdate("UPDATE authors SET firstName = '"+changed.getFirstName()+"', lastName = '"+changed.getLastName()+"', age = '"+changed.getAge()+ "' WHERE  id= "+ changed.getId()+";");
            return true; }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean add(Author item) {
        try {
            statement.executeUpdate("INSERT INTO authors " +
                    "(firstName, lastName,age)" +
                    " VALUES ('" + item.getFirstName() + "' , '" + item.getLastName() + item.getAge()+
                    "');");

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean saveAll() {
        Gson gson = new Gson();
        try (FileWriter fileWriter = new FileWriter(new File(PATH))) {

            gson.toJson(items, fileWriter);
        } catch (FileNotFoundException e) {
            Logger.getLogger("shop").log(Level.INFO, "Not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean addAll(Collection<Author> collection) {
        String sqlQuery="INSERT INTO authors " +
                "(firstName, lastName, age)" +
                " VALUES (?,?,?);";
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement =connection.prepareStatement(sqlQuery);
            for(Author author: collection) {
                statement.setString(1,author.getFirstName());
                statement.setString(2, author.getLastName());
                statement.setInt(3, author.getAge());
                 statement.executeUpdate();
            }
            connection.commit();
            connection.setAutoCommit(true);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        return false;
    }


    @Override
    public void deleteAll() {

        try {
            statement.executeUpdate("DROP TABLE authors");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
