package mediscreen.notes.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Note {

	@Id
	@Size(max = 24, message = "id cannot exceed 24 characters")
	private String id;

	@NotNull(message = "must add a patient id")
	private Long patId;

	@NotBlank(message = "must add a patient name")
	private String patient;

	private String note;
}
