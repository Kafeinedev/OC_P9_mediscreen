package mediscreen.assessment.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import mediscreen.assessment.beans.PatientInfo;

/*
 * Util to calculate the age of a patient
 */
public class AgeCalculator {

	public static long calculateAge(PatientInfo info) {
		List<String> dobData = List.of(info.getDob().split("-"));// dob is stored in a YYYY-MM-DD string
		LocalDate current = LocalDate.now();
		LocalDate dob = LocalDate.of(Integer.parseInt(dobData.get(0)), Integer.parseInt(dobData.get(1)),
				Integer.parseInt(dobData.get(2)));
		return ChronoUnit.YEARS.between(dob, current);
	}
}
