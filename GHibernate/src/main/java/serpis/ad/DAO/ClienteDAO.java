package serpis.ad.DAO;

import java.util.List;

import javax.persistence.EntityManager;

import serpis.ad.modelos.Articulo;
import serpis.ad.modelos.Cliente;

public class ClienteDAO {
	
	static EntityManager entityManager;


	public static EntityManager getEntityManager() {
		return entityManager;
	}

	public static void setEntityManager(EntityManager entityManager) {
		ClienteDAO.entityManager = entityManager;
	}

	public static List<Cliente> getAll() {
		List<Cliente> list = entityManager.createQuery("from Cliente order by Id", Cliente.class)
				.getResultList();
		return list;
	}
	
	public static Cliente getById(Long clientId) {
		Cliente cliente = entityManager.find(Cliente.class, clientId);
		return cliente;
	}
	
	public static void insert(Cliente articulo) {
		entityManager.persist(articulo);
	}
}
