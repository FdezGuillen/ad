package serpis.ad.modelos;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Entity
public class PedidoLinea implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "pedido")
	private Pedido pedido;
	@ManyToOne
	@JoinColumn(name = "articulo")
	private Articulo articulo;
	private BigDecimal precio = BigDecimal.ZERO;
	private BigDecimal unidades = BigDecimal.ONE;
	private BigDecimal importe = BigDecimal.ZERO;
	
	private PedidoLinea() {} //Hibernate necesita un ctor sin parámetros
	
	public PedidoLinea(Pedido pedido) {
		this.pedido = pedido;
		pedido.getPedidoLineas().add(this);
	}
	
	public Long getId() {
		return id;
	}
	public Pedido getPedido() {
		return pedido;
	}
	public Articulo getArticulo() {
		return articulo;
	}

	public void setArticulo(Articulo articulo) {
		precio = articulo.getPrecio();
		unidades = BigDecimal.ONE;
		this.articulo = articulo;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public BigDecimal getUnidades() {
		return unidades;
	}

	public void setUnidades(BigDecimal unidades) {
		this.unidades = unidades;
	}

	@PrePersist
	@PreUpdate
	private void preGetImporte() {
		importe = precio.multiply(unidades);
	}
	public BigDecimal getImporte() {
		preGetImporte();
		return importe;
}

	@Override
	public String toString() {
		return "PedidoLinea [id=" + id + ", pedido=" + pedido + ", articulo=" + articulo + ", precio=" + precio
				+ ", unidades=" + unidades + ", importe=" + importe + "]";
	}

}
