package serpis.ad.DAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import serpis.ad.modelos.Categoria;

public class CategoriaDAO {
	
	private static EntityManager entityManager;
	
	
	public static EntityManager getEntityManager() {
		return entityManager;
	}

	public static void setEntityManager(EntityManager entityManager) {
		CategoriaDAO.entityManager = entityManager;
	}

	public static List<Categoria> getAll() {
		List<Categoria> list = entityManager.createQuery("from Categoria order by Id", Categoria.class)
				.getResultList();
		return list;
	}
	
	public static void insert(Categoria categoria) {
		entityManager.persist(categoria);
	}

}
