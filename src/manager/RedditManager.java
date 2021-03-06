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

import crawler.entity.AuthorOpinion;
import crawler.entity.Dictionary;
import crawler.entity.RedditPost;
import crawler.entity.RedditThread;
import crawler.entity.Sentence;
import crawler.entity.WordScore;

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

		List<RedditPost> postList = getEntityManagerFactory().createEntityManager()
				.createQuery("SELECT rp FROM RedditPost rp where author = :author").setParameter("author", author)
				.getResultList();

		/*
		 * EntityManager em = getEntityManagerFactory().createEntityManager();
		 * CriteriaBuilder cb = em.getCriteriaBuilder();
		 * CriteriaQuery<RedditPost> criteria =
		 * cb.createQuery(RedditPost.class); Root<RedditPost> personRoot =
		 * criteria.from(RedditPost.class);
		 * criteria.where(cb.equal(personRoot.get("author"), author)); criteria.
		 * List<RedditPost> postList = em.createQuery(criteria).getResultList();
		 */

		return postList;
	}

	public static List<RedditPost> getAllNotOkPostsByAuthor(String author) {

		List<RedditPost> postList = getEntityManagerFactory().createEntityManager()
				.createQuery(
						"SELECT rp FROM RedditPost rp where author = :author and NOT EXISTS (SELECT ws FROM WordScore ws WHERE  ws.author = rp.author)")
				.setParameter("author", author).getResultList();

		/*
		 * EntityManager em = getEntityManagerFactory().createEntityManager();
		 * CriteriaBuilder cb = em.getCriteriaBuilder();
		 * CriteriaQuery<RedditPost> criteria =
		 * cb.createQuery(RedditPost.class); Root<RedditPost> personRoot =
		 * criteria.from(RedditPost.class);
		 * criteria.where(cb.equal(personRoot.get("author"), author)); criteria.
		 * List<RedditPost> postList = em.createQuery(criteria).getResultList();
		 */

		return postList;
	}
	
	public static List<RedditPost> getAllNotOkPosts() {

		List<RedditPost> postList = getEntityManagerFactory().createEntityManager()
				.createQuery(
						"SELECT rp FROM RedditPost rp where NOT EXISTS (SELECT ws FROM Sentence ws WHERE ws.postId = rp.id)").getResultList();

		/*
		 * EntityManager em = getEntityManagerFactory().createEntityManager();
		 * CriteriaBuilder cb = em.getCriteriaBuilder();
		 * CriteriaQuery<RedditPost> criteria =
		 * cb.createQuery(RedditPost.class); Root<RedditPost> personRoot =
		 * criteria.from(RedditPost.class);
		 * criteria.where(cb.equal(personRoot.get("author"), author)); criteria.
		 * List<RedditPost> postList = em.createQuery(criteria).getResultList();
		 */

		return postList;
	}

	public static List<RedditPost> getNotOkPostsByAuthor(String author) {

		List<RedditPost> postList = getEntityManagerFactory().createEntityManager()
				.createQuery(
						"SELECT rp FROM RedditPost rp where author = :author and NOT EXISTS (SELECT sc FROM Sentence sc WHERE  sc.postId = rp.id)")
				.setParameter("author", author).getResultList();

		/*
		 * EntityManager em = getEntityManagerFactory().createEntityManager();
		 * CriteriaBuilder cb = em.getCriteriaBuilder();
		 * CriteriaQuery<RedditPost> criteria =
		 * cb.createQuery(RedditPost.class); Root<RedditPost> personRoot =
		 * criteria.from(RedditPost.class);
		 * criteria.where(cb.equal(personRoot.get("author"), author)); criteria.
		 * List<RedditPost> postList = em.createQuery(criteria).getResultList();
		 */

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
		em.close();
		return authorList;
	}

	public static void updateRedditPost(List<RedditPost> postList) {
		EntityManager entityManager = getEntityManagerFactory().createEntityManager();
		entityManager.getTransaction().begin();

		for (Iterator<RedditPost> iterator = postList.iterator(); iterator.hasNext();) {
			RedditPost redditPost = iterator.next();

			entityManager.merge(redditPost);

		}

		entityManager.getTransaction().commit();
		entityManager.close();
	}

	public static List<Sentence> getAllSentencesByPost(String postId) {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Sentence> criteria = cb.createQuery(Sentence.class);
		Root<Sentence> personRoot = criteria.from(Sentence.class);
		criteria.where(cb.equal(personRoot.get("postId"), postId));
		List<Sentence> sentenceList = em.createQuery(criteria).getResultList();
		em.close();

		return sentenceList;
	}

	public static List<Sentence> getAllSentences() {
		EntityManager em = getEntityManagerFactory().createEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Sentence> criteria = cb.createQuery(Sentence.class);
		Root<Sentence> personRoot = criteria.from(Sentence.class);
		List<Sentence> sentenceList = em.createQuery(criteria).getResultList();
		em.close();

		return sentenceList;
	}

	public static List<Sentence> getAllNotOkSentencesByAuthor(String author) {

		List<Sentence> postList = getEntityManagerFactory().createEntityManager()
				.createQuery(
						"SELECT st FROM Sentence st where EXISTS (SELECT rp FROM RedditPost rp WHERE  rp.id = st.postId and rp.author = :author and NOT EXISTS (SELECT ws FROM WordScore ws WHERE  ws.author = rp.author))")
				.setParameter("author", author).getResultList();

		/*
		 * EntityManager em = getEntityManagerFactory().createEntityManager();
		 * CriteriaBuilder cb = em.getCriteriaBuilder();
		 * CriteriaQuery<RedditPost> criteria =
		 * cb.createQuery(RedditPost.class); Root<RedditPost> personRoot =
		 * criteria.from(RedditPost.class);
		 * criteria.where(cb.equal(personRoot.get("author"), author)); criteria.
		 * List<RedditPost> postList = em.createQuery(criteria).getResultList();
		 */

		return postList;
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

	public static void saveOpinions(List<AuthorOpinion> opinionList) {
		EntityManager entityManager = getEntityManagerFactory().createEntityManager();
		entityManager.getTransaction().begin();

		for (Iterator<AuthorOpinion> iterator = opinionList.iterator(); iterator.hasNext();) {
			AuthorOpinion opinion = iterator.next();

			entityManager.persist(opinion);

		}

		entityManager.getTransaction().commit();
		entityManager.close();
	}

	public static void saveSentenceDependency(List<Sentence> sentenceList) {
		EntityManager entityManager = getEntityManagerFactory().createEntityManager();
		entityManager.getTransaction().begin();
		try {

			for (Iterator<Sentence> iterator = sentenceList.iterator(); iterator.hasNext();) {
				Sentence sentence = iterator.next();
				try {
					entityManager.persist(sentence);
				} catch (javax.persistence.PersistenceException e) {
					System.out.println(sentence.getSentence());
				}

			}
			entityManager.getTransaction().commit();
			entityManager.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

	public static void savePosts(List<RedditPost> postList, String threadId) {
		EntityManager entityManager = getEntityManagerFactory().createEntityManager();
		
		int i = 0;

		for (Iterator<RedditPost> iterator = postList.iterator(); iterator.hasNext();) {
			i++;
			RedditPost redditThread = iterator.next();
			redditThread.setThreadId(threadId);
			entityManager.getTransaction().begin();
			try {
				entityManager.persist(redditThread);
				entityManager.getTransaction().commit();
			} catch (javax.persistence.EntityExistsException e) {
				System.out.println(redditThread.getId() + "-Coment�rio " + Integer.toString(i) + " de "
						+ Integer.toString((postList.size())));
				System.out.println("Coment�rio j� existente.");
			}
			catch (RollbackException e) {
				System.out.println("N�o foi poss�vel salvar o coment�rio");
				e.printStackTrace();
				
			}
		}
		
		entityManager.close();

	}
	
	public static void savePosts(List<RedditPost> postList) {
		savePosts(postList, "");
	}
	

	public static void saveDictionary(Dictionary dictionary) {
		EntityManager entityManager = getEntityManagerFactory().createEntityManager();
		int i = 0;

		entityManager.getTransaction().begin();
		for (Iterator<WordScore> iterator = dictionary.getDictionary().iterator(); iterator.hasNext();) {
			i++;

			WordScore ws = iterator.next();
			entityManager.persist(ws);

		}
		try {
			entityManager.getTransaction().commit();
		} catch (RollbackException e) {
			System.out.println("N�o foi poss�vel salvar o dicion�rio.");
		}
		entityManager.close();
	}

	public static void saveDictionary(Dictionary dictionary, String author) {
		EntityManager entityManager = getEntityManagerFactory().createEntityManager();
		int i = 0;

		entityManager.getTransaction().begin();
		for (Iterator<WordScore> iterator = dictionary.getDictionary().iterator(); iterator.hasNext();) {
			i++;

			WordScore ws = iterator.next();
			ws.setAuthor(author);
			try {
				entityManager.persist(ws);
			} catch (javax.persistence.PersistenceException e) {
				System.out.println("N�o foi poss�vel salvar a palavra: " + ws.getWord());
			}

		}
		try {
			entityManager.getTransaction().commit();
		} catch (RollbackException e) {
			System.out.println("N�o foi poss�vel salvar o dicion�rio.");
		}
		entityManager.close();
	}

	public static List<RedditPost> getAllPostsByParentId(String parentId) {
		List<RedditPost> postList = getEntityManagerFactory().createEntityManager()
				.createQuery("SELECT rp FROM RedditPost rp where parentId = :parentId")
				.setParameter("parentId", parentId).getResultList();

		return postList;
	}
	
	public static List<RedditPost> getAllPostsByThreadId(String threadId) {
		List<RedditPost> postList = getEntityManagerFactory().createEntityManager()
				.createQuery("SELECT rp FROM RedditPost rp where threadId = :threadId")
				.setParameter("threadId", threadId).getResultList();

		return postList;
	}

	public static List<WordScore> getAllWordScoreByAuthor(String author) {
		List<WordScore> wordScoreList = getEntityManagerFactory().createEntityManager()
				.createQuery("SELECT ws FROM WordScore ws where author = :author").setParameter("author", author)
				.getResultList();

		return wordScoreList;
	}

	public static List<WordScore> getAllDomainWordScore() {
		List<WordScore> wordScoreList = getEntityManagerFactory().createEntityManager()
				.createQuery("SELECT ws FROM WordScore ws where author is null").getResultList();

		return wordScoreList;
	}

}