package crawler;

import java.util.Iterator;
import java.util.List;

import crawler.entity.RedditPost;
import crawler.properties.RedditProperties;
import manager.RedditManager;

public class ThreadCrawler {

	
	public static void main(String[] args) {
		
		try {
			new RedditProperties();
			for (Iterator<String> iterator = RedditProperties.SELECTED_THREADS.iterator(); iterator.hasNext();) {
				List<RedditPost> postList = Requestor.getPosts(RedditProperties.HTTPS_REDDIT + (String) iterator.next());
				System.out.println("Comentarios " + postList.size());
				RedditManager.savePosts(postList);
			}
			RedditManager.shutdown();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
