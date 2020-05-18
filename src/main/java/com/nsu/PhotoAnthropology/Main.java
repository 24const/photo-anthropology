package com.nsu.PhotoAnthropology;

import com.nsu.PhotoAnthropology.DAO.Dao;
import com.nsu.PhotoAnthropology.DAO.FileDao;
import com.nsu.PhotoAnthropology.DAO.GroupDao;
import com.nsu.PhotoAnthropology.DAO.TagDao;
import com.nsu.PhotoAnthropology.DBClasses.DBConnector;
import com.nsu.PhotoAnthropology.StructureClasses.Group;
import com.nsu.PhotoAnthropology.StructureClasses.Tag;
import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args){

        DBConnector dbConnector = new DBConnector();
        Connection connection = dbConnector.getConnection();
        try {
            SqlFile sf = new SqlFile(new File("C:\\Users\\Эльдорадо\\Desktop\\photo-anthropology\\src\\main\\webapp\\WEB-INF\\sql-scripts\\init.sql"));
            sf.setConnection(connection);
            sf.execute();
        } catch (SQLException | IOException | SqlToolError e) {
            e.printStackTrace();
        } finally {
            try {
                dbConnector.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
//        Dao groupDao = new GroupDao();
//        groupDao.save(new Group("season", "What's the season?"));

//        Dao groupDao = new TagDao();
//        groupDao.save(new Tag("spring", 1));

        Dao groupDao = new FileDao();
        groupDao.save(new com.nsu.PhotoAnthropology.StructureClasses.File("index.txt", ""));
    }

}
