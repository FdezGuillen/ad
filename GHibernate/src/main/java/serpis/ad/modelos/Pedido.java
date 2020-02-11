package serpis.ad.modelos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;


@Entity
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDateTime fecha = LocalDateTime.now();
	private BigDecimal importe = BigDecimal.ZERO;
	@ManyToOne
	@JoinColumn(name = "cliente")
	private Cliente cliente;
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PedidoLinea> pedidoLineas = new ArrayList<PedidoLinea>();
	
	private Pedido() {} //Hibernate necesita un ctor sin parámetros
	
	public Pedido(Cliente cliente) {
		this.cliente = cliente;
	}
	public Long getId() {
		return id;
	}
	public LocalDateTime getFecha() {
		return fecha;
	}
	
	@PrePersist
	@PreUpdate
	private void preGetImporte() {
		importe = BigDecimal.ZERO;
		for (PedidoLinea pedidoLinea : pedidoLineas)
			importe = importe.add(pedidoLinea.getImporte());
	}
	public BigDecimal getImporte() {
		preGetImporte();
		return importe;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public List<PedidoLinea> getPedidoLineas() {
		return pedidoLineas;
}

	@Override
	public String toString() {
		return "Pedido [id=" + id + ", fecha=" + fecha + ", importe=" + importe + ", cliente=" + cliente;
	}
	
}