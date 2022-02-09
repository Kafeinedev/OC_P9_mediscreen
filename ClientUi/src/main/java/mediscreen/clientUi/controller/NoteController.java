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

/*
 * Controller for CRUD operation on Note.
 */
@Slf4j
@Controller
public class NoteController {

	private APIErrorUtil errorUtil = new APIErrorUtil();

	@Autowired
	private NoteProxy noteService;

	@Autowired
	private PatientInfoProxy patientInfoService;

	/**
	 * Gets the patient history.
	 *
	 * @param patId the id of the patient to show history
	 * @param model the model
	 * @return a view of the patient note history
	 */
	@GetMapping("/patHistory/patient")
	public String getPatientHistory(@RequestParam long patId, Model model) {
		log.info("Get @ /patHistory/patient?patId=" + patId);
		model.addAttribute("patient", patientInfoService.getPatientInfoById(patId).getFamily());
		model.addAttribute("patId", patId);
		model.addAttribute("notes", noteService.getPatientHistory(patId));
		return "notes/history";
	}

	/**
	 * Form for adding a note to a patient
	 *
	 * @param patId the pat id to add a note
	 * @param note  the note
	 * @param model the model
	 * @return a view of the form to add a note
	 */
	@GetMapping("/patHistory/add/{patId}")
	public String getNoteAdd(@PathVariable long patId, Note note, Model model) {
		log.info("Get @ /patHistory/add");
		note.setPatId(patId);
		note.setPatient(patientInfoService.getPatientInfoById(patId).getFamily());
		model.addAttribute("note", note);
		log.debug(model.toString());
		return "notes/add";
	}

	/**
	 * Post note add.
	 *
	 * @param note   the note to add
	 * @param model  the model
	 * @param result the result
	 * @return a view of the patient note history //redirection
	 * @throws JsonMappingException    the json mapping exception
	 * @throws JsonProcessingException the json processing exception
	 */
	@PostMapping("/patHistory/add/{patId}")
	public String postNoteAdd(Note note, Model model, BindingResult result)
			throws JsonMappingException, JsonProcessingException {
		log.info("Post @ /patHistory/add note = " + note.toString());

		try {
			noteService.createNote(note);
		} catch (FeignException e) {
			if (e.status() == 400) {// We only resolve bad request
									// Since there is no requirement on note field
									// this should never get called
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

	/**
	 * Form for updating a note update.
	 *
	 * @param id    the id of the note to update
	 * @param model the model
	 * @return a view of a form to update the note
	 */
	@GetMapping("/patHistory/update/{id}")
	public String getNoteUpdate(@PathVariable(name = "id") String id, Model model) {
		log.info("Get @ /patHistory/update/" + id);
		model.addAttribute("note", noteService.getNote(id));

		return "notes/update";
	}

	/**
	 * Post note update.
	 *
	 * @param id     the id of the note to update
	 * @param note   the note to update
	 * @param model  the model
	 * @param result the result
	 * @return a view of the patient note history //redirection
	 * @throws JsonMappingException    the json mapping exception
	 * @throws JsonProcessingException the json processing exception
	 */
	@PostMapping("/patHistory/update/{id}")
	public String postNoteUpdate(@PathVariable(name = "id") String id, Note note, Model model, BindingResult result)
			throws JsonMappingException, JsonProcessingException {
		log.info("Post @ /patHistory/update/" + id + " note = " + note.toString());
		note.setId(id);

		try {
			noteService.updateNote(note);
		} catch (FeignException e) {
			if (e.status() == 400) {// We only resolve bad request
									// Since there is no requirement on note field
									// this should never get called
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
