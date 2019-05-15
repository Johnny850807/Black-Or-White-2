package com.pokewords.framework.views.helpers;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author Joanna
 */

public class Gallery {
    private String pathname;
    private int row;
    private int col;
    private int padding;

    public Gallery(String pathname, int row, int col, int padding) {
        this.pathname = pathname;
        this.row = row;
        this.col = col;
        this.padding = padding;  //TODO effect padding
    }

    /**
     * To get image
     * @param pic index
     * @return image
     */
    public Image getImage(int pic){
       return getImage(pic / col, pic % col);
    }

    /**
     * To get image
     * @param row the row index
     * @param col the column index
     * @return image
     */
    public Image getImage(int row, int col){
        File f = new File(pathname);
        try {
            BufferedImage bi = ImageIO.read(f);
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
        int gridWidth = imageWidth / this.col;
        int gridHeight = imageHeight / this.row;
        System.out.println(String.format("%d %d %d %d", gridWidth * col, gridHeight * row, gridWidth, gridHeight));
        return new Rectangle(gridWidth * col, gridHeight * row, gridWidth, gridHeight);
    }

    public static void main(String[] args) {
        String pathname = "D:/我的下載/bandit_0.bmp";
        int imgrow = 7, imgcol = 10;

        Gallery gallery = new Gallery(pathname, imgrow, imgcol, 0);

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
