package it.fabrik.gestioneconto.controllers;

import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import it.fabrik.gestioneconto.service.BasicRequestService;

@RestController
public class BasicRequestController {
	Logger logger = LogManager.getLogger(BasicRequestController.class);


	@Autowired
	private BasicRequestService service;

	@RequestMapping(value = "/getSaldo", method = RequestMethod.GET)
	public ResponseEntity<String> getSaldo(HttpServletRequest request) {
		logger.info("Start getSadlo method");
		try {
			if (request.getParameter("idconto") == null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			ResponseEntity<String> result = service.getSaldo(request.getParameter("idconto"));
			logger.info("End Success getSaldo");
			return result;
		} catch (Exception e) {
			logger.error("Error in getSaldo", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@RequestMapping(value = "/getTransazioni", method = RequestMethod.GET)
	public ResponseEntity<String> getTransazioni(HttpServletRequest request) {
		logger.info("Start getTransazioni method");
		try {

			if (request.getParameter("idconto") == null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			ResponseEntity<String> result = service.getTransazioni(request.getParameter("idconto"),
					request.getParameter("startDate"), request.getParameter("endDate"));
			logger.info("End Success getTransazioni");
			return result;
		} catch (Exception e) {
			logger.error("Error in getTransazioni", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}

	}

	@RequestMapping(value = "/doBonifico", method = RequestMethod.POST)
	public ResponseEntity<String> doBonifico(HttpServletRequest request, @RequestBody String inputJsonObjt) {
		logger.info("Start doBonifico method");
		try {
			if (request.getParameter("idconto") == null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			ResponseEntity<String> result = service.doBonifico(request.getParameter("idconto"), inputJsonObjt);
			logger.info("End Success doBonifico");
			return result;
		} catch (Exception e) {
			logger.error("Error in doBonifico", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

}
