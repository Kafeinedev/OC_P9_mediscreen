package mediscreen.assessment.constant;

import java.util.List;

/*
 * Contain the term to search in patient note for evaluation
 * 
 */
public class AssessmentTerm {

	private static List<String> terms = List.of("hémoglobine a1c", "microalbumine", "taille", "poids", "fumeur",
			"anormal", "cholestérol", "vertige", "rechute", "réaction", "anticorps");

	/**
	 * Terms for evaluations.
	 *
	 * @return terms to search for evaluation all terms are lowercase only
	 */
	public static List<String> getTerms() {
		return terms;
	}
}
