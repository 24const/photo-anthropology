package ru.nsu.photo_anthropology.db_tools_test;

import com.nsu.photo_anthropology.db_tools.DbConnector;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;

public class DbConnectorTest {

    static DbConnector staticDbConnector;

    @BeforeClass
    public static void setup() {
        staticDbConnector = DbConnector.getInstance();
    }

    @Test
    public void firstInitDbConnectorTest() {
        DbConnector dbConnector = DbConnector.getInstance();
        Assert.assertNotNull(dbConnector);
    }

    @Test
    public void secondInitDbConnectorTest() {
        DbConnector newDbConnection = DbConnector.getInstance();
        Assert.assertEquals(staticDbConnector, newDbConnection);
    }

    @Test
    public void getConnectionTest() {
        Connection connection = staticDbConnector.getConnection();
        Assert.assertNotNull(connection);
    }
}
