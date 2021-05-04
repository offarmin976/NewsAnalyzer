package newsanalyzer.ui;


import java.io.*;
import java.util.List;
import java.util.Scanner;

import newsanalyzer.ctrl.Controller;
import newsapi.NewsApi;
import newsapi.NewsApiBuilder;
import newsapi.NewsApiException;
import newsapi.beans.Article;
import newsapi.beans.NewsReponse;
import newsapi.enums.Category;
import newsapi.enums.Country;
import newsapi.enums.Endpoint;
import newsreader.downloader.Downloader;
import newsreader.downloader.ParalellDownloader;
import newsreader.downloader.SequentialDownloader;

public class UserInterface 
{
	private Controller ctrl = new Controller();

	private Downloader dwn = new Downloader() {
		@Override
		public int process(List<String> urls) {
			return 0;
		}
	};

	String GLOBAL_QUERY = "";

	public void getDataFromCtrl1(){

		String Query = "";
		System.out.println("****** Bitte um Eingabe des Suchbegriffes in der Gesundheitsbranche:");
		Scanner sc = new Scanner(System.in);
		Query = sc.nextLine();

		ctrl.process(Query, Category.health, Endpoint.TOP_HEADLINES);
		GLOBAL_QUERY = Query;
	}
	public void getDataFromCtrl2(){

		String Query = "";
		System.out.println("****** Bitte um Eingabe des Suchbegriffes in der Techbranche:");
		Scanner sc = new Scanner(System.in);
		Query = sc.nextLine();

		ctrl.process(Query, Category.technology, Endpoint.TOP_HEADLINES);
		GLOBAL_QUERY = Query;

	}
	public void getDataFromCtrl3(){

		String Query = "";
		System.out.println("****** Bitte um Eingabe des Suchbegriffes in der Sportbranche:");
		Scanner sc = new Scanner(System.in);
		Query = sc.nextLine();

		ctrl.process(Query, Category.sports, Endpoint.TOP_HEADLINES);
		GLOBAL_QUERY = Query;
	}

	public void getDataFromCtrl4() throws NullPointerException{

		String Query = "Corona";

		try
		{
			NewsApi newsapi = new NewsApiBuilder()
					.setApiKey(Controller.APIKEY)
					.setQ(GLOBAL_QUERY)
					.setEndPoint(Endpoint.TOP_HEADLINES)
					.setSourceCategory(Category.health)
					.createNewsApi();

			NewsReponse NR_Perfo = newsapi.getNews();

			if (NR_Perfo != null) {
				List<Article> articles = NR_Perfo.getArticles();
				List<String> art_url = ctrl.getUrls(articles);
				try {
					for(String s : art_url)
						dwn.saveUrl2File(s);
					SequentialDownloader seqDwnld = new SequentialDownloader();
					ParalellDownloader paraDwnld = new ParalellDownloader();
					seqDwnld.process(art_url);
					paraDwnld.process(art_url);

				}

				catch (NullPointerException e)
				{
					System.out.println("Fehler im Download Bereich");
				}

				long TimeBEfore = System.currentTimeMillis();
				dwn.process(art_url);

				if(dwn instanceof ParalellDownloader){
					System.out.println("Parallel: " + (System.currentTimeMillis() - TimeBEfore));
				}
				else if(dwn instanceof SequentialDownloader){
					System.out.println(("Sequential: " + (System.currentTimeMillis() - TimeBEfore)));
				}
			}

		}
		catch (NewsApiException e)
		{
			System.out.println("Hauptklassen Objekt Fehler");

		}

	}
	
	public void getDataForCustomInput() {

		String Query = "";
		System.out.println("****** Bitte um Eingabe des Suchbegriffes in der Entertainmentbranche:");
		Scanner sc = new Scanner(System.in);
		Query = sc.nextLine();

		ctrl.process(Query, Category.entertainment, Endpoint.TOP_HEADLINES);
	}


	public void start() {
		Menu<Runnable> menu = new Menu<>("User Interfacx");
		menu.setTitel("PROG2 NEWS API  - Wählen Sie aus:");
		menu.insert("a", "** Aktuelle Nachrichten für die Gesundheits", this::getDataFromCtrl1);
		menu.insert("b", "** Aktuelle Nachrichten aus der Techwelt", this::getDataFromCtrl2);
		menu.insert("c", "** Aktuelle Nachrichten vom Sport", this::getDataFromCtrl3);
		menu.insert("d", "** Aktuelle Nachrichten aus dem Entertainment",this::getDataForCustomInput);
		menu.insert("o","** Letze Suche als Download starten", this::getDataFromCtrl4);
		menu.insert("q", "** Programm beenden", null);
		Runnable choice;
		while ((choice = menu.exec()) != null) {
			 choice.run();
		}
		System.out.println("Program finished - Thanks for using the Skynet Media Brainwash System");
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
