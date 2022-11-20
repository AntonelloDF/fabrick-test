package common;


public class Constant {
	private Constant() {}
	
	public static final String FIELD_API_SALDO = "api.saldo";
	public static final String FIELD_API_TRANSAZIONI = "api.transazioni";
	public static final String FIELD_API_BONIFICO = "api.bonifico";
	
	public static final String FILED_IDCONTO = "idconto";
	public static final String FILED_STARTDATE = "startDate";
	public static final String FILED_ENDDATE = "endDate";
	
	public static final String FOLDER_JSON = "src/test/resources/json/";
	public static final String SALDO_IN_01 = "saldo_in_01.json";
	public static final String SALDO_OUT_01 = "saldo_out_01.json";
	public static final String TRANSAZIONE_IN_01 = "transazione_in_01.json";
	public static final String TRANSAZIONE_OUT_01 = "transazione_out_01.json";
	public static final String BONIFICO_IN_01 = "bonifico_in_01.json";
	public static final String BONIFICO_OUT_01 = "bonifico_out_01.json";
	
	public static final String API_SALDO= "/saldo?idconto=%s";
	public static final String API_TRANSAZIONI="/transazioni?idconto=%s&startDate=%s&endDate=%s";
	public static final String API_BONIFICO="/doBonifico?idconto=%s";
}
