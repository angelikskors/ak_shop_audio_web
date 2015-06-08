package org.oa.tp.dao;


import com.google.gson.Gson;
import org.oa.tp.data.Car;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

class CarDao implements AbstractDao<Car> {
    private static final String PATH = "cars.txt";
    private Statement statement;
    private Connection connection;
    private Set<Car> items = new HashSet<>();

    public CarDao(Statement statement, Connection connection) {
        this.statement = statement;
        this.connection=connection;

        try {

            String sql = "CREATE TABLE IF NOT EXISTS cars " +
                    "(id INTEGER AUTO_INCREMENT, " +
                    " brand VARCHAR(255), " +
                    " PRIMARY KEY ( id ))";

            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<Car> loadAll() {
        List<Car> list = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM cars");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String brand = resultSet.getString("brand");


                list.add(new Car(id,brand));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Car findById(long carId) {
        Car car   = null;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM cars WHERE id=" + carId);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String brand = resultSet.getString("brand");

                car = new Car(id,brand);
                break;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return car;
    }

    @Override
    public boolean delete(long carId) {
        Car car   = findById(carId);

        try {
            statement.executeUpdate("DELETE FROM cars WHERE id"+ carId);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Car changed) {
        try {
            statement.executeUpdate("UPDATE cars SET brand = '"+changed.getBrand()+"' WHERE  id= "+ changed.getId()+";");
            return true; }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean add(Car item) {
        try {
            statement.executeUpdate("INSERT INTO cars " +
                    "(brand)" +
                    " VALUES ('" + item.getBrand() +
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
    public boolean addAll(Collection<Car> collection) {
        String sqlQuery="INSERT INTO cars " +
                "(brand)" +
                " VALUES (?);";
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement =connection.prepareStatement(sqlQuery);
            for(Car car: collection) {
                statement.setString(1, car.getBrand());

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
            statement.executeUpdate("DROP TABLE cars");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
