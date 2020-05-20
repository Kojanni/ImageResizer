import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ImageResizer implements Runnable {
    private ConcurrentLinkedQueue<File> queue;
    private int newWidth;
    private String dstFolder;
    private long start;
    private boolean isActive;

    public ImageResizer(ConcurrentLinkedQueue<File> queue, int newWidth, String dstFolder, long start) {
        this.queue = queue;
        this.newWidth = newWidth;
        this.dstFolder = dstFolder;
        this.start = start;
        this.isActive = true;
    }

    public void run() {
        do {
            try {
                File file = queue.poll();
                if (file == null) {
                    this.isActive = false;
                    System.out.println("Finished after start: " + (System.currentTimeMillis() - start) + " ms");
                } else {
                    BufferedImage image = ImageIO.read(file);

                    int newWidth = this.newWidth;
                    int newHeight = (int) Math.round(image.getHeight() / (image.getWidth() / (double) newWidth));

                    BufferedImage resizedImg = Scalr.resize(image, Scalr.Method.QUALITY, Scalr.Mode.AUTOMATIC, newWidth, newHeight, Scalr.OP_ANTIALIAS);
                    File newFile = new File(dstFolder + "/" + file.getName());
                    ImageIO.write(resizedImg, "jpg", newFile);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } while (isActive);

    }
}
