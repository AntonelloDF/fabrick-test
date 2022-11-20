package it.fabrik.gestioneconto.db.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.json.JSONObject;

import it.fabrik.gestioneconto.utils.JSONUtility;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;



@Entity(name = "USER_TRANSACTION")
@Getter
@Setter
@AllArgsConstructor
public class TransactionDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	
	
	private long accountId;
	
	private String transactionId;
	private String operationId;
	
	private LocalDate accountingDate;
	private LocalDate valueDate;
	
	private long amount;
	private String currency;
	private String description;
	
	private String typeValue;
	private String typeEnumeration;
	
	public TransactionDTO(String accountId, JSONObject json) throws Exception{
		 	DateTimeFormatter transaction_formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			
		 	this.accountId = Long.parseLong(accountId);
			this.transactionId = json.getString(JSONUtility.TRANSACTION_TRANSACTIONID);
			this.operationId = json.getString(JSONUtility.TRANSACTION_OPERATIONID);
			this.accountingDate = LocalDate.parse(json.getString(JSONUtility.TRANSACTION_ACCOUNTINGDATE), transaction_formatter);
			this.valueDate = LocalDate.parse(json.getString(JSONUtility.TRANSACTION_VALUEDATE), transaction_formatter);
			this.typeEnumeration = json.getJSONObject(JSONUtility.TRANSACTION_TYPE).getString(JSONUtility.TRANSACTION_ENUMERATION);
			this.typeValue = json.getJSONObject(JSONUtility.TRANSACTION_TYPE).getString(JSONUtility.TRANSACTION_VALUE);
			this.amount = json.getLong(JSONUtility.TRANSACTION_AMOUNT);
			this.currency = json.getString(JSONUtility.TRANSACTION_CURRENCY);
			this.description = json.getString(JSONUtility.TRANSACTION_DESCRIPTION);
	
		
	}


}
