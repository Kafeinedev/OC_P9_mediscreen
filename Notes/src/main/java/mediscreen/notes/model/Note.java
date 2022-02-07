package mediscreen.notes.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
	private String id;

	@NotNull
	private Long patId;

	@NotBlank
	private String patient;
	private String note;
}
