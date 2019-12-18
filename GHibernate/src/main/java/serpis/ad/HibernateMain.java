package serpis.ad;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateMain {

	public static void main(String[] args) {

		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("serpis.ad.ghibernate");

		Categoria categoria = new Categoria();
		categoria.setNombre("cat " + LocalDateTime.now());

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(categoria);

		List<Categoria> categorias = entityManager.createQuery("from Categoria order by Id", Categoria.class)
				.getResultList();
		show(categorias);
		System.out.println("********************************************");
		
		List<Articulo> articulos = entityManager.createQuery("from Articulo order by Id", Articulo.class)
				.getResultList();
		showArticulo(articulos);
		
		System.out.println("********************************************");
		
		List<Cliente> clientes = entityManager.createQuery("from Cliente order by Id", Cliente.class)
				.getResultList();
		showClientes(clientes);
		
		entityManager.getTransaction().commit();
		entityManager.close();

		entityManagerFactory.close();

	}

	private static void show(List<Categoria> categorias) {
		for (Categoria categoria : categorias)
			System.out.printf("%3d %s %n", categoria.getId(), categoria.getNombre());
	}
	
	private static void showArticulo(List<Articulo> articulos) {
		for (Articulo articulo : articulos)
			System.out.printf("%3d %s %n", articulo.getId(), articulo.getNombre());
	}
	
	private static void showClientes(List<Cliente> clientes) {
		for (Cliente cliente: clientes) {
			System.out.printf("%3d %s %n", cliente.getId(), cliente.getNombre());
			Set<Pedido> pedidos = cliente.getPedidos();
			System.out.println(pedidos);
		}
	}
}
