package mediscreen.assessment.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import mediscreen.assessment.beans.Note;
import mediscreen.assessment.beans.PatientInfo;
import mediscreen.assessment.constant.AssessmentTerm;
import mediscreen.assessment.service.AssessmentService;
import mediscreen.assessment.util.AgeCalculator;

@Service
public class DefaultAssessmentService implements AssessmentService {

	@Override
	public String assess(List<Note> notes, PatientInfo info) {
		String combinedNotes = "";
		notes.forEach(n -> {
			combinedNotes.concat(n.getNote());
		});
		int score = scoreEvaluation(combinedNotes);
		long age = AgeCalculator.calculateAge(info);

		if (age <= 30) {
			if (info.getSex().equals("F")) {// female patient
				if (score >= 7) {
					return "Early onset";
				}
				if (score >= 4) {// Since early onset is already taken into account we do not need to check for
									// upper bound score < at early onset trigger value
					return "In Danger";
				}
			} else {// male patient
				if (score >= 5) {
					return "Early onset";
				}
				if (score >= 3) {
					return "In Danger";
				}
			}
		} else { // over 30
			if (score == 2) {
				return "Borderline";
			}
			if (score >= 8) {
				return "Early onset";
			}
			if (score >= 6) {
				return "In Danger";
			}
		}

		return "None"; // No match found everything is good.
	}

	private int scoreEvaluation(String combinedNotes) {
		int score = 0;
		combinedNotes.toLowerCase();
		for (String term : AssessmentTerm.getTerms()) {
			if (combinedNotes.contains(term)) {
				++score;
			}
		}
		return score;
	}

}
