package plateforme_educative.response;

import java.util.List;

import plateforme_educative.models.Product;

public class ProductResponse {
	private String message;
	private List<Product> productList;
	private Product product;
	
	public ProductResponse(){}

	public ProductResponse(String message, List<Product> productList) {
		this.message = message;
		this.productList = productList;
	}

	public ProductResponse(String message, Product product) {
		this.message = message;
		this.product = product;
	}

	public ProductResponse(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
