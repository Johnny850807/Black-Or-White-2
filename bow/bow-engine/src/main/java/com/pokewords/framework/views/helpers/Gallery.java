package com.pokewords.framework.views.helpers;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author Joanna
 */

public class Gallery {
    private String pathname;
    private int row;
    private int col;

    public Gallery(String pathname, int row, int col) {
        this.pathname = pathname;
        this.row = row;
        this.col = col;
    }

    /**
     * To get image
     * @param pic index
     * @return image
     */
    public Image getImage(int pic){
       return getImage(pic / row - 1, pic % col);
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
        String pathname = "D:\\下載區\\bandit_0.bmp";
        int imgrow = 7, imgcol = 10;
        int row = 3, col = 4;

        Gallery gallery = new Gallery(pathname, imgrow, imgcol);
        BufferedImage image = (BufferedImage) gallery.getImage(row, col);

        JFrame jf = new JFrame("");
        JScrollPane scrollPane = new JScrollPane(new JLabel(new ImageIcon(image)));

        jf.getContentPane().add(scrollPane);
        jf.pack();
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setTitle(pathname+" "+image.getWidth()+"x"+image.getHeight());
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
    }
}
