package mule.ci.tool.app.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.Yaml;

@SuppressWarnings("unchecked")
public class Const {

	public static final String ANYPOINT_END_POINT = "https://anypoint.mulesoft.com";

	public static final String ME_END_POINT = ANYPOINT_END_POINT + "/accounts/api/me";
	
	public static final String ORG_END_POINT = ANYPOINT_END_POINT + "/accounts/api/organizations/%s";
	
	public static final String TOKEN_END_POINT = ANYPOINT_END_POINT + "/accounts/api/v2/oauth2/token";

	public static final String USER_END_POINT = ANYPOINT_END_POINT + "/accounts/api/organizations/%s/users?type=all";

	public static final String MEMBER_END_POINT = ANYPOINT_END_POINT + "/accounts/api/organizations/%s/members";

	public static final String EXCHANGE_ASSET_END_POINT = ANYPOINT_END_POINT
			+ "/exchange/api/v2/assets/search?organizationId=%s&search=%s";

	public static final String API_ASSET_END_POINT = ANYPOINT_END_POINT
			+ "/apimanager/api/v1/organizations/%s/apiSpecs?latestVersionsOnly=true&limit=50&offset=0&searchTerm=acc";

	public static final String API_MANAGER_API_END_POINT = ANYPOINT_END_POINT
			+ "/apimanager/api/v1/organizations/%s/environments/%s";

	public static final String API_INSTANCE_END_POINT = API_MANAGER_API_END_POINT + "/apis%s%s";

	public static final String TIERS_END_POINT = API_MANAGER_API_END_POINT + "/apis/%s/tiers%s%s";

	public static final String POLICY_END_POINT = API_MANAGER_API_END_POINT + "/apis/%s/policies%s%s";

	public static final String API_ALERT_END_POINT = API_MANAGER_API_END_POINT + "/apis/%s/alerts%s%s";

	public static final String APPLICATION_END_POINT = ANYPOINT_END_POINT + "/cloudhub/api/v2/applications%s%s";

	public static final String RUNTIME_ALERT_END_POINT = ANYPOINT_END_POINT + "/cloudhub/api/v2/alerts%s%s";

	public static final String COUDHUB_ACCOUNT_END_POINT = ANYPOINT_END_POINT + "/cloudhub/api/account";
	
	public static final String ASSETS_UPLOAD_END_POINT = "https://uploads.github.com/repos/%s/%s/releases/%s/assets?name=%s";
	
	public static final String ASSETS_DOWNLOAD_END_POINT = "https://github.com/%s/%s/releases/download/%s/%s";
	
	public static final String RELEASES_END_POINT = "https://api.github.com/repos/%s/%s/releases%s%s";
		
	public static final String GET = "GET";

	public static final String POST = "POST";

	public static final String PUT = "PUT";

	public static final String PATCH = "PATCH";

	public static final String DELETE = "DELETE";

//	public static String CONFIG_YAML_FILE_PATH = "config.yaml";

	public static String APPLICATION_FILE_PATH;

	public static String ANYPOINT_ORG;

	public static String ANYPOINT_ENV;

	public static String ENVIRONMENT_CLIENT_ID;

	public static String ENVIRONMENT_CLIENT_SECRET;

	public static String ANYPOINT_CLIENT_ID;

	public static String ANYPOINT_CLIENT_SECRET;

	public static String GRANT_TYPE;

	public static String ACCESS_TOKEN;

	public static String DOMAIN;

	public static String ENV_NAME;

	public static String ASSET_ID;

	public static String API_NAME;
	
	public static String API_INSTANCE_LABEL;

	public static Map<String, String> API_ID_KEYS;

	public static String RUNTIME_VERSION;

	public static String WORKER_TYPE;

	public static Integer WORKERS;

	public static String REGION;

	public static Map<String, String> RUNTIME_PROPERTIES;

	public static Boolean AUTOMATICALLY_RESTART;

	public static Boolean PERSISTENT_QUEUES;

	public static Boolean USE_OBJECT_STORE_V2;

	public static Boolean ENABLE_MONITORING;

	public static List<Map<String, Object>> TIERS;

	public static Map<String, Object> POLICIES;

	public static List<String> ALERTS;

	public static List<String> RUNTIME_ALERTS;

