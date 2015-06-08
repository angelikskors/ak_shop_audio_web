package org.oa.tp.dao;


import com.google.gson.Gson;
import org.oa.tp.data.Cat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CatDao implements AbstractDao<Cat> {
    private static final String PATH = "cats.txt";
    private Statement statement;
    private Connection connection;
    private Set<Cat> items = new HashSet<>();

    public CatDao(Statement statement, Connection connection) {
        this.statement = statement;
        this.connection=connection;

        try {

            String sql = "CREATE TABLE IF NOT EXISTS cats " +
                    "(id INTEGER AUTO_INCREMENT, " +
                    " breed VARCHAR(255), " +
                    " tail BOOLEAN, " +
                    " PRIMARY KEY ( id ))";

            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<Cat> loadAll() {
        List<Cat> list = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM cats");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String breed = resultSet.getString("breed");
                Boolean tail=resultSet.getBoolean("tail");
                list.add(new Cat(id, breed,tail));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Cat findById(long catId) {
        Cat cat = null;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM albums WHERE id=" + catId);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String breed = resultSet.getString("breed");
                Boolean tail=resultSet.getBoolean("tail");
                cat = new Cat(id, breed, tail);
                break;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cat;
    }

    @Override
    public boolean delete(long id) {
        Cat cat = findById(id);

        try {
            statement.executeUpdate("DELETE FROM cats WHERE id"+ id);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean update(Cat changed) {
        try {
            statement.executeUpdate("UPDATE cats SET breed = '"+changed.getBreed()+"', tail = '"+changed.isTail()+"' WHERE  id= "+ changed.getId()+";");
            return true; }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean add(Cat item) {
        try {
            statement.executeUpdate("INSERT INTO cats " +
                    "(breed, tail)" +
                    " VALUES ('" + item.getBreed() + "' , '" + item.isTail() +
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
    public boolean addAll(Collection<Cat> collection) {
        String sqlQuery="INSERT INTO cats " +
                "(breed, tail)" +
                " VALUES (?,?);";
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement =connection.prepareStatement(sqlQuery);
            for(Cat cat : collection) {
                statement.setString(1,cat.getBreed());
                statement.setBoolean(2,cat.isTail());


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
            statement.executeUpdate("DROP TABLE cats");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
