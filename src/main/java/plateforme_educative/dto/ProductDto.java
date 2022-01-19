package plateforme_educative.dto;

import org.springframework.web.multipart.MultipartFile;

public class ProductDto {
	private int productid;
	private String description;
	private String productname;
	private String price;
	private String quantity;
	private MultipartFile productimage;	
	public ProductDto() {}
	public ProductDto(int productid, String description, String productname, String price, String quantity,
			MultipartFile productimage) {
		super();
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
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public MultipartFile getProductimage() {
		return productimage;
	}
	public void setProductimage(MultipartFile productimage) {
		this.productimage = productimage;
	} 
}