	public static List<String> ALERT_RECIPIENTS;
	
	public static void init(String path) {

		// Reading config file path.
		Yaml common = new Yaml();
		Path input = Paths.get(path);
		System.out.println("reading config file path : " + input.toAbsolutePath());
		InputStream in = null;
		try {
			in = Files.newInputStream(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Map<String, Object> commonConf = (Map<String, Object>) common.loadAs(in, Map.class);

		// Setting Client Certification.
		Map<String, Object> connApp = (Map<String, Object>) commonConf.get("connectedApplication");
		ANYPOINT_CLIENT_ID = (String) connApp.get("clientId");
		ANYPOINT_CLIENT_SECRET = (String) connApp.get("clientSecret");
		GRANT_TYPE = (String) connApp.get("grantType");
		
		String anypointClientID = System.getenv("ANYPOINT_CLIENT_ID");
		if (StringUtils.isNotBlank(anypointClientID)) {
			ANYPOINT_CLIENT_ID = anypointClientID;
		}
		String anypointClientSecret = System.getenv("ANYPOINT_CLIENT_SECRET");
		if (StringUtils.isNotBlank(anypointClientSecret)) {
			ANYPOINT_CLIENT_SECRET = System.getenv("ANYPOINT_CLIENT_SECRET") ;
		}

		// 組織情報
//		BUSSINES_GROUPS = (Map<String, Object>) commonConf.get("organization");

		// Environment settings.
		Map<String, Object> org = (Map<String, Object>) commonConf.get("organization");
		ANYPOINT_ORG = (String) org.get("bussinesGroupID");
		ANYPOINT_ENV = (String) org.get("environmentID");
		ENV_NAME = (String) org.get("environmentName");
		ENVIRONMENT_CLIENT_ID = (String) org.get("environmentClientID");
        ENVIRONMENT_CLIENT_SECRET = (String) org.get("environmentClientSecret");
		String anypointOrg = System.getenv("ANYPOINT_ORG");
		if (StringUtils.isNotBlank(anypointOrg)) {
			ANYPOINT_ORG = anypointOrg;
		}
		String anypointEnv = System.getenv("ANYPOINT_ENV");
		if (StringUtils.isNotBlank(anypointEnv)) {
			ANYPOINT_ENV = anypointEnv;
		}
		
		// API manager settings.
		Map<String, String> apiInstance = (Map<String, String>) commonConf.get("apiInstance");
		API_NAME = (String) apiInstance.get("apiName");
		ASSET_ID = (String) apiInstance.get("assetId");
		API_INSTANCE_LABEL = (String) apiInstance.get("apiInstanceLabel");
		
		// Runtime manager settings
		Map<String, Object> runtime = (Map<String, Object>) commonConf.get("runtime");
		if (runtime != null) {
			DOMAIN = (String) runtime.get("domain");
			APPLICATION_FILE_PATH = (String) runtime.get("filename");
			RUNTIME_VERSION = (String) runtime.get("runtimeVersion");
			Map<String, Object> worker = (Map<String, Object>) runtime.get("worker");
			WORKER_TYPE = (String) worker.get("type");
			WORKERS = (Integer) worker.get("workers");
			REGION = (String) worker.get("region");
			RUNTIME_PROPERTIES = (Map<String, String>) runtime.get("properties");
			AUTOMATICALLY_RESTART = (Boolean) runtime.get("automaticallyRestart");
			PERSISTENT_QUEUES = (Boolean) runtime.get("persistentQueues");
			USE_OBJECT_STORE_V2 = (Boolean) runtime.get("useObjectStorev2");
			ENABLE_MONITORING = (Boolean) runtime.get("enableMonitoring");
		}

		// SLA層情報
		TIERS = (List<Map<String, Object>>) commonConf.get("tiers");

		// ポリシーリスト
		POLICIES = (Map<String, Object>) commonConf.get("policies");

		// アラートリスト
		ALERTS = (List<String>) commonConf.get("alerts");

		// ランタイムアラートリスト
		RUNTIME_ALERTS = (List<String>) commonConf.get("runtimeAlerts");

		// アラート受信者
		ALERT_RECIPIENTS = (List<String>) commonConf.get("alertRecipients");
	}
}
