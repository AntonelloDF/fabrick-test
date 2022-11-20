package common;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URI;

import org.springframework.boot.test.context.TestComponent;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.fabrik.gestioneconto.utils.Constants;


@TestComponent
public class TestActionBase {
	ObjectMapper objectMapper = new ObjectMapper();
	JSONParser parser = new JSONParser();
	
	
	RestTest restTest = new RestTest();
	
	protected void getSaldo(String inDate, String outDate) throws Exception, Exception, Exception {
		String idconto = (objectMapper.readValue(new File(Constant.FOLDER_JSON+inDate), JSONObject.class)).get(Constant.FILED_IDCONTO).toString();
		ResponseEntity<String> response =  restTest.doGet(String.format(Constant.API_SALDO, idconto));
		
		assertEquals(response.getStatusCodeValue(), HttpStatus.OK);
		JSONObject jsonData = objectMapper.readValue(new File(Constant.FOLDER_JSON + outDate), JSONObject.class);
		assertEquals(response.getBody(), jsonData);
	}
	
	protected void getTransazioni(String inDate, String outDate) throws Exception {
		JSONObject json = (objectMapper.readValue(new File(Constant.FOLDER_JSON+inDate), JSONObject.class));
		ResponseEntity<String> response =  restTest.doGet(
					String.format(Constant.API_TRANSAZIONI, 
							json.get(Constant.FILED_IDCONTO),
							json.get(Constant.FILED_STARTDATE),
							json.get(Constant.FILED_ENDDATE)));
		
		assertEquals(response.getStatusCodeValue(), HttpStatus.OK);
		JSONObject jsonData = objectMapper.readValue(new File(Constant.FOLDER_JSON + outDate), JSONObject.class);
		assertEquals(response.getBody(), jsonData);
	}

	protected void doBonifico(String inDate, String outDate) throws Exception{
		JSONObject json = (objectMapper.readValue(new File(Constant.FOLDER_JSON+inDate), JSONObject.class));
		URI uri = new URI(String.format(Constant.API_BONIFICO, json.get(Constant.FILED_IDCONTO)));
		HttpEntity<String> entityReq = new HttpEntity<String>(inDate, Constants.getHeader());
		ResponseEntity<String> response =  restTest.exchange(uri, HttpMethod.POST, entityReq,
				String.class);
		
		assertEquals(response.getStatusCodeValue(), HttpStatus.OK);
		JSONObject jsonData = objectMapper.readValue(new File(Constant.FOLDER_JSON + outDate), JSONObject.class);
		assertEquals(response.getBody(), jsonData);
	}
}
