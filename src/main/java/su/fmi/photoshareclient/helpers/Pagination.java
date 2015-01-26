/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package su.fmi.photoshareclient.helpers;

import java.awt.Image;
import java.util.List;
import su.fmi.photoshareclient.helpers.images.ImageLabel;

/**
 *
 * @author Merx3
 */
public class Pagination {

    private static List<ImageLabel> images;
    private static ImageLabel selectedImage;
    private static int imagesPerPage;
    private static int pageNumber;

    static {
        imagesPerPage = 6;
        pageNumber = 1;
        selectedImage = null;
    }
    
    public static List<ImageLabel> getImages() {
        return images;
    }

    public static void setImages(List<ImageLabel> imgs) {
        images = imgs;
    }

    public static int getImagesPerPage() {
        return imagesPerPage;
    }

    public static void setImagesPerPage(int imgsPerPage) {
        imagesPerPage = imgsPerPage;
    }

    public static int getPageNumber() {
        return pageNumber;
    }

    public static void setSelectImage(ImageLabel image) {
        if (selectedImage != null) {
            selectedImage.deselect();
        }
        selectedImage = image;
    }
    
    public static void removeImage(String imageName){
        for (ImageLabel image : images) {
            if(image.getFileName() == imageName){
                images.remove(image);
                return;
            }
        }
    }

    public static ImageLabel getSelectedImage() {
        return selectedImage;
    }

    public static List<ImageLabel> getCurrentPageImages() {
        int lastImageIndex = (pageNumber * imagesPerPage > images.size())
                ? images.size() : pageNumber * imagesPerPage;
        List<ImageLabel> pageImages = images.subList(
                (pageNumber - 1) * imagesPerPage,
                lastImageIndex);
        return pageImages;
    }

    public static void nextPage() {
        if (pageNumber * imagesPerPage >= images.size()) {
            pageNumber = 1;
        } else {
            pageNumber++;
        }
    }

    public static void previousPage() {
        if (pageNumber == 1) {
            pageNumber = (int) Math.ceil(images.size() / (double) imagesPerPage);
        } else {
            pageNumber--;
        }
    }
}
