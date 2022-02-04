package mediscreen.patientInfo.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

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

	@NotBlank
	@Column(length = 64, nullable = false)
	@Size(max = 64)
	private String family;

	@NotBlank
	@Column(length = 64, nullable = false)
	@Size(max = 64)
	private String given;

	@Past(message = "date of birth must be less than today")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(nullable = false)
	private Date dob;

	@NotBlank
	@Size(max = 1)
	@Pattern(regexp = "[FM]")
	@Column(columnDefinition = "CHAR", nullable = false)
	private String sex;

	@Column(length = 255)
	@Size(max = 255)
	private String address;

	@Column(length = 16)
	@Size(max = 16)
	private String phone;
}
