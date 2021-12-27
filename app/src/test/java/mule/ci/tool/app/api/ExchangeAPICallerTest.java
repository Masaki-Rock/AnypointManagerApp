package mule.ci.tool.app.api;

import org.junit.Before;
import org.junit.Test;

import mule.ci.tool.app.util.AppException;
import mule.ci.tool.app.util.Const;

public class ExchangeAPICallerTest {

	// private static final Logger log =
	// LoggerFactory.getLogger(ExchangeAPICallerTest.class);

	@Before
	public void setUp() {
		Const.init("config.yaml");
	}
	
	/**
	 * Exchangeアセット検索機能
	 * 
	 * @throws AppException アプリケーション例外
	 */
	@Test
	public void findAsset() throws AppException {
		ExchangeAPICaller caller = new ExchangeAPICaller();
		caller.findAsset(Const.ASSET_ID);
	}
}
