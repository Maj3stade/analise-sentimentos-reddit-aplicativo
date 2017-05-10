package crawler;

import java.util.Iterator;
import java.util.List;

import crawler.entity.RedditPost;
import crawler.entity.RedditThread;
import crawler.properties.RedditProperties;
import manager.RedditManager;

public class Crawler {

	
	public static void main(String[] args) {
		
		try {
			List<RedditThread> threadList = Requestor.getThreads(RedditProperties.HTTPS_REDDIT + "r/games/top");
			RedditManager.saveThreads(threadList);
			for (Iterator<RedditThread> iterator = threadList.iterator(); iterator.hasNext();) {
				RedditThread redditThread = (RedditThread) iterator.next();
				List<RedditPost> postList = Requestor.getPosts(RedditProperties.HTTPS_REDDIT + redditThread.getId());
				System.out.println("Comentários " + postList.size());
				RedditManager.savePosts(postList);
			}
			
			RedditManager.shutdown();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub

	}

}
