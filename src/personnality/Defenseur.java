package personnality;

import data.Personality;

public class Defenseur extends Personality{
	@Override
	public String toString() {
		return "Defenseur";
	}

	public Defenseur(int introverted, int extraverted, int sensing, int intuitive, int thinking, int feeling,
			int judging, int perceiving) {
		super(introverted, extraverted, sensing, intuitive, thinking, feeling, judging, perceiving);
		// TODO Auto-generated constructor stub
	}

}