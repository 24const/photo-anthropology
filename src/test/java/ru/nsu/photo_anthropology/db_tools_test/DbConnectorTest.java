package ru.nsu.photo_anthropology.db_tools_test;

import com.nsu.photo_anthropology.db_tools.DbConnector;
import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class DbConnectorTest {

    static DbConnector staticDbConnector;
    @BeforeClass
    public static void setup() {
        staticDbConnector = DbConnector.getInstance();
    }
    @Test
    public void firstInitDbConnectorTest() {
        DbConnector dbConnector =  DbConnector.getInstance();
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

    @Test(expected = PhotoAnthropologyRuntimeException.class)
    public void getFailedConnectionTest() throws SQLException {
        Connection connection = staticDbConnector.getConnection();
        connection.close();
        Connection failedConnection = staticDbConnector.getConnection();
        System.out.println(failedConnection);
    }
}
