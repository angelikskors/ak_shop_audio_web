 package org.oa.tp.dao;

 import com.google.gson.Gson;
 import org.oa.tp.data.Order;

 import java.io.File;
 import java.io.FileNotFoundException;
 import java.io.FileWriter;
 import java.io.IOException;
 import java.sql.*;
 import java.sql.Date;
 import java.util.*;
 import java.util.logging.Level;
 import java.util.logging.Logger;

 class OrderDao  implements AbstractDao<Order> {
    private static final String PATH = "orders.txt";
    private Statement statement;
    private Connection connection;
    private Set<Order> items = new HashSet<>();

    public OrderDao(Statement statement, Connection connection) {
        this.statement = statement;
        this.connection=connection;

        try {

            String sql = "CREATE TABLE IF NOT EXISTS orders " +
                    "(id INTEGER AUTO_INCREMENT, " +
                    " date VARCHAR(255), " +
                    " audio_id INTEGER, " +
                    " amount INTEGER, " +
                    " customer_id INTEGER, " +
                    " PRIMARY KEY ( id ))";

            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<Order> loadAll() {

        List<Order> list = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM orders");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                Date date = resultSet.getDate("date");
                int audio_id = resultSet.getInt("audio_id");
                int amount = resultSet.getInt("amount");

                int customer_id = resultSet.getInt("customer_id");


                list.add(new Order(id,date,audio_id,amount,customer_id));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Order findById(long orderId) {
        Order order  = null;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM orders WHERE id=" + orderId);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                Date date = resultSet.getDate("date");
                int audio_id = resultSet.getInt("audio_id");
                int amount = resultSet.getInt("amount");

                int customer_id = resultSet.getInt("customer_id");
                order = new Order(id,date,audio_id,amount,customer_id);
                break;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    @Override
    public boolean delete(long orderId) {
        Order order  = findById(orderId);

        try {
            statement.executeUpdate("DELETE FROM orders WHERE id"+ orderId);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Order changed) {
        try {
            statement.executeUpdate("UPDATE orders SET date = '"+changed.getDate()+"', audio_id = '"+changed.getAudio_id()+"', amount = '"+changed.getAmount()+ "', customer_id = '"+changed.getCustomer_id()+"' WHERE  id= "+ changed.getId()+";");
            return true; }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean add(Order item) {
        try {
            statement.executeUpdate("INSERT INTO orders " +
                    "(date, audio_id,amount,customer_id)" +
                    " VALUES ('" + item.getDate() + "' , '" + item.getAudio_id() + "' , '" +item.getAmount()+"' , '" +item.getCustomer_id()+
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
    public boolean addAll(Collection<Order> collection) {
        String sqlQuery="INSERT INTO orders " +
                "(date, audio_id,amount,customer_id)" +
                " VALUES (?,?,?,?);";
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement =connection.prepareStatement(sqlQuery);
            for(Order order: collection) {
                statement.setDate(1, (Date) order.getDate());

                statement.setInt(2, (int) order.getAudio_id());
                statement.setInt(3, order.getAmount());
                statement.setInt(4, (int) order.getCustomer_id());


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
            statement.executeUpdate("DROP TABLE audios");

            statement.executeUpdate("DROP TABLE genres");
            statement.executeUpdate("DROP TABLE albums");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

