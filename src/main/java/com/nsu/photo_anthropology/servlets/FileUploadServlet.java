package com.nsu.photo_anthropology.servlets;

import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;
import com.nsu.photo_anthropology.file_workers.FileSavingToDBWorker;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Iterator;
import java.util.List;

public class FileUploadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        boolean isMultipart = ServletFileUpload.isMultipartContent(req);

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
                        String root = getServletContext().getRealPath("/");
                        String rootPath = root + "/uploads";
                        File path = new File(rootPath);
                        String uploadedFilePath = path + "/" + fileName;
                        uploadedFile = new File(uploadedFilePath);
                        item.write(uploadedFile);
                        filePath = path + "/" + fileName;
                    }
                }
            } catch (Exception e) {
                throw new PhotoAnthropologyRuntimeException("FileUploadServlet: ошибка призагрузке файла на сервер.");
            }

            FileSavingToDBWorker.saveFileInfo(filePath);
            try {
                resp.sendRedirect("index.jsp");
            } catch (Exception e) {
                throw new PhotoAnthropologyRuntimeException("FileUploadServlet: ошибка при переходе на страницу index.jsp.");
            }
        }
    }
}