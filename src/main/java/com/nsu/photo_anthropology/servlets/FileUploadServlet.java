package com.nsu.photo_anthropology.servlets;

import com.nsu.photo_anthropology.config_workers.GetPropertyValues;
import com.nsu.photo_anthropology.file_workers.FileSavingToDBWorker;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public class FileUploadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        boolean isMultipart = ServletFileUpload.isMultipartContent(req);

        GetPropertyValues properties = new GetPropertyValues();
        try {
            properties.getPropValues();
        } catch (Exception e) {
            Logger logger = Logger.getLogger(ChangeGroupServlet.class.getName());
            logger.info(e.getMessage());
            resp.sendRedirect("error_page.jsp");
        }

        String uploadedFilesDirectory = properties.getUploadedFilesDirectory();

        File uploadedFile;
        String filePath = "";

        if (isMultipart) {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            try {
                List items = upload.parseRequest(req);
                Iterator iterator = items.iterator();
                while (iterator.hasNext()) {
                    FileItem item = (FileItem) iterator.next();
                    if (!item.isFormField()) {
                        String fileName = item.getName();
                        String uploadedFilePath = uploadedFilesDirectory + fileName;
                        uploadedFile = new File(uploadedFilePath);
                        item.write(uploadedFile);
                        filePath = uploadedFilesDirectory + fileName;
                    }
                }
            } catch (Exception e) {
                Logger logger = Logger.getLogger(FileUploadServlet.class.getName());
                logger.info(e.getMessage());
                resp.sendRedirect("error_page.jsp");
            }

            FileSavingToDBWorker.saveFileInfo(filePath);
            try {
                resp.sendRedirect("index.jsp");
            } catch (Exception e) {
                Logger logger = Logger.getLogger(FileUploadServlet.class.getName());
                logger.info(e.getMessage());
                resp.sendRedirect("error_page.jsp");
            }
        }
    }
}