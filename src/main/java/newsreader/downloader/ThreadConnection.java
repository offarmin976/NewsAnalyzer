package newsreader.downloader;

import java.util.concurrent.Callable;

public class ThreadConnection implements Callable {
    private Downloader dwn_c;
    private String url_c;

    public ThreadConnection(ParalellDownloader paralellDownloader, String url) {

    }

    @Override
    public String call() throws Exception{
        String X = "";
        try { X = dwn_c.saveUrl2File(url_c);
        }
        catch (NullPointerException e) {
            System.out.println("");
        }
        return X;
    }
    public void ThreadC (Downloader downloader, String url_c)
    {
        this.dwn_c = downloader;
        this.url_c = url_c;
    }
}
