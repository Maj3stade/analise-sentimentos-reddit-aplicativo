package manager;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author imssbora
 */
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
}