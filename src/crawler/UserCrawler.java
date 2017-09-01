package crawler;

import java.util.Iterator;
import java.util.List;

import crawler.entity.RedditPost;
import crawler.properties.RedditProperties;
import manager.RedditManager;

public class UserCrawler {

	
	public static void main(String[] args) {
		
		try {
			new RedditProperties();
			List<RedditPost> postList = Requestor.getUserPosts(RedditProperties.HTTPS_REDDIT + (String) "u/Maj3stade", "");
			System.out.println("Comentarios " + postList.size());
			//RedditManager.savePosts(postList);
			
			RedditManager.shutdown();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
