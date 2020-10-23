package lojaunit.entities;

import java.io.Serializable;

public class IdItemVenda implements Serializable {
	private Venda venda;
	private Produto produto;

	public IdItemVenda() {
		
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
}
