package serpis.ad;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import serpis.ad.modelos.Articulo;
import serpis.ad.modelos.Categoria;
import serpis.ad.modelos.Cliente;
import serpis.ad.modelos.Pedido;
import serpis.ad.modelos.PedidoLinea;

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

		List<Cliente> clientes = entityManager.createQuery("from Cliente order by Id", Cliente.class).getResultList();
		showClientes(clientes);

		List<Pedido> pedidos = entityManager.createQuery("from Pedido order by Id", Pedido.class).getResultList();

		List<PedidoLinea> lineaspedido = entityManager.createQuery("from PedidoLinea order by Id", PedidoLinea.class).getResultList();
		
		showClientesPedidos(clientes,pedidos,lineaspedido,articulos);
		
		
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
		for (Cliente cliente : clientes) {
			System.out.printf("%3d %s %n", cliente.getId(), cliente.getNombre());
		}
	}

	private static void showClientesPedidos(List<Cliente> clientes, List<Pedido> pedido, List<PedidoLinea> lineaspedido,
			List<Articulo> articulos) {
		Pedido pedidoActual = new Pedido();
		long idPedidoActual = -1;
		double total = 0;
		String texto = "";
		for (int i = 0; i < lineaspedido.size(); i++) {
			System.out.println(lineaspedido.get(i).getId());
			if (lineaspedido.get(i).getIdPedido() == idPedidoActual) {

				for (int j = 0; j < articulos.size(); j++) {
					if (articulos.get(j).getId() == lineaspedido.get(i).getIdArticulo()) {
						texto += "\n\t - " +lineaspedido.get(i).getUnidades() 
								+ articulos.get(j).getNombre() 
								+ " | Precio: " + lineaspedido.get(i).getPrecio() 
								+ " | Importe: " + lineaspedido.get(i).getImporte();
						total += lineaspedido.get(i).getImporte();
					}

				}
				
			} else {
				
				if (i != 0) {
					texto += "\n TOTAL: " + total + "\n*********************\n";
					System.out.println(texto);
				}
				
				total=0;
				
				for (int j=0; j < pedido.size(); j++) {
					if (lineaspedido.get(i).getIdPedido() == pedido.get(j).getId()) {
						pedidoActual = pedido.get(j);
						idPedidoActual = pedidoActual.getId();
					}
				}
				
				texto = "PEDIDO #" + pedidoActual.getId() + "\n Fecha: " + pedidoActual.getFecha() + "\n Cliente: "
						+ pedidoActual.getIdCliente();
				for (int j = 0; j < clientes.size(); j++) {
					if (clientes.get(j).getId() == pedidoActual.getIdCliente()) {
						texto += clientes.get(j).getNombre() + " ";
					}
				}
				
				texto += "\n Artículos del pedido:";
				
				for (int j = 0; j < articulos.size(); j++) {
					if (articulos.get(j).getId() == lineaspedido.get(i).getIdArticulo()) {
						texto += "\n\t - " +lineaspedido.get(i).getUnidades() 
								+ articulos.get(j).getNombre() 
								+ " | Precio: " + lineaspedido.get(i).getPrecio() 
								+ " | Importe: " + lineaspedido.get(i).getImporte();
						total += lineaspedido.get(i).getImporte();
					}

				}
			}

		}

	}
}
