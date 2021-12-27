package mule.ci.tool.app.api;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import mule.ci.tool.app.api.model.APIAlertRequest;
import mule.ci.tool.app.api.model.APIAlertResponse;
import mule.ci.tool.app.api.model.APIAssetsResponse;
import mule.ci.tool.app.api.model.APIInstanceRequest;
import mule.ci.tool.app.api.model.APIInstanceRequestForAssetVersion;
import mule.ci.tool.app.api.model.APIInstanceResponse;
import mule.ci.tool.app.api.model.ExchangeAssetResponse;
import mule.ci.tool.app.api.model.PoliciesResponse;
import mule.ci.tool.app.api.model.PolicyRequest;
import mule.ci.tool.app.api.model.PolicyResponse;
import mule.ci.tool.app.api.model.TierRequest;
import mule.ci.tool.app.api.model.TierResponse;
import mule.ci.tool.app.api.model.TiersResponse;
import mule.ci.tool.app.util.AppException;
import mule.ci.tool.app.util.Const;
import mule.ci.tool.app.util.HttpClientUtil;

public class APIManagerAPICaller {

	private static final Logger log = LoggerFactory.getLogger(APIManagerAPICaller.class);

	/**
	 * API instance search function.
	 * 
	 * @return search results.
	 * @throws AppException
	 */
	public APIAssetsResponse findAPIInstance() throws AppException {

		String path = String.format(Const.API_INSTANCE_END_POINT, Const.ANYPOINT_ORG, Const.ANYPOINT_ENV,
				StringUtils.EMPTY, StringUtils.EMPTY);
		String resbody = HttpClientUtil.sendRequest(path, Const.GET, null);

		APIAssetsResponse res = HttpClientUtil.makeResponse(resbody, APIAssetsResponse.class);
		return res;
	}

	/**
	 * API instance creation function.
	 * 
	 * @param groupId       Business Group ID
	 * @param assetId       
	 * @param version       Asset version
	 * @param instanceLabel 
	 * @return Registration result.
	 * @throws AppException
	 */
	public APIInstanceResponse saveAPIInstance(String groupId, String assetId, String version, String instanceLabel)
			throws AppException {

		APIInstanceRequest apiInstance = new APIInstanceRequest(groupId, assetId, version, instanceLabel);

		String path = String.format(Const.API_INSTANCE_END_POINT, Const.ANYPOINT_ORG, Const.ANYPOINT_ENV,
				StringUtils.EMPTY, StringUtils.EMPTY);
		String resbody = HttpClientUtil.sendRequest(path, Const.POST, apiInstance);

		APIInstanceResponse res = HttpClientUtil.makeResponse(resbody, APIInstanceResponse.class);
		log("saveAPIInstance. {}", res);
		return res;
	}

	/**
	 * API instance creation function.
	 * 
	 * @param assetId
	 * @param instanceLabel
	 * @return Registration result.
	 * @throws AppException
	 */
	public APIInstanceResponse saveAPIInstance(String assetId, String instanceLabel) throws AppException {

		ExchangeAssetResponse param = new ExchangeAPICaller().findAsset(assetId);
		APIInstanceResponse response = new APIManagerAPICaller().saveAPIInstance(param.getGroupId(), param.getAssetId(),
				param.getVersion(), instanceLabel);
		return response;
	}

	/**
	 * API instance update function.
	 * 
	 * @param environmentApiId API Instance ID
	 * @param assetVersion     Asset version
	 * @return Update result.
	 * @throws AppException
	 */
	public APIInstanceResponse updateAPIInstance(String environmentApiId, String assetVersion) throws AppException {

		APIInstanceRequestForAssetVersion apiInstance = new APIInstanceRequestForAssetVersion();
		apiInstance.setAssetVersion(assetVersion);

		String path = String.format(Const.API_INSTANCE_END_POINT, Const.ANYPOINT_ORG, Const.ANYPOINT_ENV,
				"/", environmentApiId);
		String resbody = HttpClientUtil.sendRequest(path, Const.PATCH, apiInstance);

		APIInstanceResponse res = HttpClientUtil.makeResponse(resbody, APIInstanceResponse.class);
		log("updateAPIInstance. {}", res);
		return res;
	}

	/**
	 * API instance deletion function.
	 * 
	 * @param environmentApiId API Instance ID
	 * @return Deletion result.
	 * @throws AppException
	 */
	public Boolean deleteAPIInstance(String environmentApiId) throws AppException {

		String path = String.format(Const.API_INSTANCE_END_POINT, Const.ANYPOINT_ORG, Const.ANYPOINT_ENV,
				"/", environmentApiId);
		String resbody = HttpClientUtil.sendRequest(path, Const.DELETE, null);

		log("deletePolicy. {}", resbody);
		if (!StringUtils.equals("204", resbody)) {
			throw new AppException();
		}
		return true;
	}

