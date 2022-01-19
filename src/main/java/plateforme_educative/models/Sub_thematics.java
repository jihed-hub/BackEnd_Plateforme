package plateforme_educative.models;

import javax.persistence.*;


@Entity
public class Sub_thematics {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "name")
	private String name;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "thematic_id")
    private Thematics thematic;
	public Sub_thematics() {}
	public Sub_thematics(String name) {
		this.name = name;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Thematics getThematic() {
		return thematic;
	}
	public void setThematic(Thematics thematic) {
		this.thematic = thematic;
	}
	@Override
	public String toString() {
		return "Sub_thematics [id=" + id + ", name=" + name + "]";
	}
    
}
