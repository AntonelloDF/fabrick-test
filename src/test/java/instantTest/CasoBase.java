package instantTest;

import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

import common.Constant;
import common.TestActionBase;

@TestComponent
public class CasoBase extends TestActionBase{

	
	//1. richiesta saldo per utente 14537780
	@Test
	public void getSaldo_01()throws Exception {
		getSaldo(Constant.SALDO_IN_01, Constant.SALDO_OUT_01);
	}
	
	//richiesta transazioni idconto=14537780&startDate=2021-12-01&endDate=2021-12-09
	@Test
	public void getTransazioni_01()throws Exception {
		getTransazioni(Constant.SALDO_IN_01, Constant.SALDO_OUT_01);
	}
	
	//richiesta transazioni idconto=14537780&startDate=2021-12-01&endDate=2021-12-09
	@Test
	public void bonifico_01()throws Exception {
		getTransazioni(Constant.SALDO_IN_01, Constant.SALDO_OUT_01);
	}
}
