package ru.nsu.photo_anthropology.config_workers_test;

import com.nsu.photo_anthropology.config_workers.GetPropertyValues;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class GetPropertyValuesTest {

    private GetPropertyValues getPropertyValues;

    @Before
    public void getPropValues() throws IOException {

        this.getPropertyValues = new GetPropertyValues();
        this.getPropertyValues.getPropValues();

    }

    @Test
    public void getUserTest() {
        String user = this.getPropertyValues.getUser();
        Assert.assertEquals("ksu", user);
    }

    @Test
    public void getPasswordTest() {
        String password = this.getPropertyValues.getPassword();
        Assert.assertEquals("ksu", password);
    }

    @Test
    public void getDbUrlAddressTest() {
        String dbUrlAddress = this.getPropertyValues.getDbUrlAddress();
        Assert.assertEquals("jdbc:postgresql://192.168.1.49:5432/photo_anthropology", dbUrlAddress);
    }

    @Test
    public void getUploadedFilesDirectoryTest() {
        String uploadedFilesDirectory = this.getPropertyValues.getUploadedFilesDirectory();
        Assert.assertEquals("C://Users//Ksenia//Desktop//photo-anthropology//target//photo-anthropology//uploads//", uploadedFilesDirectory);
    }
}
