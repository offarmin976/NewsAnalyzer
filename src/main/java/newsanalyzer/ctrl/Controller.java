package newsanalyzer.ctrl;

import com.fasterxml.jackson.databind.util.ClassUtil;
import newsanalyzer.ui.Menu;
import newsanalyzer.ui.UserInterface;
import newsapi.NewsApi;
import newsapi.NewsApiBuilder;
import newsapi.NewsApiException;
import newsapi.beans.Article;
import newsapi.beans.NewsReponse;
import newsapi.enums.Category;
import newsapi.enums.Country;
import newsapi.enums.Endpoint;
import newsapi.enums.SortBy;

import java.io.Console;
import java.text.Collator;
import java.util.*;

public class Controller {

	public static final String APIKEY = "76e6c312394046268793576291ff5c58";


	public void process(String Query, Category cat, Endpoint ep ) {

		UserInterface UI = new UserInterface();


		try
		{
			NewsApi newsapi = new NewsApiBuilder()
					.setApiKey(Controller.APIKEY)
					.setQ(Query)
					.setEndPoint(ep)
					.setSourceCategory(cat)
					.createNewsApi();

			NewsReponse NR = newsapi.getNews();

			if (NR != null) {
				int articles_number = NR.getTotalResults();
				List<Article> articles = NR.getArticles();
				System.out.println("++++ Total Hit Count for the chosen Topic on News API: " + articles_number + " ++++ Branche: " + cat + " ++++ Shortes Author Name is: " + AuthorShort(articles));

				articles.stream().forEach(article -> System.out.println(article.toString()));
			}


		}
		catch (NewsApiException e)
		{

		}


		}
		//TODO implement methods for analysis

	private static String AuthorShort(List <Article> articles)
	{

		String shortest = null;

		for (Article a: articles)
		{

			if ((shortest == null) || (a.getAuthor().length() < shortest.length()))
			{
				shortest = a.getAuthor();
			}

		}

		return shortest;
	}

	public Object getData() {

		return null;
	}
}
