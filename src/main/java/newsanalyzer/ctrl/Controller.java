package newsanalyzer.ctrl;

import com.fasterxml.jackson.databind.util.ClassUtil;
import newsanalyzer.ui.Menu;
import newsanalyzer.ui.UserInterface;
import newsapi.NewsApi;
import newsapi.NewsApiBuilder;
import newsapi.NewsApiException;
import newsapi.beans.Article;
import newsapi.beans.NewsReponse;
import newsapi.beans.Source;
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

		//UserInterface UI = new UserInterface(); //Test

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
				int articles_number = CounterOfArtic(NR);
				List<Article> articles = NR.getArticles();
				System.out.println("++++ Total Hit Count for the chosen Topic on News API: " + articles_number + " ++++ Branche: " + cat + " ++++ Shortes Author Name is: " + AuthorShort(articles));
				System.out.println("");
				articles.stream().forEach(article -> System.out.println(article.toString()));
			}

		}
		catch (NewsApiException e)
		{
			System.out.println("Die Haupt-Klassen-Objekte sind in einen Fehler gelaufen - bitte das Programm schließen");

		}

		}

		private static int CounterOfArtic(NewsReponse nr) throws NewsApiException
		{
			int counter = 0;
			counter = nr.getTotalResults();
			return counter;

		}

	private static String AuthorShort(List <Article> articles) throws NewsApiException
	{

		try {
			String shortest = null;

			for (Article a : articles) {

				if ((shortest == null) || (a.getAuthor().length() < shortest.length())) {
					shortest = a.getAuthor();
				}

			}

			return shortest;
		}
		catch(NullPointerException e)
		{
			System.out.println("Anscheinend war Magie im Spiel - Keine Autoren gefunden bzw. keine vorhanden");
			return "";
		}
	}

	private static String MostArticles(int ArtCount, List <Article> articles)
	{
		//TODO

		return "";
	}

	private static String AlphaSort(List <Article> aritcle)
	{
		//TODO
		return "";
	}

	public Object getData() {

		return null;
	}
}
