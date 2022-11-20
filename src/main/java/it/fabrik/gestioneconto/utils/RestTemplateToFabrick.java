package it.fabrik.gestioneconto.utils;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateToFabrick  extends RestTemplate{

	@Autowired
	private Properties properties;
	
	public ResponseEntity<String> doGet(String endPoint) throws Exception{
		ResponseEntity<String> result;
		String url = properties.getProperty("fabrick.base.url") + endPoint;

		HttpEntity<String> entityReq = new HttpEntity<String>(Constants.getHeader());

		result = this.exchange(new URI(url), HttpMethod.GET, entityReq, String.class);
		return result;
	}
	
}
