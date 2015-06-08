package org.oa.tp.dao;


import com.google.gson.Gson;
import org.oa.tp.data.Producer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

class ProducerDao implements AbstractDao<Producer> {
     private static final String PATH = "producers.txt";
     private Statement statement;
     private Connection connection;
     private Set<Producer> items = new HashSet<>();

    public ProducerDao(Statement statement, Connection connection) {
        this.statement = statement;
        this.connection=connection;

        try {

            String sql = "CREATE TABLE IF NOT EXISTS authors " +
                    "(id INTEGER AUTO_INCREMENT, " +
                    " firstName VARCHAR(255), " +
                    " lastName VARCHAR(255), " +
                    " car_id INTEGER(10), " +
                    " cat_id INTEGER(10), " +
                    " PRIMARY KEY ( id ))";

            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Producer> loadAll() {
        List<Producer> list = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM producers");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName=resultSet.getString("lastName");
                int car_id = resultSet.getInt("car_id");
                int cat_id = resultSet.getInt("cat_id");


                list.add(new Producer(id, firstName,lastName,car_id,cat_id));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;

    }

    @Override
    public Producer findById(long producerId) {
        Producer producer = null;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM producers WHERE id=" + producerId);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName=resultSet.getString("lastName");
                int car_id = resultSet.getInt("car_id");
                int cat_id = resultSet.getInt("cat_id");
                producer = new Producer(id, firstName,lastName,car_id,cat_id);
                break;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return producer;
    }

    @Override
    public boolean delete(long producerId) {
        Producer producer = findById(producerId);

        try {
            statement.executeUpdate("DELETE FROM producers WHERE id"+ producer.getId());
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean update(Producer changed) {

        try {
            statement.executeUpdate("UPDATE producers SET firstName = '"+changed.getFirstName()+"', lastName = '"+changed.getLastName()+"', car_id = '"+changed.getCar_id()+"', cat_id = '"+changed.getCat_id()+ "' WHERE  id= "+ changed.getId()+";");
            return true; }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean add(Producer item) {

        try {
            statement.executeUpdate("INSERT INTO producers " +
                    "(firstName, lastName,car_id,cat_id)" +
                    " VALUES ('" + item.getFirstName() + "' , '" + item.getLastName() + item.getCar_id() + item.getCat_id() +
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
    public boolean addAll(Collection <Producer> collection) {

        String sqlQuery="INSERT INTO producers " +
                "(firstName, lastName,car_id,cat_id)" +
                " VALUES (?,?,?,?);";
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement =connection.prepareStatement(sqlQuery);
            for(Producer producer : collection) {
                statement.setString(1, producer.getFirstName());
                statement.setString(2, producer.getLastName());
                statement.setInt(3, (int) producer.getCar_id());
                statement.setInt(4, (int) producer.getCat_id());
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
            statement.executeUpdate("DROP TABLE producers");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
