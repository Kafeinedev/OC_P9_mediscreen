package mediscreen.assessment.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * Data transfer object for patient info
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PatientInfo {

	private Long id;
	private String family;
	private String given;
	private String dob;
	private String sex;
	private String address;
	private String phone;
}
