package com.pokewords.framework.views.helpers.galleries;

import com.pokewords.framework.commons.Range;
import com.pokewords.framework.commons.utils.Resources;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author Joanna
 */
public class SheetGallery implements Gallery {
    private String pathname;
    private Range pictureRange;
    private int row;
    private int col;
    private int padding;

    public SheetGallery(String pathname, Range pictureRange, int row, int col) {
        this(pathname, pictureRange, row, col, 0);
    }

    public SheetGallery(String pathname, Range pictureRange, int row, int col, int padding) {
        this.pathname = pathname;
        this.pictureRange = pictureRange;
        this.row = row;
        this.col = col;
        this.padding = padding;
    }

    @Override
    public Range getPictureRange() {
        return pictureRange;
    }

    /**
     * To get image
     * @param pic the picture's number
     * @return image
     */
    @Override
    public Image getImage(int pic){
       int index = pic - pictureRange.getStart();
       return getImage(index / col, index % col);
    }

    /**
     * To get image
     * @param row the row index
     * @param col the column index
     * @return image
     */
    public Image getImage(int row, int col){
        try {
            BufferedImage bi = ImageIO.read(Resources.get(pathname));
            int imageWidth = bi.getWidth();
            int imageHeight = bi.getHeight();
            Rectangle rectangle = getSubimageRectangle(imageWidth, imageHeight, row, col);
            return bi.getSubimage(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Rectangle getSubimageRectangle(int imageWidth, int imageHeight, int row, int col) {
        int gridWidth = (imageWidth - padding * (this.col)) / this.col;
        int gridHeight = (imageHeight - padding * (this.row)) / this.row;
        System.out.println(String.format("%d %d %d %d", gridWidth * col, gridHeight * row, gridWidth, gridHeight));
        return new Rectangle((gridWidth + padding) * col, (gridHeight + padding) * row, gridWidth, gridHeight);
    }

    public static void main(String[] args) {
        String pathname = "assets/sheets/loadingText.png";
        int imgrow = 3, imgcol = 4;

        Gallery gallery = new SheetGallery(pathname, new Range(0, 11), imgrow, imgcol, 1);

        JFrame jf = new JFrame("");
        JLabel jLabel = new JLabel();
        JScrollPane scrollPane = new JScrollPane(jLabel);
        jLabel.setIcon(new ImageIcon(gallery.getImage(0)));

        jf.getContentPane().add(scrollPane);
        jf.pack();
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);

        new Thread(()-> {
            int pic = 0;
            try {
                while(true)
                {
                    Thread.sleep(100);
                    pic = (pic+1) % (imgrow*imgcol);
                    jLabel.setIcon(new ImageIcon(gallery.getImage(pic)));
                }
            } catch (InterruptedException ignored) { }
        }).start();
    }

}
