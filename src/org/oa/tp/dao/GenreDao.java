package org.oa.tp.dao;

import com.google.gson.Gson;
import org.oa.tp.data.Genre;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

class GenreDao implements AbstractDao<Genre> {
     private static final String PATH = "genres.txt";
     private Statement statement;
     private Connection connection;
     private Set<Genre> items = new HashSet<>();

     public GenreDao(Statement statement, Connection connection) {
         this.statement = statement;
         this.connection=connection;

         try {
             String sql = "CREATE TABLE IF NOT EXISTS genres " +
                     "(id INTEGER AUTO_INCREMENT, " +
                     " name VARCHAR(255), " +
                     " PRIMARY KEY ( id ))";

             statement.executeUpdate(sql);
         } catch (SQLException e) {
             e.printStackTrace();
         }
     }

     @Override
    public List<Genre> loadAll() {

         List<Genre> list = new ArrayList<>();
         try {
             ResultSet resultSet = statement.executeQuery("SELECT * FROM genres");
             while (resultSet.next()) {
                 int id = resultSet.getInt("id");
                 String name = resultSet.getString("name");

                 list.add(new Genre(id, name));

             }
         } catch (SQLException e) {
             e.printStackTrace();
         }

         return list;
    }

    @Override
    public Genre findById(long genreId) {
        Genre genre = null;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM genres WHERE id=" + genreId);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");

                genre = new Genre(id,  name);
                break;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genre;
    }

    @Override
    public boolean delete(long genreId) {
        Genre genre  = findById(genreId);

        try {
            statement.executeUpdate("DELETE FROM genres WHERE id"+ genreId);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Genre changed) {
        try {
            statement.executeUpdate("UPDATE genres SET name = '"+changed.getName()+"' WHERE  id= "+ changed.getId()+";");
            return true; }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean add(Genre item) {
        try {
            statement.executeUpdate("INSERT INTO genres " +
                    "(name)" +
                    " VALUES ('" + item.getName() +
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
     public boolean addAll(Collection<Genre> collection) {
         String sqlQuery="INSERT INTO genres " +
                 "(name)" +
                 " VALUES (?);";
         try {
             connection.setAutoCommit(false);
             PreparedStatement statement =connection.prepareStatement(sqlQuery);
             for(Genre genre : collection) {
                 statement.setString(1,genre.getName());
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
             statement.executeUpdate("DROP TABLE genres");
         } catch (SQLException e) {
             e.printStackTrace();
         }


     }
 }
