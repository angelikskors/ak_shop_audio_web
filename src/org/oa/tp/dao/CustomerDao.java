package org.oa.tp.dao;

import com.google.gson.Gson;
import org.oa.tp.data.Customer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

class CustomerDao implements AbstractDao<Customer>{
    private static final String PATH = "customers.txt";
    private Statement statement;
    private Connection connection;

    private Set<Customer> items = new HashSet<>();
    public CustomerDao(Statement statement, Connection connection) {
        this.statement = statement;
        this.connection = connection;
        try {

            String sql = "CREATE TABLE IF NOT EXISTS customers " +
                    "(id INTEGER AUTO_INCREMENT, " +
                    " name VARCHAR(255), " +
                    " address VARCHAR(255), " +
                    " phone INTEGER(10), " +
                    " rating INTEGER(10), " +
                    " PRIMARY KEY ( id ))";

            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<Customer> loadAll() {
        List<Customer> list = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM customers");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String address=resultSet.getString("address");
                int phone = resultSet.getInt("phone");
                int rating = resultSet.getInt("rating");


                list.add(new Customer(id, name,phone,address,rating));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Customer findById(long customerId) {
        Customer customer  = null;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM customers WHERE id=" + customerId);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String address=resultSet.getString("address");
                int phone = resultSet.getInt("phone");
                int rating = resultSet.getInt("rating");

                customer = new Customer(id, name,phone,address,rating);
                break;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    @Override
    public boolean delete(long customerId) {
        Customer customer  = findById(customerId);

        try {
            statement.executeUpdate("DELETE FROM customers WHERE id"+ customerId);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Customer changed) {
        try {
            statement.executeUpdate("UPDATE customers SET name = '"+changed.getName()+"', address = '"+changed.getAddress()+"', phone = '"+changed.getPhone()+"', rating = '"+changed.getRating()+ "' WHERE  id= "+ changed.getId()+";");
            return true; }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean add(Customer item) {
        try {
            statement.executeUpdate("INSERT INTO customers " +
                    "(name,address,phone,rating)" +
                    " VALUES ('" + item.getName() + "' , '" + item.getAddress() + "' , '" + item.getPhone() + "' , '"+item.getRating()+
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
    public boolean addAll(Collection<Customer> collection) {
        String sqlQuery="INSERT INTO customers " +
                "(name,address,phone,rating)" +
                " VALUES (?,?,?,?);";
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement =connection.prepareStatement(sqlQuery);
            for(Customer customer: collection) {
                statement.setString(1, customer.getName());
                statement.setString(2, customer.getAddress());
                statement.setInt(3, (int) customer.getPhone());
                statement.setInt(4, (int) customer.getRating());
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
            statement.executeUpdate("DROP TABLE customers");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
