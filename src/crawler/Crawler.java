package crawler;

import javax.persistence.EntityManager;

import manager.RedditManager;

public class Crawler {

	public static void main(String[] args) {

		try {
			System.out.println(Requestor.getHTML("https://www.reddit.com/r/games/top.json?limit=10"));
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub

	}

}
