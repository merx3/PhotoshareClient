/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package su.fmi.photoshareclient.remote;

import com.google.gson.Gson;
import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import su.fmi.photoshareclient.helpers.Pagination;
import su.fmi.photoshareclient.helpers.ProjectProperties;
import su.fmi.photoshareclient.helpers.RemoteImage;
import su.fmi.photoshareclient.helpers.images.ImageLabel;
//import su.fmi.photoshareclient.ui.LoginGUI;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author Merx3
 *
 * Mocking server side logic
 *
 */
public class ImageHandler {


    public static ImageLabel getImage(int id, String name) {
        try {
            // logic for user verification

//		Client client = ClientBuilder.newBuilder().newClient();
//            WebTarget target = client.target("http://94.156.77.61:8080/photoshare");
//            target = target.path("rest/image/getfile/MiltonStapler.jpg");
//
//            Invocation.Builder builder = target.request();
//            Response response = builder.get();
//            FileInputStream book = builder.get(FileInputStream.class);
//            System.out.println("done");
            ProjectProperties props = new ProjectProperties();
            String webPage = "http://" + props.get("socket") + props.get("restEndpoint") + "/image/getfile/" + name;
            URL url = new URL(webPage);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setRequestProperty("Authorization", "Basic " + LoginHandler.getAuthStringEncripted());
            InputStream is = urlConnection.getInputStream();
//            InputStreamReader isr = new InputStreamReader(is);
//            
//            int numCharsRead;
//            char[] charArray = new char[1024];
//            StringBuffer sb = new StringBuffer();
//            while ((numCharsRead = isr.read(charArray)) > 0) {
//                sb.append(charArray, 0, numCharsRead);
//            }
//            String result = sb.toString();
            BufferedImage bi = ImageIO.read(is);
            ImageLabel img = new ImageLabel(bi, id, name);
            return img;

//            System.out.println("*** BEGIN ***");
//            System.out.println(result);
//            System.out.println("*** END ***");
        } catch (MalformedURLException ex) {
            Logger.getLogger(LoginHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LoginHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void uploadImage(File img) {
        
      try {
            HttpClient httpclient = HttpClientBuilder.create().build();

            ProjectProperties props = new ProjectProperties();
            String endpoint = "http://" + props.get("socket") + props.get("restEndpoint") + "/image/create";
            HttpPost httppost = new HttpPost(endpoint);

            MultipartEntityBuilder  mpEntity = MultipartEntityBuilder.create();
            ContentBody cbFile = new FileBody(img, ContentType.MULTIPART_FORM_DATA, img.getName());
            mpEntity.addTextBody("fileName", img.getName());
            mpEntity.addTextBody("description", "File uploaded via Photoshare Desktop Cliend on " + new SimpleDateFormat("HH:mm dd/MM/yyyy").format(Calendar.getInstance().getTime()));
            
            mpEntity.addPart("fileUpload",  cbFile);

            httppost.setHeader("Authorization", "Basic " + LoginHandler.getAuthStringEncripted());
            
            httppost.setEntity(mpEntity.build());
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity resEntity = response.getEntity();

            if (resEntity != null) {
              System.out.println(EntityUtils.toString(resEntity));
            }
            if (resEntity != null) {
              EntityUtils.consume(resEntity);
            }

            httppost.releaseConnection();
        }catch (IOException ex) {
              Logger.getLogger(ImageHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ArrayList<ImageLabel> getImages() {
        ProjectProperties props = new ProjectProperties();
        String webPage = "http://" + props.get("socket") + props.get("restEndpoint") + "/image";
        URL url;
        try {
            url = new URL(webPage);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setRequestProperty("Authorization", "Basic " + LoginHandler.getAuthStringEncripted());
            InputStream is = urlConnection.getInputStream();

            InputStreamReader isr = new InputStreamReader(is);

            int numCharsRead;
            char[] charArray = new char[1024];
            StringBuffer sb = new StringBuffer();
            while ((numCharsRead = isr.read(charArray)) > 0) {
                sb.append(charArray, 0, numCharsRead);
            }
            String result = sb.toString();
            Gson gson = new Gson();

            RemoteImage[] remoteImages = gson.fromJson(result, RemoteImage[].class);

            ArrayList<ImageLabel> images = new ArrayList<ImageLabel>();
            Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
            for (RemoteImage rimg : remoteImages) {
                ImageLabel img = getImage(rimg.id, rimg.fileName);
                int imageWidth = img.getImage().getWidth(null);
                int imageHeight = img.getImage().getHeight(null);
                // just a relative estimation
                int imagesPerColumn = (int) Math.floor(Math.sqrt(Pagination.getImagesPerPage()));
                double ratio = (screenSize.height / (double) imageHeight < screenSize.width / (double) imageWidth)
                        ? screenSize.height / (double) imageHeight : screenSize.width / (double) imageWidth;
                ratio = ratio / imagesPerColumn; // reduce ratio because more than 1 image are located in the column
                int resizeWidth = (int) (imageWidth * ratio);
                int resizeHeight = (int) (imageHeight * ratio);
                Image resizedImage = createResizedCopy(img.getImage(), resizeWidth, resizeHeight, false);
                images.add(new ImageLabel(resizedImage, img.getImageId(), img.getFileName()));
            }
            return images;
        } catch (MalformedURLException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }

        return null;
    }

    public static void deleteImage(String fileName) {
        // TODO
//        for (ImageLabel img : mockImages) {
//            if (img.getImageId() == id) {
//                mockImages.remove(img);
//                ClassLoader classLoader = ImageHandler.class.getClassLoader();
//                String path = classLoader.getResource("mock/files/").getPath();
//                File toDelete = new File(path, img.getFileName());
//                toDelete.delete();
//                return;
//            }
//        }
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
