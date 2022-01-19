package plateforme_educative.models;

import javax.persistence.*;

@Entity
@Table(	name = "admin")
public class Admin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String firstNameclt;
	private String lastNameclt;
	private String emailclt;
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getFirstNameclt() {
		return firstNameclt;
	}


	public void setFirstNameclt(String firstNameclt) {
		this.firstNameclt = firstNameclt;
	}


	public String getLastNameclt() {
		return lastNameclt;
	}


	public void setLastNameclt(String lastNameclt) {
		this.lastNameclt = lastNameclt;
	}


	public String getEmailclt() {
		return emailclt;
	}


	public void setEmailclt(String emailclt) {
		this.emailclt = emailclt;
	}

}
