package it.fabrik.gestioneconto.service;

import java.net.URI;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import it.fabrik.gestioneconto.db.BasicRequestDB;
import it.fabrik.gestioneconto.utils.Constants;
import it.fabrik.gestioneconto.utils.Properties;
import it.fabrik.gestioneconto.utils.RestTemplateToFabrick;

@Service
public class BasicRequestService {
	Logger logger = LogManager.getLogger(BasicRequestService.class);
	@Autowired
	private Properties properties;
	
	@Autowired
	private BasicRequestDB db;
	
	@Autowired
	RestTemplateToFabrick restTemplate;
	
	public ResponseEntity<String> getSaldo(String idconto) {
		try {
			
			ResponseEntity<String> result;
			String url = properties.getProperty("fabrick.base.url")
					+ String.format(properties.getProperty("fabrick.saldo.url"), idconto);

			HttpEntity<String> entityReq = new HttpEntity<String>(Constants.getHeader());

			result = restTemplate.exchange(new URI(url), HttpMethod.GET, entityReq, String.class);
			return result;
		} catch (Exception e) {
			logger.error("Error in getSaldo service", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	public ResponseEntity<String> getTransazioni(String idconto, String startDate, String endDate) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> result;
			String url = properties.getProperty("fabrick.base.url")
					+ String.format(properties.getProperty("fabrick.transazioni.url"), idconto);
			UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(url);
			if (startDate != null) {
				uri.queryParam("fromAccountingDate", startDate);
			}
			if (endDate != null) {
				uri.queryParam("toAccountingDate", endDate);
			}
			HttpEntity<String> entityReq = new HttpEntity<String>(Constants.getHeader());
			result = restTemplate.exchange(uri.toUriString(), HttpMethod.GET, entityReq, String.class);
			return result;
		} catch (Exception e) {
			logger.error("Error in getTransazioni service", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}

	}
	
	public ResponseEntity<String> doBonifico(String idconto, String inputJsonObjt) throws JSONException {
		try {
			RestTemplate restTemplate = new RestTemplate();
			String url = properties.getProperty("fabrick.base.url")
					+ String.format(properties.getProperty("fabrick.bonifico.url"), idconto);
			HttpEntity<String> entityReq = new HttpEntity<String>(inputJsonObjt, Constants.getHeader());
			ResponseEntity<String> result = restTemplate.exchange(new URI(url), HttpMethod.POST, entityReq,
					String.class);
			if(result.getStatusCode().equals(HttpStatus.ACCEPTED)) {
				db.saveBonifico( new JSONObject(result), null);
			}else {
				db.saveBonifico(new JSONObject(), "FAIL API: "+ result.getStatusCode().toString());
			}
			return result;
		} catch (Exception e) {
			logger.error("Error in doBonifico service", e);
			db.saveBonifico(new JSONObject(inputJsonObjt), "FAIL API " + e.getClass().getSimpleName());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	
}
