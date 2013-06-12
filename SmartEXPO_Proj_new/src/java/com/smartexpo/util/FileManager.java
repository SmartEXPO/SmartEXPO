/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.util;

import com.smartexpo.managedbean.ItemInsertMB;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Boy
 */
public class FileManager implements Serializable {

    private static FileManager fileManager;
    private String savedLocation;
    private static String Destination;
    private static String SubPath = "/web/upload/";

    private FileManager() {
        initDestination();
    }

    public static FileManager getInstance() {
        if (fileManager == null) {
            fileManager = new FileManager();
        }
        return fileManager;
    }

    public String processStore(UploadedFile uploadedFile, String subDir) {
        String contentType = uploadedFile.getContentType();
        String ext = contentType.substring(contentType.lastIndexOf("/") + 1, contentType.length());
        if (contentType.equals("audio/x-wav")) {
            ext = contentType.substring(contentType.lastIndexOf("-") + 1, contentType.length());
        }
        Logger.getLogger(FileManager.class.getName()).log(Level.WARNING, "contentType = {0}, ext = {1}", new Object[]{contentType, ext});
        savedLocation = Destination + subDir + uploadedFile.hashCode() + "." + ext;
        String URL = savedLocation.substring(savedLocation.indexOf("/upload/"), savedLocation.length());

        try {
            storeFile(savedLocation, uploadedFile.getInputstream());
        } catch (IOException ex) {
            Logger.getLogger(ItemInsertMB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return URL;
    }

    private void storeFile(String saveLocation, InputStream in) {
        OutputStream out;
        try {
            out = new FileOutputStream(new File(saveLocation));

            int read;
            byte[] bytes = new byte[4096];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            in.close();
            out.flush();
            out.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ItemInsertMB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ItemInsertMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean deleteFile(String fileAddress) {
        File file = new File(fileAddress);
        return file.delete();
    }

    private void initDestination() {
        String realPath = ((ServletContext) FacesContext.getCurrentInstance()
                .getExternalContext().getContext()).getRealPath("/");

        for (int i = 0; i < 3; ++i) {
            realPath = realPath.substring(0, realPath.lastIndexOf("/"));
        }
        realPath = realPath + SubPath;
        Destination = realPath;
    }
}
