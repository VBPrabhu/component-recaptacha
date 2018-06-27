package in.reusable.component.recaptach.controller;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RecaptchaService {

	@Value("${google.recaptcha.secret}")
	String recaptchaSecret;

	@Value("${proxy.host}")
	String host;

	@Value("${proxy.port}")
	String port;

	@Value("${google.recaptcha.url}")
	String googleUrl;

	@Value("${remote.ip}")
	String remoteIp;

	@Autowired
	RestTemplateBuilder restTemplateBuilder;

	RestTemplate restTemplatewithProxy;

	RestTemplate restTemplate;

	@PostConstruct
	public void init() {

		restTemplate = new RestTemplate();
		int portNr = -1;
		try {
			portNr = Integer.parseInt(port);
		} catch (NumberFormatException e) {
			System.out.println("Unable to parse the proxy port number");
		}
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		InetSocketAddress address = new InetSocketAddress(host, portNr);
		Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
		factory.setProxy(proxy);
		restTemplatewithProxy = new RestTemplate();
		restTemplatewithProxy.setRequestFactory(factory);
	}
	
	public RestTemplate getRestTemplate() {
		return restTemplatewithProxy;
	}

	public String verifyRecaptcha(String ip, String recaptchaResponse, String scenario)
			throws JsonProcessingException, URISyntaxException {
		if(scenario.equalsIgnoreCase("Scenario-1")) return successCall(recaptchaResponse);
		else return errorCall(recaptchaResponse);
	}

	private String successCall(String recaptchaResponse) {
		
		System.out.println("Validating captcha response for remoteIp={}, response={}" + remoteIp + recaptchaResponse);
		RecaptchaResponse recaptchaResponseObj;
		try {
			recaptchaResponseObj = restTemplate.postForEntity(googleUrl,
					createBody(recaptchaSecret, remoteIp, recaptchaResponse), RecaptchaResponse.class).getBody();
		} catch (RestClientException e) {
			throw new RecaptchaServiceException("Recaptcha API not available exception", e);
		}
		
		if ( !recaptchaResponseObj.success) {
			System.out.println("Unsuccessful recaptchaResponse={}" + recaptchaResponseObj);
			String errorMessage = recaptchaResponseObj.getErrorCodes().stream()
					.map(s -> RecaptchaUtil.RECAPTCHA_ERROR_CODE.get(s))
					.collect(Collectors.joining(", "));
			return errorMessage;
		}else {
			return recaptchaResponseObj.toString();
		}
	}
	
	private String errorCall(String recaptchaResponse) {
		
		System.out.println("Validating captcha response for remoteIp={}, response={}" + remoteIp + recaptchaResponse);
		RecaptchaResponse recaptchaResponseObj;
		try {
			recaptchaResponseObj = restTemplate.postForEntity(googleUrl,
					createBodyWithError(recaptchaSecret, remoteIp, recaptchaResponse), RecaptchaResponse.class).getBody();
		} catch (RestClientException e) {
			throw new RecaptchaServiceException("Recaptcha API not available exception", e);
		}
		
		if ( !recaptchaResponseObj.success) {
			System.out.println("Unsuccessful recaptchaResponse={}" + recaptchaResponseObj);
			String errorMessage = recaptchaResponseObj.getErrorCodes().stream()
					.map(s -> RecaptchaUtil.RECAPTCHA_ERROR_CODE.get(s))
					.collect(Collectors.joining(", "));
			return errorMessage;
		}else {
			return recaptchaResponseObj.toString();
		}
	}

	private MultiValueMap<String, String> createBody(String secret, String remoteIp, String response) {
		MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
		form.add("secret", secret);
		form.add("remoteip", remoteIp);
		form.add("response", response);
		return form;
	}
	
	private MultiValueMap<String, String> createBodyWithError(String secret, String remoteIp, String response) {
		MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
		//form.add("secret", secret);
		form.add("remoteip", remoteIp);
		//form.add("response", response);
		return form;
	}

	private static class RecaptchaResponse {
		
		@JsonProperty("success")
		private boolean success;
		@JsonProperty("error-codes")
		private Collection<String> errorCodes;
		
		public boolean isSuccess() {
			return success;
		}
		public void setSuccess(boolean success) {
			this.success = success;
		}

		public Collection<String> getErrorCodes() {
			return errorCodes;
		}
		
		public void setErrorCodes(Collection<String> errorCodes) {
			this.errorCodes = errorCodes;
		}

		@Override
		public String toString() {
			return "RecaptchaResponse{" + "success=" + success + ", errorCodes=" + errorCodes + '}';
		}
	}
}
