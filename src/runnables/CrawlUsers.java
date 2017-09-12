package runnables;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.List;

import crawler.Requestor;
import crawler.entity.RedditPost;
import crawler.properties.RedditProperties;
import manager.RedditManager;

public class CrawlUsers {

	
	public static void main(String[] args) {
		
		try {
			new RedditProperties();

			List<String> authorList = RedditManager.getAllAuthorsByParentId("t3_5qqa51");
			System.out.println(authorList.size());
			
			
			for (String author:authorList) {
				try{
				List<RedditPost> postList = Requestor.getUserPosts(RedditProperties.HTTPS_REDDIT + "u/" + author, "");
				System.out.println("Comentarios " + postList.size());
				RedditManager.savePosts(postList);
				} catch (IOException e) {
					System.out.println("Usuário Inválido: " + author);
				}
			}
			
			RedditManager.shutdown();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
