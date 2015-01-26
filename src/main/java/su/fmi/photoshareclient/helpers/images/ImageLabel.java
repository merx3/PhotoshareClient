/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package su.fmi.photoshareclient.helpers.images;

import su.fmi.photoshareclient.helpers.images.StretchIcon;
import java.awt.BasicStroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import su.fmi.photoshareclient.helpers.Pagination;
import su.fmi.photoshareclient.remote.ImageHandler;
import su.fmi.photoshareclient.ui.PhotoViewDialog;

/**
 *
 * @author Merx3
 */
public class ImageLabel extends JLabel implements MouseListener {

    private Image iconImage;
    private String fileName;
    private int imageId;
    private boolean isSelected = false;
    private static final int BORDER_SIZE_PERCENTAGE = 2;

    public ImageLabel(String imgLocation, int id, String fileName) {
        this(new StretchIcon(imgLocation), id);
        this.fileName = fileName;
    }
    
    public ImageLabel(Image img, int id, String fileName) {
        this(new StretchIcon(img), id);
        this.fileName = fileName;
    }

    private ImageLabel(StretchIcon icon, int id) {
        this.iconImage = icon.getImage();
        this.imageId = id;
        setIcon(icon);
//    // setMargin(new Insets(0,0,0,0));
        setIconTextGap(0);
//    // setBorderPainted(false);
        setBorder(null);
        setText(null);
//    setSize(icon.getImage().getWidth(null), icon.getImage().getHeight(null));
        addMouseListener(this);
    }
    
    public Image getImage(){
        return this.iconImage;
    }
    
    public int getImageId(){
        return this.imageId;
    }
    
    public String getFileName(){
        return this.fileName;
    }

    public void deselect() {
        this.isSelected = false;
        setIcon(new StretchIcon(this.iconImage));
    }

    @Override
    public void mouseClicked(MouseEvent e) {        
        if (e.getClickCount() == 2 && !e.isConsumed()) {
            e.consume();
            ImageLabel fullScaleImage = ImageHandler.getImage(this.getImageId(), this.getFileName());
            JDialog viewImageDialog = new PhotoViewDialog(this.getOwningFrame(this), true, fullScaleImage);
            viewImageDialog.setLocationRelativeTo(this.getParent());
            viewImageDialog.setVisible(true);
       }
       else if (!this.isSelected) {
            int borderSize = (BORDER_SIZE_PERCENTAGE * 
                    Math.max(this.iconImage.getWidth(null), this.iconImage.getHeight(null))) / 100;

            BufferedImage bi = new BufferedImage(this.iconImage.getWidth(null) + 2 * borderSize,
                    this.iconImage.getHeight(null) + 2 * borderSize, BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.createGraphics();
            // g.drawImage(image, 0, 0, image.getWidth(null), image.getHeight(null), null, null);  
            g.setColor(Color.blue);
            g.fillRect(0, 0, this.iconImage.getWidth(null) + 2 * borderSize,
                    this.iconImage.getHeight(null) + 2 * borderSize);
            g.setColor(Color.white);
            g.fillRect(borderSize / 2, borderSize / 2, this.iconImage.getWidth(null) + borderSize,
                    this.iconImage.getHeight(null) + borderSize);
            g.drawImage(this.iconImage, borderSize, borderSize, null);
            setIcon(new StretchIcon(bi));
            this.isSelected = true;
            Pagination.setSelectImage(this);
            g.dispose();
        }
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
    
    private static Frame getOwningFrame(Component comp) {
        if (comp == null) {
          throw new IllegalArgumentException("null Component passed");
        }

        if (comp instanceof Frame) {
          return (Frame) comp;
        }
        return getOwningFrame(SwingUtilities.windowForComponent(comp));
    }
}
