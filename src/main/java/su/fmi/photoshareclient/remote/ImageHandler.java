/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package su.fmi.photoshareclient.remote;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.apache.commons.io.FilenameUtils;
import su.fmi.photoshareclient.helpers.Pagination;
import su.fmi.photoshareclient.helpers.images.ImageLabel;

/**
 *
 * @author Merx3
 *
 * Mocking server side logic
 *
 */
public class ImageHandler {

    private static ArrayList<ImageLabel> mockImages;

    static {
        try {
            mockImages = new ArrayList<ImageLabel>();
            int imageId = 1;

            ClassLoader classLoader = ImageHandler.class.getClassLoader();
            File mockImagesFolder = new File(classLoader.getResource("mock/files").getFile());

            for (File fileEntry : mockImagesFolder.listFiles()) {
                if (!fileEntry.isDirectory()) {
                    mockImages.add(new ImageLabel(ImageIO.read(fileEntry), imageId, fileEntry.getName()));
                    imageId++;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ImageHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ImageLabel getImage(int id) {
        for (ImageLabel img : mockImages) {
            if (img.getImageId() == id) {
                return img;
            }
        }
        return null;
    }

    public static void uploadImage(File img) {
        // read the image file
        FileInputStream fileReader = null;
        FileOutputStream outputStream = null;
        try {
            byte[] buffer = new byte[1000];
            fileReader = new FileInputStream(img.getAbsolutePath());
            // MOCK CODE
            ClassLoader classLoader = ImageHandler.class.getClassLoader();
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

    public static ArrayList<ImageLabel> getImages() {
        ArrayList<ImageLabel> images = new ArrayList<ImageLabel>();
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        for (ImageLabel mockImage : mockImages) {
            int imageWidth = mockImage.getImage().getWidth(null);
            int imageHeight = mockImage.getImage().getHeight(null);
            // just a relative estimation
            int imagesPerColumn = (int) Math.floor(Math.sqrt(Pagination.getImagesPerPage()));
            double ratio = (screenSize.height / (double)imageHeight < screenSize.width / (double)imageWidth)
                    ? screenSize.height / (double)imageHeight : screenSize.width / (double)imageWidth;
            ratio = ratio / imagesPerColumn; // reduce ratio because more than 1 image are located in the column
            int resizeWidth = (int)(imageWidth * ratio);
            int resizeHeight = (int)(imageHeight * ratio);
            Image resizedImage = createResizedCopy(mockImage.getImage(), resizeWidth, resizeHeight, false);
            images.add(new ImageLabel(resizedImage, mockImage.getImageId(), mockImage.getFileName()));
        }
        return images;
    }

    public static void deleteImage(int id) {
        for (ImageLabel img : mockImages) {
            if (img.getImageId() == id) {
                mockImages.remove(img);
                ClassLoader classLoader = ImageHandler.class.getClassLoader();
                String path = classLoader.getResource("mock/files/").getPath();
                File toDelete = new File(path, img.getFileName());
                toDelete.delete();
                return;
            }
        }
    }

    public static BufferedImage createResizedCopy(Image originalImage,
            int scaledWidth, int scaledHeight,
            boolean preserveAlpha) {
        int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
        Graphics2D g = scaledBI.createGraphics();
        if (preserveAlpha) {
            g.setComposite(AlphaComposite.Src);
        }
        g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
        g.dispose();
        return scaledBI;
    }
}
