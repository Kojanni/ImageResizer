import java.io.File;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Main
{
    private static int newWidth = 300;
    public static void main(String[] args)
    {
        String srcFolder = "E://JAVA/Pic/src";
        String dstFolder = "E://JAVA/Pic/dst";

        File srcDir = new File(srcFolder);

        long start = System.currentTimeMillis();

        File[] files = srcDir.listFiles();
        ConcurrentLinkedQueue<File> queue = new ConcurrentLinkedQueue<>();
        for (File f : files) {
            queue.add(f);
        }
        int processorsNumber = Runtime.getRuntime().availableProcessors();
        for (int x = 0; x < (processorsNumber); x++) {
            ImageResizer imageResizer = new ImageResizer(queue, newWidth, dstFolder, start);
            new  Thread(imageResizer).start();
        }
    }
}
