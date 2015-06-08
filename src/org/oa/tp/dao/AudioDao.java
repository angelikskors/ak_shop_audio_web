package org.oa.tp.dao;

import com.google.gson.Gson;
import org.oa.tp.data.Audio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


class AudioDao implements AbstractDao<Audio> {
     private static final String PATH = "audios.txt";
    List<Audio> list;
     private Statement statement;
     private Connection connection;
     private Set<Audio> items = new HashSet<>();
     public AudioDao(Statement statement, Connection connection) {
         this.statement = statement;
         this.connection=connection;

         try {

             String sql = "CREATE TABLE IF NOT EXISTS audios " +
                     "(id INTEGER AUTO_INCREMENT, " +
                     " name VARCHAR(255), " +
                     " author_id INTEGER, " +
                     " duration INTEGER, " +
                     " price DECIMAL(8,2) DEFAULT NULL, "+
                     " genre_id INTEGER, " +
                     " album_id INTEGER, " +
                     " PRIMARY KEY ( id ))";

             statement.executeUpdate(sql);
         } catch (SQLException e) {
             e.printStackTrace();
         }
     }
    @Override
    public List<Audio> loadAll() {

        list = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM audios");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int authorId = resultSet.getInt("author_id");
                int duration = resultSet.getInt("duration");
                double price = resultSet.getDouble("price");
                int genreId = resultSet.getInt("genre_id");
                int albumId = resultSet.getInt("album_id");

                list.add(new Audio(id,name,authorId,price,genreId,duration,albumId));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Audio findById(long audioId) {
        Audio audio  = null;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM audios WHERE id=" + audioId);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int authorId = resultSet.getInt("author_id");
                int duration = resultSet.getInt("duration");
                double price = resultSet.getDouble("price");
                int genreId = resultSet.getInt("genre_id");
                int albumId = resultSet.getInt("album_id");
                audio = new Audio(id,name,authorId,price,genreId,duration,albumId);
                break;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return audio;
    }

    @Override
    public boolean delete(long audioId) {
        Audio audio  = findById(audioId);

        try {
            statement.executeUpdate("DELETE FROM audios WHERE id=" + audio.getId());
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Audio changed) {
        try {
            statement.executeUpdate("UPDATE audios SET name = '"+changed.getName()+"', author_id = '"+changed.getAuthorId()+"', duration = '"+changed.getDuration()+ "', price = '"+changed.getPrice()+ "', genre_id = '"+changed.getGenreId()+"', album_id = '"+changed.getAlbumId()+"' WHERE  id= "+ changed.getId()+";");
            return true; }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean add(Audio item) {
        try {
            statement.executeUpdate("INSERT INTO audios " +
                    "(name, author_id,duration,price,genre_id,album_id)" +
                    " VALUES ('" + item.getName() + "' , '" + item.getAuthorId() + "' , '" +item.getDuration()+"' , '" +item.getPrice()+"' , '" +item.getGenreId()+"' , '" +item.getAlbumId()+
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
        String json = gson.toJson(list);
        try (FileWriter fileWriter = new FileWriter(new File(PATH))) {
            fileWriter.write(json);
            fileWriter.close();

        } catch (FileNotFoundException e) {
            Logger.getLogger("shop").log(Level.INFO, "Not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

     @Override
     public boolean addAll(Collection<Audio> collection) {
         String sqlQuery="INSERT INTO audios " +
                 "(name, author_id,duration,price,genre_id,album_id)" +
                 " VALUES (?,?,?,?,?,?);";
         try {
             connection.setAutoCommit(false);
             PreparedStatement statement =connection.prepareStatement(sqlQuery);
             for(Audio audio: collection) {
                 statement.setString(1, audio.getName());
                 statement.setInt(2, (int) audio.getAuthorId());
                 statement.setInt(3, audio.getDuration());
                 statement.setDouble(4, audio.getPrice());
                 statement.setInt(5, (int) audio.getGenreId());
                 statement.setInt(6, (int) audio.getAlbumId());

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
