package plateforme_educative.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(	name = "client")
@JsonIgnoreProperties({ "user" })
public class Client {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String firstNameclt;
	private String lastNameclt;
	private String emailclt;
	private int etat;
	private boolean notif_etat ;
	private int telclt;
	private String adresseclt;
	private String photoclt ;

	public Client() {
	}
	
	public Client(Long id, String firstNameclt, String lastNameclt, String emailclt, int etat, boolean notif_etat,
			int telclt, String adresseclt, String photoclt) {
		super();
		this.id = id;
		this.firstNameclt = firstNameclt;
		this.lastNameclt = lastNameclt;
		this.emailclt = emailclt;
		this.etat = etat;
		this.notif_etat = notif_etat;
		this.telclt = telclt;
		this.adresseclt = adresseclt;
		this.photoclt = photoclt;
	}


	public Client(String firstNameclt, String lastNameclt, String emailclt, int etat, boolean notif_etat, int telclt,
			String adresseclt) {
		this.firstNameclt = firstNameclt;
		this.lastNameclt = lastNameclt;
		this.emailclt = emailclt;
		this.etat = etat;
		this.notif_etat = notif_etat;
		this.telclt = telclt;
		this.adresseclt = adresseclt;
	}

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
	public int getEtat() {
		return etat;
	}
	public void setEtat(int etat) {
		this.etat = etat;
	}
	public boolean isNotif_etat() {
		return notif_etat;
	}
	public void setNotif_etat(boolean notif_etat) {
		this.notif_etat = notif_etat;
	}
	public int getTelclt() {
		return telclt;
	}
	public void setTelclt(int telclt) {
		this.telclt = telclt;
	}
	public String getAdresseclt() {
		return adresseclt;
	}
	public void setAdresseclt(String adresseclt) {
		this.adresseclt = adresseclt;
	}

	public String getPhotoclt() {
		return photoclt;
	}

	public void setPhotoclt(String photoclt) {
		this.photoclt = photoclt;
	}

	

	
	
}
