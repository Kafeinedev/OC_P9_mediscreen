package mediscreen.clientUi.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * Data transfer object to allow the use of feign for transfer of id only
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StringId {
	private String id;
}
