package serpis.ad;

import java.time.LocalDateTime;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateMain {

	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("serpis.ad.ghibernate");
		
		Categoria categoria = new Categoria();
		categoria.setNombre("cat " + LocalDateTime.now());
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.persist(categoria);
		entityManagerFactory.close();
	}
}
