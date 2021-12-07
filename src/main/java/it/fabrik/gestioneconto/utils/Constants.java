package it.fabrik.gestioneconto.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class Constants {
	public final static String BASE_URL = "https://sandbox.platfr.io";
	public final static String AUTH_SCHEMA_VALUE = "S2S";
	public final static String AUTH_SCHEMA_KEY = "Auth-Schema";
	public final static String API_KEY_KEY = "Api-Key";
	public final static String API_KEY_VALUE = "FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP";
	public final static String ID_KEY = "3202"; // (per uso interno, non utile al fine del test)
	public final static String ACCOUNT_ID = "14537780";
	public final static String GET_TRANSAZIONI_URL = "/api/gbs/banking/v4.0/accounts/%s/transactions";
	public final static String DO_BONIFICO_URL = "/api/gbs/banking/v4.0/accounts/%s/payments/money-transfers";
	public final static String SALDO_URL = "/api/gbs/banking/v4.0/accounts/%s/balance";
	public final static String TIMEZONE_TRANSFER_KEY = "X-Time-Zone";
	
	public final static HttpHeaders getHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set(Constants.AUTH_SCHEMA_KEY, Constants.AUTH_SCHEMA_VALUE);
		headers.set(Constants.API_KEY_KEY, Constants.API_KEY_VALUE);
		return headers;
	}
}
