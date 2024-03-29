/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package mule.ci.tool.app;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mule.ci.tool.app.api.APIManagerAPICaller;
import mule.ci.tool.app.api.CloudhubAPICaller;
import mule.ci.tool.app.api.ExchangeAPICaller;
import mule.ci.tool.app.api.model.APIAssetsResponse;
import mule.ci.tool.app.api.model.ApplicationResponse;
import mule.ci.tool.app.api.model.ExchangeAssetResponse;
import mule.ci.tool.app.util.AppException;
import mule.ci.tool.app.util.Const;

public class App {

	private static final Logger log = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {

		try {
			CommandLine line = setMenue(args);
			taskControler(line);
		} catch (AppException e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * Menu Settings
	 * 
	 * @param args Command Line
	 * @return Command Line
	 * @throws AppException Application Exception
	 */
	private static CommandLine setMenue(String[] args) throws AppException {

		CommandLineParser parser = new DefaultParser();
		CommandLine line = null;
		try {
			line = parser.parse(setMainOptions(), args);
		} catch (ParseException e) {
			throw new AppException(e);
		}
		return line;
	}

	/**
	 * Main options information settings.  
	 * 
	 * @return Options
	 */
	private static Options setMainOptions() {
		Options options = new Options()
		.addOption("create", false,
				"Create the specified settings. <all|apiInstance|slaTiers|policies|alerts|application|runtimeAlerts>")
		.addOption("update", false, "Update the specified settings. <all|apiInstance|application>")
		.addOption("delete", false,
				"Delete the specified setting.<all|apiInstance|slaTiers|policies|alerts|application|runtimeAlerts>")
		.addOption("all", false, "The targets are the API Instance,the specific SLA tiers,the specific policies and the specific API alert.")
		.addOption("api", false, "The targets is the API Instance.")
		.addOption("tiers", false, "The targets is the specific SLA tiers.")
		.addOption("policies", false, "The targets is the specific policies.")
		.addOption("alerts", false, "The targets is the specific API alert.")
		.addOption("application", false, "The targets is the application.")
		.addOption("runtimeAlerts", false, "The targets is the specific runtime alert.")
		.addOption("org", true, "The business Group settings.")
		.addOption("env", true, "The environment settings.")
		.addOption("config", true, "The configuration file path settings.")
		.addOption("help", false, "Show top menue.");
		return options;
	}

	/**
	 * Task control function.
	 * 
	 * @param line Command Line
	 * @throws AppException Application Exception.
	 */
	private static void taskControler(CommandLine line) throws AppException {

		executeSettingsMenue(line);
		executeDeleteMenue(line);
		executeSaveMenue(line);
		executeUpdateMenue(line);
		if (line.hasOption("all")) {
			log.info("all process start!!");
			init();
			log.info("save process finished!!");
		}
		if (line.hasOption("help")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("Mule CD Tool", setMainOptions());
		}
	}

	private static void executeSettingsMenue(CommandLine line) throws AppException {

		
		if (line.hasOption("config")) {
			String filepath = line.getOptionValue("config");
			Const.init(filepath);
			log.info("Bussines Group Name is {}.", Const.ANYPOINT_ORG);
		}
		if (!line.hasOption("config")) {
			Const.init("config.yaml");
		}
		if (line.hasOption("org")) {
			String bussinesGroupName = line.getOptionValue("org");
			Const.ANYPOINT_ORG = bussinesGroupName;
			log.info("Bussines Group Name is {}.", Const.ANYPOINT_ORG);
		}
		if (line.hasOption("env")) {
			String environmentName = line.getOptionValue("env");
			Const.ANYPOINT_ENV = environmentName;
			log.info("Environment Name is {}.", Const.ANYPOINT_ENV);
		}
	}

	private static void executeDeleteMenue(CommandLine line) throws AppException {
		
		if (line.hasOption("delete")) {
			log.info("deletion function start!!");

			APIAssetsResponse param = new APIManagerAPICaller().findAPIInstance();
			String environmentApiId = param.getId();

			if (line.hasOption("runtimeAlerts")) {
				deleteRuntimeAlerts();
			}
			if (line.hasOption("application")) {
				deleteApplication();
			}
			if (line.hasOption("alerts")) {
				deleteAlerts(environmentApiId);
			}
			if (line.hasOption("policies")) {
				deletePolicies(environmentApiId);
			}
			if (line.hasOption("tiers")) {
				deleteSLATiers(environmentApiId);
			}
			if (line.hasOption("api")) {
				deleteAPIInstance(environmentApiId);
			}
			log.info("deletion function finished!!");
		}
	}

	private static void executeSaveMenue(CommandLine line) throws AppException {
		
		if (line.hasOption("create")) {
			log.info("creation function start!!");

			if (line.hasOption("all")) {
				createRuntimeAlerts();
				String environmentApiId = createAPIInstance();
				createSLATiers(environmentApiId);
				createPolicies(environmentApiId);
				createAlerts(environmentApiId);
				createApplication();
			}
			if (line.hasOption("runtimeAlerts")) {
				createRuntimeAlerts();
			}
			if (line.hasOption("api")) {
				createAPIInstance();
			}
			if (line.hasOption("application")) {
				createApplication();
			}
			if (line.hasOption("alerts")) {
				APIAssetsResponse param = new APIManagerAPICaller().findAPIInstance();
				String environmentApiId = param.getId();
				createAlerts(environmentApiId);
			}
			if (line.hasOption("policies")) {
				APIAssetsResponse param = new APIManagerAPICaller().findAPIInstance();
				String environmentApiId = param.getId();
				createPolicies(environmentApiId);
			}
			if (line.hasOption("tiers")) {
				APIAssetsResponse param = new APIManagerAPICaller().findAPIInstance();
				String environmentApiId = param.getId();
				createSLATiers(environmentApiId);
			}

			log.info("creation function finished!!");
		}
	}

	private static void executeUpdateMenue(CommandLine line) throws AppException {
		if (line.hasOption("update")) {
			log.info("update process start!!");

			APIAssetsResponse param = new APIManagerAPICaller().findAPIInstance();
			String environmentApiId = param.getId();

			if (line.hasOption("application")) {
				updateApplication();
			}
			if (line.hasOption("api")) {
				updateAPIInstance(environmentApiId);
			}
			log.info("update process finished!!");
		}
	}

	/**
	 * All of Mule API component initiation functions.
	 * 
	 * @throws AppException
	 */
	public static void init() throws AppException {

		APIAssetsResponse param = new APIManagerAPICaller().findAPIInstance();
		String environmentApiId = param.getId();
		
		if (StringUtils.isBlank(environmentApiId)) {
			createAPIInstance();
		}
		deleteSLATiers(environmentApiId);
		createSLATiers(environmentApiId);
		deletePolicies(environmentApiId);
		createPolicies(environmentApiId);
		deleteAlerts(environmentApiId);
		createAlerts(environmentApiId);
		deleteRuntimeAlerts();
		createRuntimeAlerts();
		if (existApplication()) {
			updateApplication();
		} else {
			createApplication();
		}
	}

	/**
	 * All of Mule API updating function.
	 * 
	 * @throws AppException
	 */
	public static void updateMuleAPI() throws AppException {

		APIAssetsResponse param = new APIManagerAPICaller().findAPIInstance();
		String environmentApiId = param.getId();
		
		log.debug("updateMuleAPI start!");
		if (!existAPIInstance(environmentApiId)) {
			environmentApiId = createAPIInstance();
			createSLATiers(environmentApiId);
			createPolicies(environmentApiId);
			createAlerts(environmentApiId);
		}
		if (existApplication()) {
			updateApplication();
		} else {
			createApplication();
		}
		log.debug("updateMuleAPI finish!");
	}

	/**
	 * All component deletion function.
	 * 
	 * @throws AppException
	 */
	public static void deleteAll() throws AppException {

		deleteRuntimeAlerts();
		deleteApplication();
		APIAssetsResponse param = new APIManagerAPICaller().findAPIInstance();
		String environmentApiId = param.getId();
		deleteAPIInstance(environmentApiId);
	}

	/**
	 * The API instance existence check function.
	 * 
	 * @throws AppException
	 */
	public static Boolean existAPIInstance(String environmentApiId) throws AppException {

		log.debug("APIInstance existedeing check!");
		if (StringUtils.isBlank(environmentApiId))
			return false;
		return true;
	}

	/**
	 * Create API Instance function.
	 * 
	 * @throws AppException
	 */
	public static String createAPIInstance() throws AppException {

		APIManagerAPICaller caller = new APIManagerAPICaller();
		log.debug("API Instance name is {}.", Const.API_NAME);
		caller.saveAPIInstance(Const.ASSET_ID, Const.API_INSTANCE_LABEL);
		APIAssetsResponse param = caller.findAPIInstance();
		return param.getId();
	}

	/**
	 * The API instance update function.
	 * 
	 * @throws AppException
	 */
	public static void updateAPIInstance(String environmentApiId) throws AppException {

		ExchangeAPICaller excaller = new ExchangeAPICaller();
		APIManagerAPICaller caller = new APIManagerAPICaller();
		ExchangeAssetResponse exres = excaller.findAsset(Const.ASSET_ID);
		caller.updateAPIInstance(environmentApiId, exres.getVersion());
	}

	/**
	 * The API instance deletion function.
	 * 
	 * @throws AppException
	 */
	public static void deleteAPIInstance(String environmentApiId) throws AppException {

		if (StringUtils.isBlank(environmentApiId))
			throw new AppException("The API is null.");
		APIManagerAPICaller caller = new APIManagerAPICaller();
		caller.deleteAPIInstance(environmentApiId);
	}

	/**
	 * The specific SLA tiers creation function.
	 * 
	 * @throws AppException
	 */
	public static void createSLATiers(String environmentApiId) throws AppException {

		if (StringUtils.isBlank(environmentApiId))
			throw new AppException("The API is null.");
		APIManagerAPICaller caller = new APIManagerAPICaller();
		caller.saveSLATiers(environmentApiId);
	}

	/**
	 * The specific SLA tiers deletion function. 
	 * 
	 * @throws AppException
	 */
	public static void deleteSLATiers(String environmentApiId) throws AppException {

		if (StringUtils.isBlank(environmentApiId))
			throw new AppException("The API is null.");
		APIManagerAPICaller caller = new APIManagerAPICaller();
		caller.deleteSLATiers(environmentApiId);
	}

	/**
	 * The specific policies deletion function
	 * 
	 * @throws AppException Application Exception.
	 */
	public static void createPolicies(String environmentApiId) throws AppException {

		if (StringUtils.isBlank(environmentApiId))
			throw new AppException("The API is null.");
		APIManagerAPICaller caller = new APIManagerAPICaller();
		caller.savePolicies(environmentApiId);
	}

	/**
	 * The specific policies deletion function
	 * 
	 * @throws AppException Application Exception.
	 */
	public static void deletePolicies(String environmentApiId) throws AppException {

		if (StringUtils.isBlank(environmentApiId))
			throw new AppException("The API is null.");
		APIManagerAPICaller caller = new APIManagerAPICaller();
		caller.deletePolicies(environmentApiId);
	}

	/**
	 * Create All API alerts.
	 * 
	 * @throws AppException Application Exception.
	 */
	public static void createAlerts(String environmentApiId) throws AppException {

		APIManagerAPICaller caller = new APIManagerAPICaller();
		caller.saveAlerts(environmentApiId, Const.API_NAME);
	}

	/**
	 * Delete All API alerts.
	 * 
	 * @throws AppException Application Exception.
	 */
	public static void deleteAlerts(String environmentApiId) throws AppException {

		if (StringUtils.isBlank(environmentApiId))
			throw new AppException("The API is null.");
		APIManagerAPICaller caller = new APIManagerAPICaller();
		caller.deleteAlerts(environmentApiId);
	}

	/**
	 * checking exist Application
	 * 
	 * @throws AppException Application Exception.
	 */
	public static Boolean existApplication() throws AppException {

		CloudhubAPICaller caller = new CloudhubAPICaller();
		ApplicationResponse application = caller.findApplication(Const.ENV_NAME);
		if (application == null) {
			return false;
		}
		return true;
	}

	/**
	 * Create an application.
	 * 
	 * @throws AppException Application Exception.
	 */
	public static void createApplication() throws AppException {

		APIManagerAPICaller apicaller = new APIManagerAPICaller();
		APIAssetsResponse res = apicaller.findAPIInstance();
		CloudhubAPICaller caller = new CloudhubAPICaller();
		String domain = String.format(Const.DOMAIN, Const.ENV_NAME);
		caller.saveApplication(domain, res.getId());
	}

	/**
	 * Update an application.
	 * 
	 * @throws AppException Application Exception.
	 */
	public static void updateApplication() throws AppException {

		APIManagerAPICaller apicaller = new APIManagerAPICaller();
		APIAssetsResponse res = apicaller.findAPIInstance();
		CloudhubAPICaller caller = new CloudhubAPICaller();
		String domain = String.format(Const.DOMAIN, Const.ENV_NAME);
		caller.updateApplication(domain, res.getId());
	}

	/**
	 * Delete an application.
	 * 
	 * @throws AppException Application Exception.
	 */
	public static void deleteApplication() throws AppException {

		CloudhubAPICaller caller = new CloudhubAPICaller();
		String domain = String.format(Const.DOMAIN, Const.ENV_NAME);
		caller.deleteApplication(domain);
	}

	/**
	 * Save All Runtime alerts.
	 * 
	 * @throws AppException Application Exception.
	 */
	public static void createRuntimeAlerts() throws AppException {

		CloudhubAPICaller caller = new CloudhubAPICaller();
		caller.saveRuntimeAlerts();
	}

	/**
	 * Delete All Runtime alerts.
	 * 
	 * @throws AppException Application Exception.
	 */
	public static void deleteRuntimeAlerts() throws AppException {

		CloudhubAPICaller caller = new CloudhubAPICaller();
		caller.deleteRuntimeAlerts();
	}
}
