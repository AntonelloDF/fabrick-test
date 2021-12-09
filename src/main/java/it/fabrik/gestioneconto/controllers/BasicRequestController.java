package it.fabrik.gestioneconto.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.print.attribute.HashAttributeSet;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import it.fabrik.gestioneconto.utils.Constants;

@RestController
public class BasicRequestController {
	Logger logger = LogManager.getLogger(BasicRequestController.class);

	@RequestMapping(value = "/getSaldo", method = RequestMethod.GET)
	public ResponseEntity<String> getSaldo(HttpServletRequest request) {
		logger.info("Start getSadlo method");
		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> result;
			if (request.getParameter("idconto") == null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			String url = Constants.BASE_URL + String.format(Constants.SALDO_URL, request.getParameter("idconto"));

			HttpEntity<String> entityReq = new HttpEntity<String>(Constants.getHeader());

			result = restTemplate.exchange(new URI(url), HttpMethod.GET, entityReq, String.class);
			logger.info("End Success getSaldo");
			return result;
		} catch (Exception e) {
			logger.error("Error in getSaldo", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@RequestMapping(value = "/getTransazioni", method = RequestMethod.GET)
	public ResponseEntity<String> getTransazioni(HttpServletRequest request)  {
		logger.info("Start getTransazioni method");
		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> result;
			if (request.getParameter("idconto") == null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			String url = Constants.BASE_URL
					+ String.format(Constants.GET_TRANSAZIONI_URL, request.getParameter("idconto"));
			HttpHeaders headers = Constants.getHeader();
			UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(url);
			if (request.getParameter("startDate") != null) {
				uri.queryParam("fromAccountingDate", request.getParameter("startDate"));
			}
			if (request.getParameter("endDate") != null) {
				uri.queryParam("toAccountingDate", request.getParameter("endDate"));
			}
			HttpEntity<String> entityReq = new HttpEntity<String>( Constants.getHeader());
			URI u = new URI(url);
			
			result = restTemplate.exchange(uri.toUriString(), HttpMethod.GET, entityReq, String.class);
			logger.info("End Success getTransazioni");
			return result;
		} catch (Exception e) {
			logger.error("Error in getTransazioni", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}

	}

	@RequestMapping(value = "/doBonifico", method = RequestMethod.POST)
	public ResponseEntity<String> doBonifico(HttpServletRequest request, @RequestBody String inputJsonObjt) {
		logger.info("Start doBonifico method");
		try {
			RestTemplate restTemplate = new RestTemplate();
			if (request.getParameter("idconto") == null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			String url = Constants.BASE_URL + String.format(Constants.DO_BONIFICO_URL, request.getParameter("idconto"));
			HttpEntity<String> entityReq = new HttpEntity<String>(inputJsonObjt, Constants.getHeader());
			ResponseEntity<String> result = restTemplate.exchange(new URI(url), HttpMethod.POST, entityReq,
					String.class);
			logger.info("End Success getTransazioni");
			return result;
		} catch (Exception e) {
			logger.error("Error in doBonifico", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

}
