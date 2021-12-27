package mule.ci.tool.app.api;

import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mule.ci.tool.app.api.model.APIAssetsResponse;
import mule.ci.tool.app.api.model.ExchangeAssetResponse;
import mule.ci.tool.app.api.model.PoliciesResponse;
import mule.ci.tool.app.api.model.TierResponse;
import mule.ci.tool.app.api.model.TiersResponse;
import mule.ci.tool.app.util.AppException;
import mule.ci.tool.app.util.Const;

public class APIManagerAPICallerTest {

	private static final Logger log = LoggerFactory.getLogger(APIManagerAPICallerTest.class);

	@Before
	public void setUp() {
		Const.init("config.yaml");
	}

	@Test
	public void findAPIInstance() throws AppException {	
		APIManagerAPICaller caller = new APIManagerAPICaller();
		caller.findAPIInstance();
	}

	@Ignore @Test
	public void saveAPIInstance() throws AppException {
		APIManagerAPICaller caller = new APIManagerAPICaller();
		caller.saveAPIInstance(Const.ASSET_ID, Const.API_INSTANCE_LABEL);
	}

	@Ignore @Test
	public void updateAPIInstance() throws AppException {
		APIManagerAPICaller caller = new APIManagerAPICaller();
		ExchangeAPICaller excaller = new ExchangeAPICaller();
		APIAssetsResponse param = caller.findAPIInstance();
		ExchangeAssetResponse exres = excaller.findAsset(Const.ASSET_ID);
		caller.updateAPIInstance(param.getId(), exres.getVersion());
	}

	@Ignore @Test
	public void deleteAPIInstance() throws AppException {
		APIManagerAPICaller caller = new APIManagerAPICaller();
		APIAssetsResponse param = caller.findAPIInstance();
		caller.deleteAPIInstance(param.getId());
	}

	// @Test
	public void findTier() throws AppException {
		APIManagerAPICaller caller = new APIManagerAPICaller();
		APIAssetsResponse param = caller.findAPIInstance();
		caller.findTier(param.getId());
	}

	@Ignore @Test
	public void saveAllTier() throws AppException {
		APIManagerAPICaller caller = new APIManagerAPICaller();
		APIAssetsResponse param = caller.findAPIInstance();
		caller.saveSLATiers(param.getId());
	}

	@Ignore @Test
	public void deleteAllTier() throws AppException {
		APIManagerAPICaller caller = new APIManagerAPICaller();
		APIAssetsResponse param = caller.findAPIInstance();
		TiersResponse response = caller.findTier(param.getId());
		for (Map<String, Object> tier : response.getTiers()) {
			caller.deleteSLATier(param.getId(), (Integer) tier.get("id"));
		}
	}

	// @Test
	public void findPolicy() throws AppException {
		APIManagerAPICaller caller = new APIManagerAPICaller();
		APIAssetsResponse param = caller.findAPIInstance();
		caller.findPolicy(param.getId());
	}

	@Ignore @Test
	public void saveAllPolicy() throws AppException {
		APIManagerAPICaller caller = new APIManagerAPICaller();
		APIAssetsResponse param = caller.findAPIInstance();
		caller.savePolicies(param.getId());
	}

	@Ignore @Test
	public void deleteAllPolicy() throws AppException {
		APIManagerAPICaller caller = new APIManagerAPICaller();
		APIAssetsResponse param = caller.findAPIInstance();
		caller.deletePolicies(param.getId());
	}

	// @Test
	public void findAPIAlert() throws AppException {
		APIManagerAPICaller caller = new APIManagerAPICaller();
		APIAssetsResponse param = caller.findAPIInstance();
		caller.findAPIAlert(param.getId());
	}

	@Ignore @Test
	public void saveAPIAlert() throws AppException {
		APIManagerAPICaller caller = new APIManagerAPICaller();
		APIAssetsResponse param = caller.findAPIInstance();
		PoliciesResponse policy = caller.findPolicy(param.getId());
		TierResponse response = caller.saveAPIAlert(param.getId(), Const.API_NAME, "rate-limiting-sla-based",
				policy.get("rate-limiting-sla-based"));
		log.info("saveAPIAlert. {}", response.getId());
	}

	@Ignore @Test
	public void saveAllAPIAlert() throws AppException {
		APIManagerAPICaller caller = new APIManagerAPICaller();
		APIAssetsResponse param = caller.findAPIInstance();
		caller.saveAlerts(param.getId(), Const.API_NAME);
	}

	@Ignore @Test
	public void deleteAllAPIAlert() throws AppException {

		APIManagerAPICaller caller = new APIManagerAPICaller();
		APIAssetsResponse param = caller.findAPIInstance();
		caller.deleteAlerts(param.getId());
	}
}
