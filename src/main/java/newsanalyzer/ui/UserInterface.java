package newsanalyzer.ui;


import java.io.*;
import java.util.List;
import java.util.Scanner;

import newsanalyzer.ctrl.Controller;
import newsapi.NewsApi;
import newsapi.NewsApiBuilder;
import newsapi.beans.Article;
import newsapi.beans.NewsReponse;
import newsapi.enums.Category;
import newsapi.enums.Country;
import newsapi.enums.Endpoint;

public class UserInterface 
{
	private Controller ctrl = new Controller();

	public void getDataFromCtrl1(){

		String Query = "";
		System.out.println("****** Bitte um Eingabe des Suchbegriffes in der Gesundheitsbranche:");
		Scanner sc = new Scanner(System.in);
		Query = sc.nextLine();

		NewsApi newsapi_tech = new NewsApiBuilder()
				.setApiKey(Controller.APIKEY)
				.setQ(Query)
				.setEndPoint(Endpoint.TOP_HEADLINES)
				.setSourceCategory(Category.health)
				.createNewsApi();

	}
	public void getDataFromCtrl2(){

		Article arr = new Article();
		String NameAuth = arr.getAuthor();
		String Short = "";
		Short = ctrl.AuthorShort(NameAuth);

		String Query = "";
		System.out.println("****** Bitte um Eingabe des Suchbegriffes in der Techbranche:");
		Scanner sc = new Scanner(System.in);
		Query = sc.nextLine();

		ctrl.process(Query, Category.technology, Endpoint.TOP_HEADLINES);




	}
	public void getDataFromCtrl3(){

		Article arr = new Article();
		String NameAuth = arr.getAuthor();
		String Short = "";
		Short = ctrl.AuthorShort(NameAuth);

		NewsApi newsapi_sports = new NewsApiBuilder()
				.setApiKey(Controller.APIKEY)
				.setEndPoint(Endpoint.TOP_HEADLINES)
				.setSourceCategory(Category.sports)
				.createNewsApi();



		NewsReponse NR_Sport = newsapi_sports.getNews();

		if(NR_Sport != null){
			int articles_number = NR_Sport.getTotalResults();
			System.out.println("++++ Total Hit Count for the chosen Topic on News API: " + articles_number + " ++++ Branche: " + Country.us + " ++++ Shortes Author Name is: " + Short);
			List<Article> articles = NR_Sport.getArticles();
			articles.stream().forEach(article -> System.out.println(article.toString()));
		}


	}
	
	public void getDataForCustomInput()
	{
		try
		{
		InputStream input = new BufferedInputStream(new FileInputStream("exception-log.txt"));
		byte[] buffer = new byte[8192];

			try {
				System.out.println("EXC FILE OUTPUT: ");
				for (int length = 0; (length = input.read(buffer)) != -1; ) {
					System.out.write(buffer, 0, length);
				}
			} finally {
				input.close();
			}
			System.out.println(" +++ END OF LINES +++ ");
		}
		catch (IOException e) {
			System.out.println("EXCEPTION LOG FILE READING INTERRUPTED - ASK PROGRAMMER FOR HELP");
		}
	}
	public void start() {
		Menu<Runnable> menu = new Menu<>("User Interfacx");
		menu.setTitel("PROG2 NEWS API  - Wählen Sie aus:");
		menu.insert("a", "** Aktuelle Nachrichten nach Suche", this::getDataFromCtrl1);
		menu.insert("b", "** Aktuelle Nachrichten aus der Techwelt", this::getDataFromCtrl2);
		menu.insert("c", "** Aktuelle Sport Nachrichten", this::getDataFromCtrl3);
		menu.insert("d", "** Exception Log Output",this::getDataForCustomInput);
		menu.insert("q", "** Programm beenden", null);
		Runnable choice;
		while ((choice = menu.exec()) != null) {
			 choice.run();
		}
		System.out.println("Program finished");
	}

    protected String readLine() {
		String value = "\0";
		BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
		try {
			value = inReader.readLine();
        } catch (IOException ignored) {
		}
		return value.trim();
	}

	protected Double readDouble(int lowerlimit, int upperlimit) 	{
		Double number = null;
        while (number == null) {
			String str = this.readLine();
			try {
				number = Double.parseDouble(str);
            } catch (NumberFormatException e) {
                number = null;
				System.out.println("Please enter a valid number:");
				continue;
			}
            if (number < lowerlimit) {
				System.out.println("Please enter a higher number:");
                number = null;
            } else if (number > upperlimit) {
				System.out.println("Please enter a lower number:");
                number = null;
			}
		}
		return number;
	}
}
