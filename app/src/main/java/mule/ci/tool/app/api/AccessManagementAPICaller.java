package mule.ci.tool.app.api;

import java.util.Map;

import mule.ci.tool.app.api.model.ClientCredential;
import mule.ci.tool.app.api.model.CommonResponse;
import mule.ci.tool.app.api.model.MeResponse;
import mule.ci.tool.app.api.model.OrganizationResponse;
import mule.ci.tool.app.util.AppException;
import mule.ci.tool.app.util.Const;
import mule.ci.tool.app.util.HttpClientUtil;

/**
 * 認証やユーザ検索関連クラス
 * @author masaki.kawaguchi
 */
public class AccessManagementAPICaller {

	/**
	 * ビジネスグループ検索機能
	 * @return 検索結果
	 * @throws AppException アプリケーション例外
	 */
	public String findBussinesGroup() throws AppException {
		
		String resbody = HttpClientUtil.sendRequest(Const.ME_END_POINT, Const.GET, null);
		MeResponse res = HttpClientUtil.makeResponse(resbody, MeResponse.class);
		
		res.getUser();
		return null;
	}
	
	/**
	 * 環境検索機能
	 * @return 検索結果
	 * @throws AppException アプリケーション例外
	 */
	public String findEnvironment() throws AppException {
		
		String path = String.format(Const.ORG_END_POINT, Const.ANYPOINT_ORG);
		String resbody = HttpClientUtil.sendRequest(path, Const.GET, null);
		OrganizationResponse res = HttpClientUtil.makeResponse(resbody, OrganizationResponse.class);
		res.getId();
		return null;
	}
	
	/**
	 * ユーザ検索機能
	 * @return 検索結果
	 * @throws AppException アプリケーション例外
	 */
	public CommonResponse findUser() throws AppException {
		
		String path = String.format(Const.USER_END_POINT, Const.ANYPOINT_ORG);
		String resbody = HttpClientUtil.sendRequest(path, Const.GET, null);
		CommonResponse res = HttpClientUtil.makeResponse(resbody, CommonResponse.class);
		return res;
	}
	
	/**
	 * メンバー検索機能
	 * @return 検索結果
	 * @throws AppException アプリケーションエラー
	 */
	public CommonResponse findMember() throws AppException {
		
		String path = String.format(Const.MEMBER_END_POINT, Const.ANYPOINT_ORG);
		String resbody = HttpClientUtil.sendRequest(path, Const.GET, null);
		CommonResponse res = HttpClientUtil.makeResponse(resbody, CommonResponse.class);
		return res;
	}
	
	private CommonResponse users;
	
	/**
	 * メンバー検索機能
	 * @param mailAddress メールアドレス
	 * @return メンバーID結果
	 * @throws AppException アプリケーション例外
	 */
	public Map<String,Object> findMember(String username) throws AppException {
		
		if (this.users == null) {
			this.users = findMember();
		}
		return this.users.getUser(username);
	}
	
	/**
	 * クライアント認証機能
	 * @return 認証結果
	 * @throws AppException アプリケーション例外
	 */
	public ClientCredential getToken() throws AppException {
		
		String resbody = HttpClientUtil.sendRequestForJson(Const.TOKEN_END_POINT, Const.POST, new ClientCredential(), null);
		ClientCredential res = HttpClientUtil.makeResponse(resbody, ClientCredential.class);
		return res;
	}
	
	/**
	 * アクセストークン取得機能
	 * @return アクセストークン
	 * @throws AppException アプリケーション例外
	 */
    public String getAccessToken() throws AppException {
    	
    	if (Const.ACCESS_TOKEN != null) return Const.ACCESS_TOKEN;
    
    	ClientCredential credential = getToken();
    	Const.ACCESS_TOKEN = credential.getAccess_token();
    	return Const.ACCESS_TOKEN;
    }
}
