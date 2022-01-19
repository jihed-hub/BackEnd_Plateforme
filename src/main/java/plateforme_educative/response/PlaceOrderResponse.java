package plateforme_educative.response;

import java.util.List;

import plateforme_educative.models.PlaceOrder;

public class PlaceOrderResponse {
	private String message;
	private List<PlaceOrder> placeOrderist;
	private PlaceOrder placeOrder;
	public PlaceOrderResponse() {}
	public PlaceOrderResponse(String message, List<PlaceOrder> placeOrderist, PlaceOrder placeOrder) {
		this.message = message;
		this.placeOrderist = placeOrderist;
		this.placeOrder = placeOrder;}
	public PlaceOrderResponse(String message, PlaceOrder placeOrder) {
		this.message = message;
		this.placeOrder = placeOrder;
	}
	public PlaceOrderResponse(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<PlaceOrder> getPlaceOrderist() {
		return placeOrderist;
	}
	public void setPlaceOrderist(List<PlaceOrder> placeOrderist) {
		this.placeOrderist = placeOrderist;
	}
	public PlaceOrder getPlaceOrder() {
		return placeOrder;
	}
	public void setPlaceOrder(PlaceOrder placeOrder) {
		this.placeOrder = placeOrder;
	}
}
