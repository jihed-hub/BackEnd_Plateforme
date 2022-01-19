package plateforme_educative.response;

import java.util.HashMap;
import java.util.List;

import plateforme_educative.models.Address;

public class UserResponse {
	private String message;
	private HashMap<String, String> map;
	private List<Address>adrlist;
	private Address add;
	public UserResponse(String message, HashMap<String, String> map) {
		this.message = message;
		this.map = map;
	}
	
	public UserResponse(HashMap<String, String> map) {
		this.map = map;
	}
	

	public UserResponse(List<Address> adrlist) {
		this.adrlist = adrlist;
	}

	public UserResponse(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public HashMap<String, String> getMap() {
		return map;
	}
	public void setMap(HashMap<String, String> map) {
		this.map = map;
	}

	public List<Address> getAdrlist() {
		return adrlist;
	}

	public void setAdrlist(List<Address> adrlist) {
		this.adrlist = adrlist;
	}

	public UserResponse(Address add) {
		this.add = add;
	}

	public Address getAdd() {
		return add;
	}

	public void setAdd(Address add) {
		this.add = add;
	}
	
}
