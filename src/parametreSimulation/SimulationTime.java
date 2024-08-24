package parametreSimulation;

public class SimulationTime {
    private int heures;
    private int minutes;
    private int jours;

    public SimulationTime() {
        this.heures = 0;
        this.minutes = 0;
        this.jours = 0;
    }

    public int getHeures() {
        return heures;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getJours() {
        return jours;
    }

    public void incrementer(SimulationTime temps) {
        this.minutes += temps.getMinutes();
        if (this.minutes >= 60) {
            this.heures += this.minutes / 60;
            this.minutes %= 60;
        }
        this.heures += temps.getHeures();
        if (this.heures >= 24) {
            this.jours += this.heures / 24;
            this.heures %= 24;
        }
        this.jours += temps.getJours();
    }

    public void setHeures(int heures) {
		this.heures = heures;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public void setJours(int jours) {
		this.jours = jours;
	}

	public void reset() {
        this.heures = 0;
        this.minutes = 0;
        this.jours = 0;
    }
}
