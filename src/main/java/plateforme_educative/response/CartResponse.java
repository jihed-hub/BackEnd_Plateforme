package plateforme_educative.response;

import java.util.List;

import plateforme_educative.models.Cart;

public class CartResponse {
	private String message;
	private List<Cart> carts;
	public CartResponse(String message, List<Cart> carts) {
		this.message = message;
		this.carts = carts;
	}
	public CartResponse(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<Cart> getCarts() {
		return carts;
	}
	public void setCarts(List<Cart> carts) {
		this.carts = carts;
	}
}
