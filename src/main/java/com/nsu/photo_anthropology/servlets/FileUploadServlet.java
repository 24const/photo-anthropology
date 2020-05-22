package com.nsu.photo_anthropology.servlets;

import com.nsu.photo_anthropology.file_workers.FileSavingToDBWorker;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class FileUploadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String sourceFilePath = (String) req.getAttribute("sourceFilePath");
        boolean isMultipart = ServletFileUpload.isMultipartContent(req);

        File uploadedFile = null;
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
                        System.out.println("File name: " + fileName);
                        String root = getServletContext().getRealPath("/");
                        System.out.println("Root: "+root);
                        File path = new File(root + "/uploads");
                        if (!path.exists()) {
                            boolean status = path.mkdirs();
                        }
                        uploadedFile = new File(path + "/" + fileName);
                        item.write(uploadedFile);
                        filePath = path + "/" + fileName;
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            FileSavingToDBWorker.saveFileInfo(filePath);
            resp.sendRedirect("index.jsp");

        }
    }
}