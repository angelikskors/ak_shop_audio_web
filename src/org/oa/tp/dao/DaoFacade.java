package org.oa.tp.dao;

import org.oa.tp.data.*;

import javax.servlet.ServletContext;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoFacade {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/ak_audio_shop";
    private java.sql.Statement statement;
    private Connection connection;
    private final AlbumDao albumDao;
    private final GenreDao genreDao;
    private final AuthorDao authorDao;
    private final AudioDao audioDao;
    private final CarDao carDao;
    private final CatDao catDao;
    private final ProducerDao producerDao;
    private final CustomerDao customerDao;
    private final OrderDao orderDao;


    public DaoFacade() {

        openSqlConnection();

        if (connection == null) {
            System.exit(1);
        }
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        albumDao = new AlbumDao(statement, connection);
        authorDao = new AuthorDao(statement, connection);
        genreDao = new GenreDao(statement, connection);
        audioDao = new AudioDao(statement, connection);
        carDao = new CarDao(statement, connection);
        catDao = new CatDao(statement, connection);
        producerDao = new ProducerDao(statement, connection);
        customerDao = new CustomerDao(statement, connection);
        orderDao = new OrderDao(statement, connection);
    }

    public AbstractDao<Order> getOrderDao() {
        return orderDao;
    }

    public AbstractDao<Album> getAlbumDao() {
        return albumDao;
    }

    public AbstractDao<Genre> getGenreDao() {
        return genreDao;
    }

    public AbstractDao<Author> getAuthorDao() {
        return authorDao;
    }

    public AbstractDao<Audio> getAudioDao() {
        return audioDao;
    }

    public AbstractDao<Car> getCarDao() {
        return carDao;
    }

    public AbstractDao<Cat> getCatDao() {
        return catDao;
    }

    public AbstractDao<Producer> getProducerDao() {
        return producerDao;
    }

    public AbstractDao<Customer> getCustomerDao() {
        return customerDao;
    }

    public void openSqlConnection() {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, "root", "toor");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void closeSqlConnection() {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

}
