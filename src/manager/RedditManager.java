package manager;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import crawler.Requestor;
import crawler.entity.RedditPost;
import crawler.entity.RedditThread;

public class RedditManager {
	private static final String PERSISTENCE_UNIT_NAME = "PERSISTENCE";
	private static EntityManagerFactory factory;
	

	public static EntityManagerFactory getEntityManagerFactory() {
	    if (factory == null) {
	      factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	    }
	    return factory;
	}


	public static void shutdown() {
		if (factory != null) {
			factory.close();
		}
	}

	public static void saveThreads(List<RedditThread> threadList) {
		EntityManager entityManager = getEntityManagerFactory().createEntityManager();
		entityManager.getTransaction().begin();  
		
		
		for (Iterator<RedditThread> iterator = threadList.iterator(); iterator.hasNext();) {
			RedditThread redditThread = (RedditThread) iterator.next();

			entityManager.persist(redditThread);


		}

		entityManager.getTransaction().commit();
		entityManager.close();
	}


	public static void savePosts(List<RedditPost> postList) {
		EntityManager entityManager = getEntityManagerFactory().createEntityManager();
		entityManager.getTransaction().begin();  
		
		
		for (Iterator<RedditPost> iterator = postList.iterator(); iterator.hasNext();) {
			RedditPost redditThread = (RedditPost) iterator.next();

			entityManager.persist(redditThread);


		}

		entityManager.getTransaction().commit();
		entityManager.close();
		
	}

}