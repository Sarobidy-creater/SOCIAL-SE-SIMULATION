package data;

public class Profession {
	private String name;
	private Routine travail;

	public Profession(String name) {
		this.name = name;

	}

	@Override
	public String toString() {
		return "Profession [name=" + name + ", travail=" + travail + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Routine getTravail() {
		return travail;
	}

	public void setTravail(Routine travail) {
		this.travail = travail;
	}
}
