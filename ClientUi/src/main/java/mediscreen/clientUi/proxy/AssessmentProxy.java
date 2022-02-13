package mediscreen.clientUi.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import mediscreen.clientUi.bean.StringFamilyName;
import mediscreen.clientUi.bean.StringId;

/*
 * Proxy for the Note microservice
 */
@FeignClient(name = "assessment", url = "${url.assessment}")
public interface AssessmentProxy {
	/**
	 * Assess by id.
	 *
	 * @param id the id DTO of the patient to assess
	 * @return the result in a form of a string
	 */
	@PostMapping(value = "/assess/id", consumes = "application/x-www-form-urlencoded")
	public String assessById(StringId id);

	/**
	 * Assess by name.
	 *
	 * @param familyName the family name DTO of the patient to assess
	 * @return the result in a form of a string
	 */
	@PostMapping(value = "/assess/familyName", consumes = "application/x-www-form-urlencoded")
	public String assessByName(StringFamilyName familyName);
}
