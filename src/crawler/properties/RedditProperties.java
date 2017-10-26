package crawler.properties;

import java.util.ArrayList;
import java.util.List;

public class RedditProperties {

	public static final String HTTPS_REDDIT = "https://www.reddit.com/";
	
	public static final List<String> SELECTED_THREADS = new ArrayList<>();

	public static final List<String> SELECTED_TARGETS = new ArrayList<>();
	
	//Tópicos Selecionados Através do TCC
	public RedditProperties(){
		/*SELECTED_THREADS.add("r/worldnews/67ivae");
		SELECTED_THREADS.add("r/worldnews/5uzetf");
		SELECTED_THREADS.add("r/worldnews/5qqa51");
		SELECTED_TARGETS.add("Trump");*/
		
		SELECTED_THREADS.add("r/movies/706y1p");
		SELECTED_THREADS.add("r/movies/73g2fx");
		SELECTED_THREADS.add("r/movies/6g5lmo");
		SELECTED_TARGETS.add("Movie");
		
		
	}
	

}
