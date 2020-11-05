package lojaunit.entities;

import java.io.Serializable;

public class IdItemVenda implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer venda;
	private Integer produto;

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
}