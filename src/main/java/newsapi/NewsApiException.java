package newsapi;

public class NewsApiException extends Exception {

    public NewsApiException() {}

    public NewsApiException(String message)
    {
        super(message);

    }
    public NewsApiException(Throwable cause)
    {
        super(cause);
    }
    public NewsApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