	/**
	 * SLA tier search function.
	 * 
	 * @param environmentApiId API Instance ID
	 * @return search results.
	 * @throws AppException
	 */
	public TiersResponse findTier(String environmentApiId) throws AppException {

		String path = String.format(Const.TIERS_END_POINT, Const.ANYPOINT_ORG, Const.ANYPOINT_ENV,
				environmentApiId, StringUtils.EMPTY, StringUtils.EMPTY);
		String resbody = HttpClientUtil.sendRequest(path, Const.GET, null);

		TiersResponse res = HttpClientUtil.makeResponse(resbody, TiersResponse.class);
		log("findTier. {}", res);
		return res;
	}

	/**
	 * SLA tier creation function.
	 * 
	 * @param environmentApiId         API Instance ID
	 * @param name                     SLA tier name
	 * @param description              
	 * @param autoApprove              
	 * @param maximumRequests          
	 * @param timePeriodInMilliSeconds 
	 * @return Registration result.
	 * @throws AppException
	 */
	public TierResponse saveSLATier(String environmentApiId, String name,
			String description, Boolean autoApprove,
			Integer maximumRequests, Integer timePeriodInMilliSeconds) throws AppException {

		TierRequest tier = new TierRequest(name, description, autoApprove,
				maximumRequests, timePeriodInMilliSeconds);

		String path = String.format(Const.TIERS_END_POINT, Const.ANYPOINT_ORG, Const.ANYPOINT_ENV,
				environmentApiId, StringUtils.EMPTY, StringUtils.EMPTY);
		String resbody = HttpClientUtil.sendRequest(path, Const.POST, tier);

		TierResponse res = HttpClientUtil.makeResponse(resbody, TierResponse.class);
		log("saveSLATier. {}", res);
		return res;
	}

	/**
	 * SLA tier creation function.
	 * 
	 * @param environmentApiId API Instance ID
	 * @return Registration result.
	 * @throws AppException
	 */
	public void saveSLATiers(String environmentApiId) throws AppException {

		for (Map<String, Object> tier : Const.TIERS) {

			saveSLATier(environmentApiId,
					(String) tier.get("name"),
					(String) tier.get("description"),
					(Boolean) tier.get("autoApprove"),
					(Integer) tier.get("maximumRequests"),
					(Integer) tier.get("timePeriodInMilliSeconds"));
		}
	}

	/**
	 * SLA tier deletion function.
	 * 
	 * @param environmentApiId API Instance ID
	 * @param tierId           
	 * @return 削除結果
	 * @throws AppException
	 */
	public Boolean deleteSLATier(String environmentApiId, Integer tierId) throws AppException {

		String path = String.format(Const.TIERS_END_POINT, Const.ANYPOINT_ORG, Const.ANYPOINT_ENV,
				environmentApiId, "/", tierId);
		String resbody = HttpClientUtil.sendRequest(path, Const.DELETE, null);

		log("deletePolicy. {}", resbody);
		if (!StringUtils.equals("204", resbody)) {
			throw new AppException();
		}
		return true;
	}

	/**
	 * SLA層削除機能
	 * 
	 * @param environmentApiId API Instance ID
	 * @throws AppException アプリケーション例外
	 */
	public void deleteSLATiers(String environmentApiId) throws AppException {

		TiersResponse response = findTier(environmentApiId);
		if (response.getTiers() == null) {
			return;
		}
		for (Map<String, Object> tier : response.getTiers()) {
			deleteSLATier(environmentApiId, (Integer) tier.get("id"));
		}
	}

	/**
	 * ポリシー検索機能
	 * 
	 * @param environmentApiId API Instance ID
	 * @return 検索結果
	 * @throws AppException アプリケーション例外
	 */
	public PoliciesResponse findPolicy(String environmentApiId) throws AppException {

		String path = String.format(Const.POLICY_END_POINT, Const.ANYPOINT_ORG, Const.ANYPOINT_ENV,
				environmentApiId, StringUtils.EMPTY, StringUtils.EMPTY);
		String resbody = HttpClientUtil.sendRequest(path, Const.GET, null);

		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		PoliciesResponse res = HttpClientUtil.makeResponse(resbody, PoliciesResponse.class);
		log("findPolicy. {}", res);
		return res;
	}

	/**
	 * ポリシー登録機能
	 * 
	 * @param environmentApiId API Instance ID
	 * @param policyName       ポリシー名
	 * @return 登録結果
	 * @throws AppException アプリケーション例外
	 */
	public PolicyResponse savePolicy(String environmentApiId,
			String policyName) throws AppException {

		PolicyRequest policy = PolicyRequest.factory(policyName);

		String path = String.format(Const.POLICY_END_POINT, Const.ANYPOINT_ORG, Const.ANYPOINT_ENV,
				environmentApiId, StringUtils.EMPTY, StringUtils.EMPTY);
		String resbody = HttpClientUtil.sendRequest(path, Const.POST, policy);

		PolicyResponse res = HttpClientUtil.makeResponse(resbody, PolicyResponse.class);
		log("savePolicy. {}", res);
		if (res.getId() == null) {
			throw new AppException();
		}
		return res;
	}

	/**
	 * ポリシー登録機能
	 * 
	 * @param environmentApiId API instance ID
	 * @throws AppException
	 */
	public void savePolicies(String environmentApiId) throws AppException {

		for (String policyName : Const.POLICIES.keySet()) {
			savePolicy(environmentApiId, policyName);
		}
	}

