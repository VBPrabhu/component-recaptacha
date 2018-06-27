package in.reusable.component.recaptach.controller;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
public class CaptachaController {

	@Autowired 
	RecaptchaService captchaService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET )
	public String hello(Model model){
		model.addAttribute("title", "Reusable Component - Recaptacha Server Validation");
		return "recaptacha";
	}
	
	@RequestMapping(value = "scenario-1", method = RequestMethod.POST)
	public ResponseEntity<?> scenario1(@RequestParam(name = "g-recaptcha-response") String recaptchaResponse, Model model,
			HttpServletRequest request) throws JsonProcessingException, URISyntaxException {
		
		Map<String, Object> response = new HashMap<>();
		System.out.println("Inside Login :\n" + recaptchaResponse);

		String ip = request.getRemoteAddr();
		System.out.println("IP : " + ip);
		String captchaVerifyMessage = 
				captchaService.verifyRecaptcha(ip, recaptchaResponse, "Scenario-1");
		
		System.out.println("Response : " + captchaVerifyMessage);
		if ( StringUtils.isNotEmpty(captchaVerifyMessage)) {
				response.put("message", captchaVerifyMessage);
				return ResponseEntity.badRequest()
						.body(response);
		}
		response.put("message", captchaVerifyMessage);	
		return ResponseEntity.ok().body(response);
	}
	
	@RequestMapping(value = "scenario-2", method = RequestMethod.POST)
	public ResponseEntity<?> scenario2(@RequestParam(name = "g-recaptcha-response") String recaptchaResponse, Model model,
			HttpServletRequest request) throws JsonProcessingException, URISyntaxException {
		
		Map<String, Object> response = new HashMap<>();
		System.out.println("Inside Login :\n" + recaptchaResponse);

		String ip = request.getRemoteAddr();
		System.out.println("IP : " + ip);
		String captchaVerifyMessage = 
				captchaService.verifyRecaptcha(ip, recaptchaResponse, "Scenario-2");
		
		System.out.println("Response : " + captchaVerifyMessage);
		if ( StringUtils.isNotEmpty(captchaVerifyMessage)) {
				response.put("message", captchaVerifyMessage);
				return ResponseEntity.badRequest()
						.body(response);
		}
		response.put("message", captchaVerifyMessage);	
		return ResponseEntity.ok().body(response);
	}
	
}