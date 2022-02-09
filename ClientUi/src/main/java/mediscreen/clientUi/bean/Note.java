package mediscreen.clientUi.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * Data transfer object for note.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Note {

	private String id;
	private Long patId;
	private String patient;
	private String note;
}
