package newsreader.downloader;

import java.util.List;

public class SequentialDownloader extends newsreader.downloader.Downloader {

    @Override
    public int process(List<String> urls) {
        int count = 0;
        long timer = System.currentTimeMillis();
        for (String url : urls) {
            String fileName = saveUrl2File(url);
            if(fileName != null)
                count++;
        }
        System.out.println("Timer vs Serial: " + (System.currentTimeMillis() - timer));
        return count;
    }
}
