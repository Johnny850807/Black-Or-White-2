package com.pokewords.framework.views.helpers.galleries;

import com.pokewords.framework.commons.Range;
import com.pokewords.framework.engine.utils.ImageUtility;
import com.pokewords.framework.engine.utils.Resources;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The sequential gallery, it requires the resource homePath where the images are located.
 * The sequence will contain all the images in the homePath directory, it expects all the image file names
 * are in number form, (e.g. 0.png, 1.png, 2.png,...), so that it can determine the sequence order (ascending).
 * @author johnny850807 (waterball)
 */
public class SequenceGallery implements Gallery {
    private List<Image> imageSequence;
    private Range pictureRange;

    public SequenceGallery(String homePath, Range pictureRange) {
        this.imageSequence = readImageSequenceFromHomePath(homePath);
        this.pictureRange = pictureRange;
    }

    public SequenceGallery(List<Image> imageSequence, Range pictureRange) {
        this.imageSequence = imageSequence;
        this.pictureRange = pictureRange;
    }

    private  List<Image> readImageSequenceFromHomePath(String homePath) {
        File homeDir = Resources.get(homePath);
        List<File> picFiles = Arrays.asList(Objects.requireNonNull(homeDir.listFiles()));
        sortFilesByAscendingIndex(picFiles);
        return picFiles.stream()
                .map(ImageUtility::readImage)
                .collect(Collectors.toList());
    }

    private void sortFilesByAscendingIndex(List<File> picFiles) {
        picFiles.sort((f1, f2) -> {
            int f1Index = Integer.parseInt(f1.getName().split("\\.")[0]);
            int f2Index = Integer.parseInt(f2.getName().split("\\.")[0]);
            return f1Index - f2Index;
        });
    }

    @Override
    public Image getImage(int pic) {
        int index = pic - getPictureRange().getStart();
        return imageSequence.get(index);
    }


    @Override
    public Range getPictureRange() {
        return pictureRange;
    }

    @SuppressWarnings("Duplicates")
    public static void main(String[] args) {
        String pathname = "assets/sequences/loadingProgressBar";
        Gallery gallery = new SequenceGallery(pathname, new Range(0, 11));

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
                    pic = (pic+1) % 12;
                    jLabel.setIcon(new ImageIcon(gallery.getImage(pic)));
                }
            } catch (InterruptedException ignored) { }
        }).start();
    }
}
