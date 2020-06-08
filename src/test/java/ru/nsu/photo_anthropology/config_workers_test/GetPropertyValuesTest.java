package ru.nsu.photo_anthropology.config_workers_test;

import com.nsu.photo_anthropology.config_workers.GetPropertyValues;
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
        Assert.assertEquals("ksu", user);
    }

    @Test
    public void getPasswordTest() {
        String password = getPropertyValues.getPassword();
        Assert.assertEquals("ksu", password);
    }

    @Test
    public void getDbUrlAddressTest() {
        String dbUrlAddress = getPropertyValues.getDbUrlAddress();
        Assert.assertEquals("jdbc:postgresql://192.168.1.44:5432/photo_anthropology", dbUrlAddress);
    }

    @Test
    public void getUploadedFilesDirectoryTest() {
        String uploadedFilesDirectory = getPropertyValues.getUploadedFilesDirectory();
        Assert.assertEquals("C://Users//Ksenia//Desktop//photo-anthropology//target//photo-anthropology//uploads//", uploadedFilesDirectory);
    }
}
