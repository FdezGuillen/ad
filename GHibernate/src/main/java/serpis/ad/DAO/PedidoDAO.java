package serpis.ad.DAO;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import serpis.ad.modelos.Articulo;
import serpis.ad.modelos.Cliente;
import serpis.ad.modelos.Pedido;
import serpis.ad.modelos.PedidoLinea;

public class PedidoDAO {

	static EntityManager entityManager;


	public static void setEntityManager(EntityManager entityManager) {
		PedidoDAO.entityManager = entityManager;
	}
	
	public static List<Pedido> getAll(){
		List<Pedido> list = entityManager.createQuery("from Pedido order by Id", Pedido.class).getResultList();
		return list;
	}
	
	public static void insert() {
		Cliente cliente = entityManager.find(Cliente.class, 2L);
		Pedido pedido = new Pedido(cliente);
		PedidoLinea pedidoLinea1 = new PedidoLinea(pedido);
		Articulo articulo1 = entityManager.find(Articulo.class, 1L);
		pedidoLinea1.setArticulo(articulo1);
		pedidoLinea1.setUnidades(new BigDecimal(4));
		PedidoLinea pedidoLinea2 = new PedidoLinea(pedido);
		Articulo articulo2 = entityManager.find(Articulo.class, 2L);
		pedidoLinea2.setArticulo(articulo2);
		pedidoLinea2.setUnidades(new BigDecimal(5));
		entityManager.persist(pedido);
}
	
}
