package mediscreen.assessment;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mediscreen.assessment.beans.Note;
import mediscreen.assessment.beans.PatientInfo;
import mediscreen.assessment.service.impl.DefaultAssessmentService;

class DefaultAssessmentServiceTest {

	private PatientInfo info;

	private List<Note> notes;

	private DefaultAssessmentService service;

	@BeforeEach
	void setUp() {
		service = new DefaultAssessmentService();
	}

	@Test
	void assess_whenpatientishealthy_returnNone() {
		info = new PatientInfo(1L, "family", "given", "2000-01-01", "F", null, null);
		notes = new ArrayList<>();

		assertEquals("None", service.assess(notes, info));
	}

	@Test
	void assess_whenpatientisover30andscoreoftwo_returnBorderline() {
		info = new PatientInfo(1L, "family", "given", "1900-01-01", "F", null, null);
		Note one = new Note();
		Note two = new Note();
		one.setNote("blabcholEStérollba");
		two.setNote("VERTIGEblablba");
		notes = List.of(one, two);

		assertEquals("Borderline", service.assess(notes, info));
	}

	@Test
	void assess_whenpatientisover30andscoreof7_returnInDanger() {
		info = new PatientInfo(1L, "family", "given", "1900-01-01", "F", null, null);
		Note one = new Note();
		Note two = new Note();
		one.setNote("blréactIONabcholEStérollbarechutehémoglobine a1c");
		two.setNote("VERTIGEblablbamicroalbuminetaille");
		notes = List.of(one, two);

		assertEquals("In Danger", service.assess(notes, info));
	}

	@Test
	void assess_whenpatientisover30andscoreof8_returnEarlyonset() {
		info = new PatientInfo(1L, "family", "given", "1900-01-01", "F", null, null);
		Note one = new Note();
		Note two = new Note();
		one.setNote("poidsblréactIONabcholEStérollbarechutehémoglobine a1c");
		two.setNote("VERTIGEblablbamicroalbuminetaille");
		notes = List.of(one, two);

		assertEquals("Early onset", service.assess(notes, info));
	}

	@Test
	void assess_whenpatientismaleunder30andscoreof5_returnEarlyonset() {
		info = new PatientInfo(1L, "family", "given", "2022-01-01", "M", null, null);
		Note one = new Note();
		one.setNote("poidsblréactIONabcholEStérollbarechutehémoglobine a1c");
		notes = List.of(one);

		assertEquals("Early onset", service.assess(notes, info));
	}

	@Test
	void assess_whenpatientismaleunder30andscoreof3_returnInDanger() {
		info = new PatientInfo(1L, "family", "given", "2022-01-01", "M", null, null);
		Note one = new Note();
		one.setNote("Jajoute Absoulument nimpabcholEStérollbarechutehémoglobine a1c");
		notes = List.of(one);

		assertEquals("In Danger", service.assess(notes, info));
	}

	@Test
	void assess_whenpatientisfemaleunder30andscoreof8_returnEarlyonset() {
		info = new PatientInfo(1L, "family", "given", "2022-01-01", "F", null, null);
		Note one = new Note();
		Note two = new Note();
		one.setNote("poidsblréactIONabcholEStérollbarechutehémoglobine a1c");
		two.setNote("VERTIGEblablbamicroalbuminetaille");
		notes = List.of(one, two);

		assertEquals("Early onset", service.assess(notes, info));
	}

	@Test
	void assess_whenpatientisfemaleunder30andscoreof4_returnInDanger() {
		info = new PatientInfo(1L, "family", "given", "2022-01-01", "F", null, null);
		Note one = new Note();
		one.setNote("Jajouteanticorps Absoulument nimpabcholEStérollbarechutehémoglobine a1c");
		notes = List.of(one);

		assertEquals("In Danger", service.assess(notes, info));
	}

}
