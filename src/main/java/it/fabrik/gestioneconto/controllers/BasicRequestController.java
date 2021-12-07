package it.fabrik.gestioneconto.controllers;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import it.fabrik.gestioneconto.utils.Constants;

@RestController
public class BasicRequestController {

	@RequestMapping(value = "/getSaldo", method = RequestMethod.GET)
	public ResponseEntity<String> getSaldo(HttpServletRequest request) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> result;
			if (request.getParameter("idconto") == null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			String url = Constants.BASE_URL
					+ String.format(Constants.GET_TRANSAZIONI_URL, request.getParameter("idconto"));

			HttpEntity<String> entityReq = new HttpEntity<String>(Constants.getHeader());

			result = restTemplate.exchange(new URI(url), HttpMethod.GET, entityReq, String.class);
			return result;
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@RequestMapping(value = "/getTransazioni", method = RequestMethod.GET)
	public ResponseEntity<String> getTransazioni(HttpServletRequest request) throws URISyntaxException {
		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> result;
			if (request.getParameter("idconto") == null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			String url = Constants.BASE_URL
					+ String.format(Constants.GET_TRANSAZIONI_URL, request.getParameter("idconto"));
			HttpHeaders headers = Constants.getHeader();
			if(request.getParameter("startDate")!=null) {
				headers.set("fromAccountingDate", request.getParameter("startDate"));
			}
			if(request.getParameter("endDate")!=null) {
				headers.set("toAccountingDate", request.getParameter("endDate"));
			}
			HttpEntity<String> entityReq = new HttpEntity<String>(Constants.getHeader());

			result = restTemplate.exchange(new URI(url), HttpMethod.GET, entityReq, String.class);
			return result;
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}

	}

	@RequestMapping(value = "/doBonifico", method = RequestMethod.GET)
	public ResponseEntity<String> doBonifico(HttpServletRequest request, @RequestBody String inputJsonObjt)
			throws RestClientException, URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		if (request.getAttribute("idconto") == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		String url = Constants.BASE_URL + String.format(Constants.DO_BONIFICO_URL, request.getAttribute("idconto"));
		HttpEntity<String> entityReq = new HttpEntity<String>(inputJsonObjt, Constants.getHeader());
		ResponseEntity<String> result = restTemplate.exchange(new URI(url), HttpMethod.GET, entityReq, String.class);
		return result;

	}

}
