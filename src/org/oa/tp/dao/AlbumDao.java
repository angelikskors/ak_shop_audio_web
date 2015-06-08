package org.oa.tp.dao;

import com.google.gson.Gson;
import org.oa.tp.data.Album;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


class AlbumDao implements AbstractDao<Album> {

    private static final String PATH = "albums.txt";
    private Statement statement;
    private Connection connection;
    private Set<Album> items = new HashSet<>();

    public AlbumDao(Statement statement, Connection connection) {
        this.statement = statement;
        this.connection=connection;

        try {

            String sql = "CREATE TABLE IF NOT EXISTS albums " +
                    "(id INTEGER AUTO_INCREMENT, " +
                    " name VARCHAR(255), " +
                    " year INTEGER, " +
                    " producer_id INTEGER(255), " +
                    " country VARCHAR(255), " +
                    " language VARCHAR(255), " +
                    " PRIMARY KEY ( id ))";

            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Album> loadAll() {

        List<Album> list = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM albums");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
               String name = resultSet.getString("name");
                int year = resultSet.getInt("year");
                int producer_id = resultSet.getInt("producer_id");
                String country=resultSet.getString("country");
                String language=resultSet.getString("language");
                list.add(new Album(id, year, name, producer_id, country, language));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Album findById(long albumId) {
        Album album = null;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM albums WHERE id=" + albumId);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int year = resultSet.getInt("year");
                int producer_id = resultSet.getInt("producer_id");
                String country=resultSet.getString("country");
                String language=resultSet.getString("language");
                album = new Album(id, year, name, producer_id, country, language);
                break;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return album;
    }

    @Override
    public boolean delete(long id) {
        Album album = findById(id);

        try {
            statement.executeUpdate("DELETE FROM albums WHERE id"+ id);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }


    }
    @Override
    public boolean update(Album changed) {
        try {
            statement.executeUpdate("UPDATE albums SET name = '"+changed.getName()+"', year = '"+changed.getYear()+"', producer_id = '"+changed.getProducer_id()+ "', country = '"+changed.getCountry()+ "', language = '"+changed.getLanguage()+"' WHERE  id= "+ changed.getId()+";");
            return true; }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public void deleteAll(){

            try {
                statement.executeUpdate("DROP TABLE albums");
                statement.executeUpdate("DROP TABLE producers");
                statement.executeUpdate("DROP TABLE cars");
                statement.executeUpdate("DROP TABLE cats");
            } catch (SQLException e) {
                e.printStackTrace();
            }

    }

    @Override
    public boolean add(Album item) {
        try {
            statement.executeUpdate("INSERT INTO albums " +
                    "(name, year,producer_id,country,language)" +
                    " VALUES ('" + item.getName() + "' , '" + item.getYear() + item.getProducer_id()+item.getCountry()+item.getLanguage()+
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
    public boolean addAll(Collection<Album> collection) {
        String sqlQuery="INSERT INTO albums " +
                "(name, year, producer_id, country, language)" +
                " VALUES (?,?,?,?,?);";
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement =connection.prepareStatement(sqlQuery);
            for(Album album: collection) {
                statement.setString(1,album.getName());
                statement.setInt(2,album.getYear());
                statement.setInt(3, (int) album.getProducer_id());
                statement.setString(4,album.getCountry());
                statement.setString(5,album.getLanguage());
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

}
