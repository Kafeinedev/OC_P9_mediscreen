package mediscreen.clientUi.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PatientInfo {

	private Long id;
	private String family;
	private String given;
	private String dob;
	private String sex;
	private String address;
	private String phone;
}
