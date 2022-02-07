package mediscreen.clientUi.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mediscreen.clientUi.bean.Note;

@FeignClient(name = "note", url = "${url.note}")
public interface NoteProxy {

	@PostMapping("/patHistory/add")
	public Note createNote(Note note);

	@PutMapping("/patHistory/update")
	public Note updateNote(Note note);

	@GetMapping("/patHistory/search/{id}")
	public Note getNote(@PathVariable String id);

	@GetMapping("/patHistory")
	public List<Note> getPatientHistory(@RequestParam Long patId);
}
