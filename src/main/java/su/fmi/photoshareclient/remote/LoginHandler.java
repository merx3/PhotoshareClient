/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package su.fmi.photoshareclient.remote;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import su.fmi.photoshareclient.helpers.ProjectProperties;
import su.fmi.photoshareclient.ui.LoginGUI;

/**
 *
 * @author Merx3
 */
public class LoginHandler {

    private static String authStringEncr = null;

    public static boolean login(String username, char[] password) {
        try {
            ProjectProperties properties = new ProjectProperties();
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost("http://" + properties.get("socket") + properties.get("restEndpoint") + "/user/login");
            StringEntity input = new StringEntity("{\"username\":\"" + username + "\",\"password\":\"" + new String(password) + "\"}", 
                    ContentType.create("application/json"));
            post.setHeader("Content-Type", "application/json");
            post.addHeader(BasicScheme.authenticate(
                new UsernamePasswordCredentials(username, new String(password)),
                "UTF-8", false));
            post.setEntity(input);
            
            if(username.isEmpty() || password.length == 0){
                return false;
            }
            HttpResponse response = (HttpResponse) client.execute(post);
            if (response.getStatusLine().getStatusCode() == 200) {
                String authString = username + ":" + new String(password);
                byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
                authStringEncr = new String(authEncBytes);
                return true;
            } else {
                return false;
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(LoginGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LoginGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean register(String username, char[] password) {
        try {
            Random randomGenerator = new Random();
            ProjectProperties properties = new ProjectProperties();
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost("http://" + properties.get("socket") + properties.get("restEndpoint") + "/user/create");
            StringEntity input = new StringEntity("{\"firstName\":\"desktopUser\",\"lastName\":\"desktopUserLast\","
                    + "\"username\": \"" + username + "\", \"email\": \"res" + randomGenerator.nextInt() + "@example.com\","
                    + "\"password\": \"" + new String(password) + "\"}");
            input.setContentType("application/json");
            post.setEntity(input);
            HttpResponse response = (HttpResponse) client.execute(post);
            return true;
//                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
//                String line = "";
//                while ((line = rd.readLine()) != null) {
//                    System.out.println(line);
//                }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(LoginGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LoginGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static String getAuthStringEncripted() {
        return authStringEncr;
    }
}
