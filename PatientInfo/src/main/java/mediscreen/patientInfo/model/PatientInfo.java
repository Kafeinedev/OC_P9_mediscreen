package mediscreen.patientInfo.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String family;
	private String given;
	private Date dob;
	private String sex;
	private String address;
	private String phone;
}
