package ru.nsu.photo_anthropology.config_workers_test;

import com.nsu.photo_anthropology.config.GetPropertyValues;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

public class GetPropertyValuesTest {

    private static GetPropertyValues getPropertyValues;

    @BeforeClass
    public static void getPropValues() throws IOException {
        getPropertyValues = new GetPropertyValues();
        getPropertyValues.getPropValues();
    }

    @Test
    public void getUserTest() {
        String user = getPropertyValues.getUser();
        Assert.assertNotNull(user);
    }

    @Test
    public void getPasswordTest() {
        String password = getPropertyValues.getPassword();
        Assert.assertNotNull(password);
    }

    @Test
    public void getDbUrlAddressTest() {
        String dbUrlAddress = getPropertyValues.getDbUrlAddress();
        Assert.assertNotNull(dbUrlAddress);
    }

    @Test
    public void getUploadedFilesDirectoryTest() {
        String uploadedFilesDirectory = getPropertyValues.getUploadedFilesDirectory();
        Assert.assertEquals("target//photo-anthropology//uploads//", uploadedFilesDirectory);
    }
}
