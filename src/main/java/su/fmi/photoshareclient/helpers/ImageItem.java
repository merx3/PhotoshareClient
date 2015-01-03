///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package su.fmi.photoshareclient.helpers;
//
//import java.awt.Graphics;
//import java.awt.Image;
//import java.awt.Point;
//import java.io.File;
//import java.io.IOException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.imageio.ImageIO;
//import javax.swing.ImageIcon;
//import javax.swing.JLabel;
//
///**
// *
// * @author Merx3
// */
//public class ImageItem extends JLabel {
//    private Image image;
//    private Point myLocation;
//
//    public ImageItem() {
//        /*
//         ImageIcon icon = new ImageIcon("D:\\Wallpapers\\Anime\\ahri13.jpg");
//         setIcon(icon);
//         // setMargin(new Insets(0,0,0,0));
//         setIconTextGap(0);
//         // setBorderPainted(false);
//         setBorder(null);
//         setText(null);
//         setSize(icon.getImage().getWidth(null), icon.getImage().getHeight(null));
//         */
//
//        if (image == null) {
//            try {
//                String imagePath = "D:\\Wallpapers\\Anime\\ahri13.jpg";
//                File image2 = new File(imagePath);
//                image = ImageIO.read(image2);
//            } catch (IOException ex) {
//                Logger.getLogger(ImageItem.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        if (myLocation == null) {
//            myLocation = new Point();
//        }
//    }
//
//    public void setImageLocation(int x, int y) {
//        System.out.println("OLD: " + this.myLocation.x + "," +this.myLocation.y);
//        this.myLocation.x = x;
//        this.myLocation.y = y;
//    }
//
//    public Point getImageLocation() {
//        return this.myLocation;
//    }
//
//    @Override
//    public void paintComponent(Graphics g) {
//        System.out.println("LOCATION:" + myLocation.x + "," + myLocation.y);
//        if (image == null) {
//            return;
//        }
//        int imageWidth = image.getWidth(this);
//        int imageHeight = image.getHeight(this);
//        Point imgLocation = getImageLocation();
//        g.drawImage(image, imgLocation.x, imgLocation.y, 100, 100, this);
//
////        for (int i = 0; i*imageWidth <= getWidth(); i++)
////            for(int j = 0; j*imageHeight <= getHeight();j++)
////                if(i+j>0) g.copyArea(0, 0, imageWidth, imageHeight, i*imageWidth, j*imageHeight);
//    }
//
//}
