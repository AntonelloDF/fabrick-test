package common;


import java.net.URI;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import it.fabrik.gestioneconto.utils.Constants;


@Component
public class RestTest  extends RestTemplate{

	final static String SERVER = "http://localhost:8080";
	
	public ResponseEntity<String> doGet(String endPoint) throws Exception{
		ResponseEntity<String> result;
		String url = SERVER + endPoint;


		result = this.exchange(new URI(url), HttpMethod.GET, null, String.class);
		return result;
	}
	
	public ResponseEntity<String> doPost(String endPoint, String jsonBody) throws Exception {
		String url = SERVER + endPoint;
		HttpEntity<String> entityReq = new HttpEntity<String>(jsonBody, Constants.getHeader());
		ResponseEntity<String> result =  this.exchange(url, HttpMethod.POST, entityReq,
				String.class);
		
		return result;
	}
}