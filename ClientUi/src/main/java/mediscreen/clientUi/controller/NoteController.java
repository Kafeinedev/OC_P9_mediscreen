package mediscreen.clientUi.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import mediscreen.clientUi.bean.Note;
import mediscreen.clientUi.proxy.NoteProxy;
import mediscreen.clientUi.proxy.PatientInfoProxy;
import mediscreen.clientUi.util.APIErrorUtil;

@Slf4j
@Controller
public class NoteController {

	private APIErrorUtil errorUtil = new APIErrorUtil();

	@Autowired
	private NoteProxy noteService;

	@Autowired
	private PatientInfoProxy patientInfoService;

	@GetMapping("/patHistory/patient")
	public String getPatientHistory(@RequestParam long patId, Model model) {
		log.info("Get @ /patHistory/patient?patId=" + patId);
		model.addAttribute("patient", patientInfoService.getPatientInfoById(patId).getFamily());
		model.addAttribute("patId", patId);
		model.addAttribute("notes", noteService.getPatientHistory(patId));
		return "/notes/history";
	}

	@GetMapping("/patHistory/add/{patId}")
	public String getAddPatient(@PathVariable long patId, Note note, Model model) {
		log.info("Get @ /patHistory/add");
		model.addAttribute("patId", patId);
		model.addAttribute("note", note);
		return "notes/add";
	}

	@PostMapping("/patHistory/add")
	public String postAddPatient(Note note, Model model, BindingResult result)
			throws JsonMappingException, JsonProcessingException {
		log.info("Post @ /patHistory/add note = " + note.toString());

		try {
			noteService.createNote(note);
		} catch (FeignException e) {
			if (e.status() == 400) {// We only resolve bad request
				Map<String, String> errors = errorUtil.extractError(e.contentUTF8());
				errors.forEach((k, v) -> {
					result.addError(new FieldError("note", k, v));
				});
				return "notes/add";
			} else {
				throw e;
			}
		}

		return "redirect:/patHistory/patient?patId=" + note.getPatId();
	}

	@GetMapping("/patHistory/update/{id}")
	public String getUpdatePatient(@PathVariable(name = "id") String id, Model model) {
		log.info("Get @ /patHistory/update/" + id);
		model.addAttribute("note", noteService.getNote(id));

		return "notes/update";
	}

	@PostMapping("/patHistory/update/{id}")
	public String putUpdatePatient(@PathVariable(name = "id") String id, Note note, Model model, BindingResult result)
			throws JsonMappingException, JsonProcessingException {
		log.info("Post @ /patHistory/update/" + id + " note = " + note.toString());
		note.setId(id);

		try {
			noteService.updateNote(note);
		} catch (FeignException e) {
			if (e.status() == 400) {// We only resolve bad request
				Map<String, String> errors = errorUtil.extractError(e.contentUTF8());
				errors.forEach((k, v) -> {
					result.addError(new FieldError("note", k, v));
				});

				return "patient/update";
			} else {// Another method handle the rest.
				throw e;
			}
		}

		return "redirect:/patHistory/patient?patId=" + note.getPatId();
	}
}
