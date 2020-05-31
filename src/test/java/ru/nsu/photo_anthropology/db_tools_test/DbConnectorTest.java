package ru.nsu.photo_anthropology.db_tools_test;

import com.nsu.photo_anthropology.db_tools.DbConnector;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;

public class DbConnectorTest {

    DbConnector dbConnector;

    @Test
    public void firstInitDbConnectorTest() {
        dbConnector = DbConnector.getInstance();
        Assert.assertNotNull(dbConnector);
    }

    @Test
    public void secondInitDbConnectorTest() {
        DbConnector newDbConnection = DbConnector.getInstance();
        Assert.assertEquals(dbConnector, newDbConnection);
    }

    @Test
    public void getConnectionTest() {
        Connection connection = dbConnector.getConnection();
        Assert.assertNotNull(connection);
    }

    //TODO: не хватает теста, где соединение не было установлено, и как ведет себя DbConnector в этом случае?

}
