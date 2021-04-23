package newsapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import newsapi.beans.NewsReponse;
import newsapi.enums.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NewsApi {

    public static final String DELIMITER = "&";

    public static final String NEWS_API_URL = "http://newsapi.org/v2/%s?q=%s&apiKey=%s";

    private Endpoint endpoint;
    private String q;
    private String qInTitle;
    private Country sourceCountry;
    private Category sourceCategory;
    private String domains;
    private String excludeDomains;
    private String from;
    private String to;
    private Language language;
    private SortBy sortBy;
    private String pageSize;
    private String page;
    private String apiKey;


    public Endpoint getEndpoint() {
        return endpoint;
    }

    public String getQ() {
        return q;
    }

    public String getqInTitle() {
        return qInTitle;
    }

    public Country getSourceCountry() {
        return sourceCountry;
    }

    public Category getSourceCategory() {
        return sourceCategory;
    }

    public String getDomains() {
        return domains;
    }

    public String getExcludeDomains() {
        return excludeDomains;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public Language getLanguage() {
        return language;
    }

    public SortBy getSortBy() {
        return sortBy;
    }

    public String getPageSize() {
        return pageSize;
    }

    public String getPage() {
        return page;
    }

    public String getApiKey() {
        return apiKey;
    }

    public NewsApi(String q, String qInTitle, Country sourceCountry, Category sourceCategory, String domains, String excludeDomains, String from, String to, Language language, SortBy sortBy, String pageSize, String page, String apiKey, Endpoint endpoint) {
        this.q = q;
        this.qInTitle = qInTitle;
        this.sourceCountry = sourceCountry;
        this.sourceCategory = sourceCategory;
        this.domains = domains;
        this.excludeDomains = excludeDomains;
        this.from = from;
        this.to = to;
        this.language = language;
        this.sortBy = sortBy;
        this.pageSize = pageSize;
        this.page = page;
        this.apiKey = apiKey;
        this.endpoint = endpoint;
    }

    protected String requestData() throws NewsApiException {
        String url = buildURL();
        System.out.println("URL: "+url);
        URL obj = null;
        try {
            obj = new URL(url);
        } catch (MalformedURLException e) {
            // TOOO improve ErrorHandling
            throw new NewsApiException("MalformedURLEXCP");

            System.out.println("no legal protocol could be found in a specification string or the string could not be parsed.");
            try{
                FileWriter fstream_malformedURL_EXC = new FileWriter("exception-log.txt", true);
                BufferedWriter out = new BufferedWriter(fstream_malformedURL_EXC);
                PrintWriter pWriter = new PrintWriter(out, true);
                e.printStackTrace(pWriter);

            }
            catch(Exception ie)
            {
                throw new RuntimeException("Could not write Exception to file We should pray", ie);

            }

            e.printStackTrace();
        }
        HttpURLConnection con;
        StringBuilder response = new StringBuilder();
        try {
            con = (HttpURLConnection) obj.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } catch (IOException e) {


            System.out.println("a problem occured - produced by failed or interrupted I/O operations.");
            try{
                FileWriter fstream_IO_EXC = new FileWriter("exception-log.txt", true);
                BufferedWriter out = new BufferedWriter(fstream_IO_EXC);
                PrintWriter pWriter = new PrintWriter(out, true);
                e.printStackTrace(pWriter);

            }
            catch(Exception ie)
            {
                throw new RuntimeException("Could not write Exception to file We should pray", ie);
            }

            System.out.println("Error "+e.getMessage());
        }
        return response.toString();
    }

    protected String buildURL() {
        // TODO ErrorHandling

        try {
            String urlbase = String.format(NEWS_API_URL, getEndpoint().getValue(), getQ(), getApiKey());
            StringBuilder sb = new StringBuilder(urlbase);

            if (getFrom() != null) {
                sb.append(DELIMITER).append("from=").append(getFrom());
            }
            if (getTo() != null) {
                sb.append(DELIMITER).append("to=").append(getTo());
            }
            if (getPage() != null) {
                sb.append(DELIMITER).append("page=").append(getPage());
            }
            if (getPageSize() != null) {
                sb.append(DELIMITER).append("pageSize=").append(getPageSize());
            }
            if (getLanguage() != null) {
                sb.append(DELIMITER).append("language=").append(getLanguage());
            }
            if (getSourceCountry() != null) {
                sb.append(DELIMITER).append("country=").append(getSourceCountry());
            }
            if (getSourceCategory() != null) {
                sb.append(DELIMITER).append("category=").append(getSourceCategory());
            }
            if (getDomains() != null) {
                sb.append(DELIMITER).append("domains=").append(getDomains());
            }
            if (getExcludeDomains() != null) {
                sb.append(DELIMITER).append("excludeDomains=").append(getExcludeDomains());
            }
            if (getqInTitle() != null) {
                sb.append(DELIMITER).append("qInTitle=").append(getqInTitle());
            }
            if (getSortBy() != null) {
                sb.append(DELIMITER).append("sortBy=").append(getSortBy());
            }
            return sb.toString();
        }
        catch(Exception e)
        {
            System.out.println("a problem occured - produced probably by a missing value operation.");
            try{
                FileWriter fstream_IO_EXC = new FileWriter("exception-log.txt", true);
                BufferedWriter out = new BufferedWriter(fstream_IO_EXC);
                PrintWriter pWriter = new PrintWriter(out, true);
                e.printStackTrace(pWriter);

            }
            catch(IOException eio)
            {
                throw new RuntimeException("Could not write Exception to file We should pray", eio);

            }
        }
        return "";
    }

    public NewsReponse getNews() {

        NewsReponse newsReponse = null;
        String jsonResponse = requestData();
        if (jsonResponse != null && !jsonResponse.isEmpty()) {

            ObjectMapper objectMapper = new ObjectMapper();
            try {
                newsReponse = objectMapper.readValue(jsonResponse, NewsReponse.class);
                if (!"ok".equals(newsReponse.getStatus())) {
                    System.out.println("Error: " + newsReponse.getStatus());
                }
            } catch (JsonProcessingException ej) {
                System.out.println("Error: " + ej.getMessage());

            }

        }

        return newsReponse;
}}

