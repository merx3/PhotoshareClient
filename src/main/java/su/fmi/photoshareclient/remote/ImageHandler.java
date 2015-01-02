/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package su.fmi.photoshareclient.remote;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author Merx3
 */
public class ImageHandler {

    public Image getImage(int id) {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File mockImg = new File(classLoader.getResource("mock/files/de734763062959ce4ca78f8f118d4cbb.jpg").getFile());
            Image image = ImageIO.read(mockImg);
            return image;
        } catch (IOException ex) {
            Logger.getLogger(ImageHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void uploadImage(File img) {
        // read the image file
        FileInputStream fileReader = null;
        FileOutputStream outputStream = null;
        try {
            byte[] buffer = new byte[1000];
            fileReader = new FileInputStream(img.getAbsolutePath());
            // MOCK CODE
            ClassLoader classLoader = getClass().getClassLoader();
            File uploadImg = File.createTempFile("mock_",
                    "." + FilenameUtils.getExtension(img.getAbsolutePath()), 
                    new File(classLoader.getResource("mock/files/").getFile()));
            outputStream = new FileOutputStream(uploadImg.getAbsolutePath());
            while (fileReader.read(buffer) != -1) {
                outputStream.write(buffer);
            }
            // END MOCK CODE
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ImageHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ImageHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (fileReader != null) {
                    fileReader.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(ImageHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //upload the file 
    }

    public ArrayList<Image> getImages() {
        try {
            ArrayList<Image> mockImages = new ArrayList<Image>();

            ClassLoader classLoader = getClass().getClassLoader();
            File mockImagesFolder = new File(classLoader.getResource("mock/files").getFile());

            for (File fileEntry : mockImagesFolder.listFiles()) {
                if (!fileEntry.isDirectory()) {
                    mockImages.add(ImageIO.read(fileEntry));
                }
            }

            return mockImages;
        } catch (IOException ex) {
            Logger.getLogger(ImageHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
}
