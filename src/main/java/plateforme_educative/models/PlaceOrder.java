package plateforme_educative.models;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "PlaceOrder")
public class PlaceOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int orderId;
	@NotBlank
	private String email;
	@NotBlank
	private String orderStatus;
	private Date orderDate;
	private double totalCost;
	private String successUrl;
	private String cancelUrl;
	private String currency;
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getSuccessUrl() {
		return successUrl;
	}
	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}
	public String getCancelUrl() {
		return cancelUrl;
	}
	public void setCancelUrl(String cancelUrl) {
		this.cancelUrl = cancelUrl;
	}
	public PlaceOrder() {}
	public PlaceOrder(int orderId, @NotBlank String email, @NotBlank String orderStatus, Date orderDate,
			double totalCost) {
		this.orderId = orderId;
		this.email = email;
		this.orderStatus = orderStatus;
		this.orderDate = orderDate;
		this.totalCost = totalCost;}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
}
