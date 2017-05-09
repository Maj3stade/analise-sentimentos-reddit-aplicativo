package crawler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;
import javax.persistence.EntityManager;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import crawler.entity.RedditThread;
import manager.RedditManager;

public class Requestor {
	
	public static String getHTML(String urlToRead) throws Exception {

		RedditManager rm = new RedditManager();
		ObjectMapper objectMapper = new ObjectMapper();
		StringBuilder result = new StringBuilder();
		URL url = new URL(urlToRead);
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("User-Agent", "Crawler 0.1");
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		String line;
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		rd.close();
		JsonNode node = objectMapper.readValue(result.toString(), JsonNode.class);
		JsonNode jacksonThreads = node.get("data").get("children");
		Iterator<JsonNode> threadIterator = jacksonThreads.elements();
		EntityManager entityManager = RedditManager.getEntityManagerFactory().createEntityManager();

		entityManager.getTransaction().begin();  
	    while(threadIterator.hasNext()) {
			JsonNode iteratorThread = (JsonNode) threadIterator.next();
			RedditThread thread = objectMapper.readValue(iteratorThread.get("data").toString(), RedditThread.class);
			
			entityManager.persist(thread);
			System.out.println(thread.getPermalink());

	    }

		entityManager.getTransaction().commit();  
		
		entityManager.close();
	    /*System.out.println(node.toString());
		RedditThread thread = objectMapper.readValue(node.toString(), RedditThread.class);
		System.out.println(thread.getSubreddit());*/
		/*RedditThread thread = objectMapper.readValue(result.toString(), RedditThread.class);*/
		
		
		return result.toString();
	}

}
