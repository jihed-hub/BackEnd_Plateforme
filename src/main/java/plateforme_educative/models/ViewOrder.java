package plateforme_educative.models;

import java.util.ArrayList;
import java.util.List;

public class ViewOrder {
	private int orderId;
	private String orderBy;
	private String orderStatus;
	private List<Cart> products = new ArrayList<>();
	public ViewOrder() {}
	public ViewOrder(int orderId, String orderBy, String orderStatus, List<Cart> products) {
		this.orderId = orderId;
		this.orderBy = orderBy;
		this.orderStatus = orderStatus;
		this.products = products;}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public List<Cart> getProducts() {
		return products;
	}
	public void setProducts(List<Cart> products) {
		this.products = products;
	}
}
