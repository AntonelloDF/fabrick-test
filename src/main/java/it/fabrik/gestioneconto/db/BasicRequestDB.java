package it.fabrik.gestioneconto.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.fabrik.gestioneconto.utils.JSONUtility;
import it.fabrik.gestioneconto.utils.Properties;

@Repository
public class BasicRequestDB {
	final Logger logger = LogManager.getLogger(BasicRequestDB.class);
	final static String SAVE_BONIFICO = "INSERT INTO money_transfer_history (creditor_name, creditor_iban, amount, currency, debtor_name, debtor_iban, status, bonifico) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?);";
	final static String BAD_REQUEST_JSON = "INSERT INTO money_transfer_history (status, bonifico) VALUES (?,?)";
	@Autowired
	private Properties properties;
	
	public String saveBonifico(JSONObject bonifico, String errorMessage) {
		logger.info("Start save Bonifico");
		try (Connection conn = DriverManager.getConnection(properties.getProperty("db.url"),
				properties.getProperty("db.user"), properties.getProperty("db.pw"));
				PreparedStatement pstmt = conn.prepareStatement(SAVE_BONIFICO);) {
			pstmt.setString(1, bonifico.getJSONObject("creditor").getString("name"));
			pstmt.setString(2, bonifico.getJSONObject("creditor").getJSONObject("account").getString("accountCode"));
			if(errorMessage == null) {
				pstmt.setString(3, bonifico.getJSONObject("amount").getString("creditorAmount"));
				pstmt.setString(4, bonifico.getJSONObject("amount").getString("creditorCurrency"));
				
			}else {
				pstmt.setString(3, bonifico.getString("amount"));
				pstmt.setString(4, bonifico.getString("currency"));
			}
			pstmt.setString(5, errorMessage == null? bonifico.getJSONObject("debtor").getString("name"): "");
			pstmt.setString(6, errorMessage == null? bonifico.getJSONObject("debtor").getJSONObject("account").getString("accountCode"):"");
			pstmt.setString(7, errorMessage == null? bonifico.getString(JSONUtility.JSON_STATUS): errorMessage);
			pstmt.setString(8, bonifico.toString());

			int affectedRows = pstmt.executeUpdate();
			if(affectedRows>0) {
				logger.info("Save Bonifico finished");
				return "Success";
			}
			logger.warn("saveBonifico 0 Row inserted");
			return "Fail";
		}catch (JSONException je) {
			logger.error("JSONException in saveBonifico", je);
			saveBadRequestJSON(bonifico, errorMessage+ " JSONException");
			return "Fail";
		} 
		catch (Exception e) {
			logger.error("Error in save Bonifico", e);
			return e.getMessage();
		}
		
	}
	
	private void saveBadRequestJSON(JSONObject bonifico, String errorMessage) {
		try (Connection conn = DriverManager.getConnection(properties.getProperty("db.url"),
				properties.getProperty("db.user"), properties.getProperty("db.pw"));
				PreparedStatement pstmt = conn.prepareStatement(BAD_REQUEST_JSON);) {
			pstmt.setString(1, errorMessage);
			pstmt.setString(2, bonifico.toString());
			pstmt.executeUpdate();
		}catch (Exception e) {
			logger.error("Error in saveBAdRequestJSON", e);
		}
	}
	
}
