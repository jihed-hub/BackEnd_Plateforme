package plateforme_educative.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@Entity
@Table(name = "Products")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int productid;
	@NotBlank
	private String description;
	@NotBlank
	private String productname;
	private double price;
	private int quantity;
	@Lob
	private byte[] productimage;
	public Product(){}
	public Product(int productid, String description, String productname, double price, int quantity,
			byte[] productimage) {
		this.productid = productid;
		this.description = description;
		this.productname = productname;
		this.price = price;
		this.quantity = quantity;
		this.productimage = productimage;
	}
	public int getProductid() {
		return productid;
	}
	public void setProductid(int productid) {
		this.productid = productid;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public byte[] getProductimage() {
		return productimage;
	}
	public void setProductimage(byte[] productimage) {
		this.productimage = productimage;
	}
}