	/**
	 * ポリシー削除機能
	 * 
	 * @param environmentApiId API Instance ID
	 * @param policyId         
	 * @return 削除結果
	 * @throws AppException
	 */
	public Boolean deletePolicy(String environmentApiId, String policyId) throws AppException {

		String path = String.format(Const.POLICY_END_POINT, Const.ANYPOINT_ORG, Const.ANYPOINT_ENV,
				environmentApiId, "/", policyId);
		String resbody = HttpClientUtil.sendRequest(path, Const.DELETE, null);

		log("deletePolicy. {}", resbody);
		if (!StringUtils.equals("204", resbody)) {
			throw new AppException();
		}
		return true;
	}

	/**
	 * ポリシー削除機能
	 * 
	 * @param environmentApiId API Instance ID
	 * @throws AppException
	 */
	public void deletePolicies(String environmentApiId) throws AppException {

		PoliciesResponse response = findPolicy(environmentApiId);
		if (response.getPolicies() == null) {
			return;
		}
		for (Map<String, Object> policy : response.getPolicies()) {
			deletePolicy(environmentApiId, policy.get("policyId").toString());
		}
	}

	/**
	 * APIアラート検索機能
	 * 
	 * @param environmentApiIdAPI Instance ID
	 * @return 検索結果
	 * @throws AppException
	 */
	public APIAlertResponse[] findAPIAlert(String environmentApiId) throws AppException {

		String path = String.format(Const.API_ALERT_END_POINT, Const.ANYPOINT_ORG, Const.ANYPOINT_ENV,
				environmentApiId, StringUtils.EMPTY, StringUtils.EMPTY);
		String resbody = HttpClientUtil.sendRequest(path, Const.GET, null);

		APIAlertResponse[] res = HttpClientUtil.makeResponse(resbody, APIAlertResponse[].class);
		log("findAPIAlert. {}", res);
		return res;
	}

	/**
	 * アラート登録機能
	 * 
	 * @param environmentApiId API Instance ID
	 * @param alertType        
	 * @param policyId         
	 * @return 登録結果
	 * @throws AppException
	 */
	public TierResponse saveAPIAlert(String environmentApiId, String apiInstanceName, String alertType,
			Integer policyId)
			throws AppException {

		APIAlertRequest alert = APIAlertRequest.factory(apiInstanceName, alertType, policyId);

		String path = String.format(Const.API_ALERT_END_POINT, Const.ANYPOINT_ORG, Const.ANYPOINT_ENV,
				environmentApiId, StringUtils.EMPTY, StringUtils.EMPTY);
		String resbody = HttpClientUtil.sendRequest(path, Const.POST, alert);

		TierResponse res = HttpClientUtil.makeResponse(resbody, TierResponse.class);
		log("saveAPIAlert. {}", res);
		if (res.getId() == null) {
			throw new AppException("API alert could not be registed.");
		}
		return res;
	}

	/**
	 * アラート登録機能
	 * 
	 * @param environmentApiId APIインスタンスID
	 * @param apiInstanceName
	 * @throws AppException
	 */
	public void saveAlerts(String environmentApiId, String apiInstanceName) throws AppException {

		PoliciesResponse policy = findPolicy(environmentApiId);
		for (String alertType : Const.ALERTS) {
			saveAPIAlert(environmentApiId, apiInstanceName, alertType, policy.get(alertType));
		}
	}

	/**
	 * アラート削除機能
	 * 
	 * @param environmentApiId APIインスタンスID
	 * @param alertId          アラートID
	 * @throws AppException アプリケーション例外
	 */
	public Boolean deleteAPIAlert(String environmentApiId, String alertId) throws AppException {

		String path = String.format(Const.API_ALERT_END_POINT, Const.ANYPOINT_ORG, Const.ANYPOINT_ENV,
				environmentApiId, "/", alertId);
		String resbody = HttpClientUtil.sendRequest(path, Const.DELETE, null);

		log("deleteAPIAlert. {}", resbody);
		if (!StringUtils.equals("204", resbody)) {
			throw new AppException();
		}
		return true;
	}

	/**
	 * アラート削除機能
	 * 
	 * @param environmentApiId APIインスタンスID
	 * @param alertId          アラートID
	 * @throws AppException
	 */
	public void deleteAlerts(String environmentApiId) throws AppException {

		APIAlertResponse[] alerts = findAPIAlert(environmentApiId);
		if (alerts == null) {
			return;
		}
		for (APIAlertResponse alert : alerts) {
			Boolean flg = deleteAPIAlert(environmentApiId, alert.getId());
			if (!flg) {
				throw new AppException();
			}
		}
	}

	/**
	 * JSON形式ログ出力機能
	 * 
	 * @param marker マーカー 例： "Application Response {}"
	 * @param res    出力オブジェクト
	 * @throws AppException
	 */
	public static void log(String marker, Object res) throws AppException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		String json;
		try {
			json = mapper.writeValueAsString(res);
		} catch (JsonProcessingException e) {
			throw new AppException(e);
		}
		log.debug(marker, json);
	}
}
