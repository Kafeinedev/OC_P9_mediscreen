package mediscreen.patientInfo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "must provide a family name")
	@Column(length = 64, nullable = false)
	@Size(max = 64)
	private String family;

	@NotBlank(message = "must provide a given name")
	@Column(length = 64, nullable = false)
	@Size(max = 64)
	private String given;

	@NotNull(message = "must provide a date of birth")
	@Pattern(regexp = "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))", message = "Date must follow yyyy-mm-dd format")
	@Column(columnDefinition = "DATE", nullable = false)
	private String dob; // String to provide easy validation with regex

	@NotBlank(message = "must provide a sex")
	@Size(max = 1)
	@Pattern(regexp = "[FM]", message = "Must be either 'F' or 'M'")
	@Column(columnDefinition = "CHAR", nullable = false)
	private String sex;

	@Column(length = 255)
	@Size(max = 255)
	private String address;

	@Column(length = 16)
	@Size(max = 16)
	private String phone;
}
