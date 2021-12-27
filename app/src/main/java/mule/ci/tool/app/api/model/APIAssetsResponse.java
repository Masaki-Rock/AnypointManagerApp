package mule.ci.tool.app.api.model;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import mule.ci.tool.app.util.AppException;
import mule.ci.tool.app.util.Const;

@JsonIgnoreProperties(ignoreUnknown = true)
public class APIAssetsResponse {

//	private static final Logger log = LoggerFactory.getLogger(APIAssetsResponse.class);

	@JsonProperty("total")
	private Integer total;

	@JsonProperty("assets")
	private List<Map<String, Object>> assets;

	/**
	 * API ID取得機能
	 * 
	 * @param instanceName APIインスタンスネーム
	 * @return APIID
	 * @throws AppException アプリケーション例外
	 */
	@SuppressWarnings("unchecked")
	public String getId() throws AppException {

		for (Map<String, Object> asset : this.assets) {
			List<Map<String, Object>> apis = (List<Map<String, Object>>) asset.get("apis");
			for (Map<String, Object> api : apis) {
				String instanceLabel = (String) api.get("instanceLabel");
				if (StringUtils.equals(Const.API_INSTANCE_LABEL, instanceLabel)) {
					return "" + api.get("id");
				}
			}
		}
		return null;
	}

	/**
	 * API ID存在チェック機能
	 * 
	 * @return 存在チェック結果
	 * @throws AppException アプリケーション例外
	 */
	public Boolean exist() throws AppException {

		if (getId() == null) {
			return false;
		}
		return true;
	}
}
