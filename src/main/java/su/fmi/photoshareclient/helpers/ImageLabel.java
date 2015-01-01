/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package su.fmi.photoshareclient.helpers;

import java.awt.BasicStroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Merx3
 */
public class ImageLabel extends JLabel implements MouseListener{
  private ImageIcon icon; 
  
  public ImageLabel(String img) {
    this(new StretchIcon(img));
  }

  public ImageLabel(ImageIcon icon) {
     this.icon = icon;
    setIcon(icon);
//    // setMargin(new Insets(0,0,0,0));
    setIconTextGap(0);
//    // setBorderPainted(false);
    setBorder(null);
    setText(null);
//    setSize(icon.getImage().getWidth(null), icon.getImage().getHeight(null));
      addMouseListener(this);
  }

    @Override
    public void mouseClicked(MouseEvent e) {
        Image image = icon.getImage();   
        int borderSize = 2;
         
        BufferedImage bi = new BufferedImage(image.getWidth(null) + 2*borderSize, image.getHeight(null) + 2*borderSize, BufferedImage.TYPE_INT_ARGB);  
        Graphics g = bi.createGraphics();  
       // g.drawImage(image, 0, 0, image.getWidth(null), image.getHeight(null), null, null);  
        g.setColor(Color.blue);
        g.fillRect(0, 0, image.getWidth(null) + 2*borderSize, image.getHeight(null) + 2*borderSize);
        g.drawImage(image, borderSize, borderSize, null);
        System.out.println("DRUN");
        this.icon = new StretchIcon(bi); 
        g.dispose();
        
        setIcon(this.icon);
    }

    @Override
    public void mousePressed(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
