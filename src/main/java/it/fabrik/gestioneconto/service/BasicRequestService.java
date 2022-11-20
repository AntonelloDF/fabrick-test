package it.fabrik.gestioneconto.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import it.fabrik.gestioneconto.db.TransactionRepository;
import it.fabrik.gestioneconto.db.dto.TransactionDTO;
import it.fabrik.gestioneconto.utils.Constants;
import it.fabrik.gestioneconto.utils.Properties;
import it.fabrik.gestioneconto.utils.RestTemplateToFabrick;

@Service
public class BasicRequestService {
	Logger logger = LogManager.getLogger(BasicRequestService.class);
	@Autowired
	private Properties properties;
	
	
	@Autowired
	RestTemplateToFabrick restTemplate;
	
	@Autowired
	TransactionRepository transactionRepository;
	
	public ResponseEntity<String> getSaldo(String idconto) {
		try {
			
			ResponseEntity<String> result;
			String url = String.format(properties.getProperty("fabrick.saldo.url"), idconto);
			result = restTemplate.doGet(url);
			return result;
		} catch (Exception e) {
			logger.error("Error in getSaldo service", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	public ResponseEntity<String> getTransazioni(String idconto, String startDate, String endDate) {
		try {
			ResponseEntity<String> result;
			String url = String.format(properties.getProperty("fabrick.transazioni.url"), idconto);
			UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(url);
			if (startDate != null) {
				uri.queryParam("fromAccountingDate", startDate);
			}
			if (endDate != null) {
				uri.queryParam("toAccountingDate", endDate);
			}
			result = restTemplate.doGet(uri.toUriString());
			saveTransaction(idconto, new JSONObject(result.getBody()));
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
			return result;
		} catch (Exception e) {
			logger.error("Error in doBonifico service", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	private void saveTransaction(String idConto, JSONObject json) {
		try {
			JSONArray jsonArray = json.getJSONObject("payload").getJSONArray("list"); 
			List<TransactionDTO> transactions = new ArrayList<>();
			for (int i = 0; i< jsonArray.length(); i++) {
				transactions.add(new TransactionDTO(idConto,jsonArray.getJSONObject(i)));
			}
			transactionRepository.saveAll(transactions);
		} catch (Exception e) {
			logger.error("Error in saveTransaction", e);
		} 
		
	}
}
