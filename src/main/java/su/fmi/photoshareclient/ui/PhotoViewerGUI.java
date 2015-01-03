/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package su.fmi.photoshareclient.ui;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import su.fmi.photoshareclient.helpers.images.ImageLabel;
import su.fmi.photoshareclient.helpers.Pagination;
import su.fmi.photoshareclient.helpers.images.StretchIcon;
import su.fmi.photoshareclient.remote.ImageHandler;

/**
 *
 * @author Merx3
 */
public class PhotoViewerGUI extends javax.swing.JFrame {

    /**
     * Creates new form PhotoViewerGUI
     */
    public PhotoViewerGUI() {
        initComponents();

        remoteImagesHandler = new ImageHandler();
        List<ImageLabel> allImages = remoteImagesHandler.getImages();
        Pagination.setImages(allImages);
        
        refreshPage();
    }
    
    public void selectImage(ImageLabel img){
        if(this.selectedImage != img){
            this.selectedImage.deselect();
        }
        this.selectedImage = img;
    }

    private void refreshPage() {
        List<ImageLabel> pageImages = Pagination.getCurrentPageImages();
        jPanel1.removeAll();
        jPanel1.revalidate();
        jPanel1.repaint();
        for (ImageLabel img : pageImages) {
            jPanel1.add(img);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel6 = new javax.swing.JPanel();
        uploadButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        prevPageButton = new javax.swing.JButton();
        nextPageButton = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Photo Viewer");

        jPanel6.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel6.setPreferredSize(new java.awt.Dimension(704, 35));
        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.LINE_AXIS));

        uploadButton.setText("Upload");
        uploadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadButtonActionPerformed(evt);
            }
        });
        jPanel6.add(uploadButton);

        getContentPane().add(jPanel6, java.awt.BorderLayout.PAGE_START);

        jPanel1.setPreferredSize(new java.awt.Dimension(0, 300));
        jPanel1.setLayout(new java.awt.GridLayout(3, 5, 5, 5));
        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.setLayout(new java.awt.GridLayout(1, 3));

        jPanel4.setPreferredSize(new java.awt.Dimension(0, 0));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 283, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 35, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel4);

        jPanel3.setPreferredSize(new java.awt.Dimension(10, 35));

        prevPageButton.setText("«");
        prevPageButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                prevPageButtonMouseClicked(evt);
            }
        });
        jPanel3.add(prevPageButton);
        prevPageButton.getAccessibleContext().setAccessibleName("prevPage");

        nextPageButton.setText("»");
        nextPageButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nextPageButtonMouseClicked(evt);
            }
        });
        jPanel3.add(nextPageButton);
        nextPageButton.getAccessibleContext().setAccessibleName("nextPage");

        jPanel2.add(jPanel3);

        jPanel5.setPreferredSize(new java.awt.Dimension(0, 0));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 283, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 35, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel5);

        getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void uploadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uploadButtonActionPerformed
        if (uploadFile == null) {
            uploadFile = new JFileChooser();
        }

        //In response to a button click:
        int returnVal = uploadFile.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File uploadImage = uploadFile.getSelectedFile();
            Pagination.setImages(remoteImagesHandler.getImages());
            remoteImagesHandler.uploadImage(uploadImage);
        } else {
            System.out.println("File access cancelled by user.");
        }
    }//GEN-LAST:event_uploadButtonActionPerformed

    private void prevPageButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_prevPageButtonMouseClicked
        Pagination.previousPage();
        refreshPage();
    }//GEN-LAST:event_prevPageButtonMouseClicked

    private void nextPageButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nextPageButtonMouseClicked
        Pagination.nextPage();
        refreshPage();
    }//GEN-LAST:event_nextPageButtonMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PhotoViewerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PhotoViewerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PhotoViewerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PhotoViewerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PhotoViewerGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JButton nextPageButton;
    private javax.swing.JButton prevPageButton;
    private javax.swing.JButton uploadButton;
    // End of variables declaration//GEN-END:variables
    private javax.swing.JFileChooser uploadFile;
    private ImageHandler remoteImagesHandler;
    private ImageLabel selectedImage = null;
}
