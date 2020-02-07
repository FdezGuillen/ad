package serpis.ad;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Conexion {

	EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("serpis.ad.ghibernate");
	EntityManager entityManager = entityManagerFactory.createEntityManager();
	
	private static Conexion instance = new Conexion();
	
	private Conexion() {
		
	}
	
	public static Conexion getInstance() {
		return instance;
	}

	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public void closeConnection() {
		System.out.println("Desconectando...");
		entityManager.close();
		entityManagerFactory.close();

	}
	
}
