package serpis.ad.DAO;

import java.util.List;

import javax.persistence.EntityManager;

import serpis.ad.modelos.Articulo;
import serpis.ad.modelos.Categoria;

public class ArticuloDAO {

	private static EntityManager entityManager;

	
	public static EntityManager getEntityManager() {
		return entityManager;
	}

	public static void setEntityManager(EntityManager entityManager) {
		ArticuloDAO.entityManager = entityManager;
	}

	public static List<Articulo> getAll() {
		List<Articulo> list = entityManager.createQuery("from Articulo order by Id", Articulo.class)
				.getResultList();
		return list;
	}
	
	public static void insert(Articulo articulo) {
		entityManager.persist(articulo);
	}
}
