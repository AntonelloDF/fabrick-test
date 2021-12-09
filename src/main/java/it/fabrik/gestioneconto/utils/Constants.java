package it.fabrik.gestioneconto.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class Constants {
	public final static String ID_KEY = "3202";
	public final static String ACCOUNT_ID = "14537780";
	public final static String TIMEZONE_TRANSFER_KEY = "X-Time-Zone";
	
	

	public final static HttpHeaders getHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set(HeadersParma.AUTH_SCHEMA.getKey(), HeadersParma.AUTH_SCHEMA.getValue());
		headers.set(HeadersParma.API_KEY.getKey(), HeadersParma.API_KEY.getValue());
		return headers;
	}

	public enum HeadersParma {
		API_KEY("Api-Key", "FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP"), 
		AUTH_SCHEMA("Auth-Schema", "S2S");

		private String key;
		private String value;

		HeadersParma(String key, String value) {
			this.key = key;
			this.value = value;
		}

		public String getKey() {
			return key;
		}

		public String getValue() {
			return value;
		}
	}
}
