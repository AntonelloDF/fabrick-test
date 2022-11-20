package common;

import java.net.URI;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class RestTest  extends RestTemplate{

	final static String SERVER = "http://localhost:8080";
	
	public ResponseEntity<String> doGet(String endPoint) throws Exception{
		ResponseEntity<String> result;
		String url = SERVER + endPoint;


		result = this.exchange(new URI(url), HttpMethod.GET, null, String.class);
		return result;
	}
	
}