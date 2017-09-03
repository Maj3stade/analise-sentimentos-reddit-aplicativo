package manager;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.criterion.Projections;

import crawler.entity.RedditPost;
import crawler.entity.RedditThread;
import crawler.entity.Sentence;

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

	public static List<RedditPost> getAllPosts() {
		List<RedditPost> postList = getEntityManagerFactory().createEntityManager()
				.createQuery("SELECT rp FROM RedditPost rp").getResultList();
		return postList;
	}

	public static List<RedditPost> getAllPostsByAuthor(String author) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<RedditPost> criteria = cb.createQuery(RedditPost.class);
		Root<RedditPost> personRoot = criteria.from(RedditPost.class);
		criteria.where(cb.equal(personRoot.get("author"), author));
		List<RedditPost> postList = em.createQuery(criteria).getResultList();

		return postList;
	}

	public static List<String> getAllAuthorsByParentId(String parentId) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<String> criteria = cb.createQuery(String.class);
		Root<RedditPost> personRoot = criteria.from(RedditPost.class);
		criteria.select(personRoot.get("author")).distinct(true);
		criteria.where(cb.equal(personRoot.get("parentId"), parentId));
		List<String> authorList = em.createQuery(criteria).getResultList();

		return authorList;
	}

	public static void saveThreads(List<RedditThread> threadList) {
		EntityManager entityManager = getEntityManagerFactory().createEntityManager();
		entityManager.getTransaction().begin();

		for (Iterator<RedditThread> iterator = threadList.iterator(); iterator.hasNext();) {
			RedditThread redditThread = iterator.next();

			entityManager.persist(redditThread);

		}

		entityManager.getTransaction().commit();
		entityManager.close();
	}
	
	
	public static void saveSentenceDependency(List<Sentence> sentenceList) {
		EntityManager entityManager = getEntityManagerFactory().createEntityManager();
		entityManager.getTransaction().begin();
		
		for (Iterator<Sentence> iterator = sentenceList.iterator(); iterator.hasNext();) {
			Sentence sentence = iterator.next();
			try{
				entityManager.persist(sentence);
			}catch (javax.persistence.PersistenceException e) {
				System.out.println(sentence.getSentence());
				throw  e;
			}

		}
		entityManager.getTransaction().commit();
		entityManager.close();
	}
	
	

	public static void savePosts(List<RedditPost> postList) {
		EntityManager entityManager = getEntityManagerFactory().createEntityManager();
		int i = 0;

		for (Iterator<RedditPost> iterator = postList.iterator(); iterator.hasNext();) {
			i++;
			System.out.println("Comentário " + Integer.toString(i) + " de " + Integer.toString((postList.size())));
			entityManager.getTransaction().begin();

			RedditPost redditThread = iterator.next();

			entityManager.persist(redditThread);
			try {
				entityManager.getTransaction().commit();
			} catch (RollbackException e) {
				System.out.println("Não foi possível salvar o comentário.");
			}

		}
		entityManager.close();

	}

}