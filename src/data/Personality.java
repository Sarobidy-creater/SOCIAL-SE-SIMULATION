package data;

import java.util.Random;

public class Personality {
	@Override
	public String toString() {
		return "Personalité [introverted=" + introverted + ", extraverted=" + extraverted + ", sensing=" + sensing
				+ ", intuitive=" + intuitive + ", thinking=" + thinking + ", feeling=" + feeling + ", judging="
				+ judging + ", perceiving=" + perceiving + "]";
	}

	private int introverted;
	private int extraverted;
	private int sensing;
	private int intuitive;
	private int thinking;
	private int feeling;
	private int judging;
	private int perceiving;

	public Personality(int introverted, int extraverted, int sensing, int intuitive, int thinking, int feeling,
			int judging, int perceiving) {
		this.introverted = introverted;
		this.extraverted = extraverted;
		this.sensing = sensing;
		this.intuitive = intuitive;
		this.thinking = thinking;
		this.feeling = feeling;
		this.judging = judging;
		this.perceiving = perceiving;
	}

	// Getters et setters
	public int getIntroverted() {
		return introverted;
	}

	public void setIntroverted(int introverted) {
		this.introverted = introverted;
	}

	public int getExtraverted() {
		return extraverted;
	}

	public void setExtraverted(int extraverted) {
		this.extraverted = extraverted;
	}

	public int getSensing() {
		return sensing;
	}

	public void setSensing(int sensing) {
		this.sensing = sensing;
	}

	public int getIntuitive() {
		return intuitive;
	}

	public void setIntuitive(int intuitive) {
		this.intuitive = intuitive;
	}

	public int getThinking() {
		return thinking;
	}

	public void setThinking(int thinking) {
		this.thinking = thinking;
	}

	public int getFeeling() {
		return feeling;
	}

	public void setFeeling(int feeling) {
		this.feeling = feeling;
	}

	public int getJudging() {
		return judging;
	}

	public void setJudging(int judging) {
		this.judging = judging;
	}

	public int getPerceiving() {
		return perceiving;
	}

	public void setPerceiving(int perceiving) {
		this.perceiving = perceiving;
	}

	public String generatePositiveWorkResponse() {
		String[] responses = { "Mon travail est vraiment enrichissant en ce moment.",
				"Je suis ravi de mes réalisations professionnelles.",
				"Le travail me donne un sentiment d'accomplissement." };
		return responses[getRandomIndex(responses.length)];
	}

	public String generateNegativeWorkResponse() {
		String[] responses = { "Je suis stressé par mes responsabilités au travail.",
				"Je me sens submergé par ma charge de travail.", "Le travail me semble épuisant ces derniers temps." };
		return responses[getRandomIndex(responses.length)];
	}

	public String generateNeutralWorkResponse() {
		String[] responses = { "Mon travail suit son cours habituel.",
				"Je suis concentré sur mes tâches professionnelles.", "Pas grand-chose de nouveau au travail." };
		return responses[getRandomIndex(responses.length)];
	}

	public String generatePositiveHomeResponse() {
		String[] responses = { "Je me sens vraiment bien chez moi.", "La maison est un lieu de détente pour moi.",
				"Je suis content de retrouver mon chez-moi à la fin de la journée." };
		return responses[getRandomIndex(responses.length)];
	}

	public String generateNegativeHomeResponse() {
		String[] responses = { "La maison est en désordre et ça me stresse.",
				"Je suis fatigué des tâches ménagères à faire à la maison.",
				"Je me sens seul chez moi ces derniers temps." };
		return responses[getRandomIndex(responses.length)];
	}

	public String generateNeutralHomeResponse() {
		String[] responses = { "Je passe une soirée tranquille chez moi.",
				"Rien de spécial ne se passe à la maison en ce moment.",
				"Je profite d'un peu de temps libre à la maison." };
		return responses[getRandomIndex(responses.length)];
	}

	public String generatePositiveSchoolResponse() {
		String[] responses = { "Je suis vraiment enthousiaste à propos de mes études.",
				"Je suis motivé pour réussir mes examens.", "J'apprécie mes cours et mes camarades de classe." };
		return responses[getRandomIndex(responses.length)];
	}

	public String generateNegativeSchoolResponse() {
		String[] responses = { "Je suis stressé par mes devoirs et mes examens.",
				"J'ai du mal à suivre le rythme à l'école.", "Je trouve mes cours ennuyeux en ce moment." };
		return responses[getRandomIndex(responses.length)];
	}

	public String generateNeutralSchoolResponse() {
		String[] responses = { "L'école se passe bien pour moi en ce moment.",
				"Rien de particulier à signaler concernant l'école.", "Je me concentre sur mes études sans problème." };
		return responses[getRandomIndex(responses.length)];
	}

	public String generatePositiveNeutralResponse() {
		String[] responses = { "Je me promène et profite de l'air frais.",
				"La rue est animée et ça me met de bonne humeur.",
				"C'est agréable de se détendre et de regarder les gens passer." };
		return responses[getRandomIndex(responses.length)];
	}

	public String generateNegativeNeutralResponse() {
		String[] responses = { "Je me sens un peu perdu dans la foule.", "Je trouve la rue bruyante et agitée.",
				"Je suis pressé de rentrer chez moi." };
		return responses[getRandomIndex(responses.length)];
	}

	public String generateNeutralNeutralResponse() {
		String[] responses = { "Je suis juste en train de me promener sans but précis.",
				"Je suis dans la rue pour faire quelques courses.",
				"Rien de spécial ne se passe pendant ma promenade." };
		return responses[getRandomIndex(responses.length)];
	}

	private int getRandomIndex(int length) {
		return new Random().nextInt(length);
	}
}
