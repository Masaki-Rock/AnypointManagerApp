package mule.ci.tool.app.api;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mule.ci.tool.app.util.AppException;
import mule.ci.tool.app.util.Const;

public class AccessManagementAPICallerTest {
	
	private static final Logger log = LoggerFactory.getLogger(AccessManagementAPICallerTest.class);
	
	@Before
	public void setUp() {
		Const.init("config.yaml");
	}
	
//	@Test
	public void findUser() throws AppException {
		AccessManagementAPICaller caller = new AccessManagementAPICaller();
		caller.findUser();
	}
	
//	@Test
	public void findMember() throws AppException {
		AccessManagementAPICaller caller = new AccessManagementAPICaller();
		caller.findMember();
	}
	
	@Test
	public void findMemberByUsername() throws AppException {
		AccessManagementAPICaller caller = new AccessManagementAPICaller();
		Map<String,Object> user = caller.findMember("dev29_masakikawaguchi");
		log.debug("hit user id {}", user.get("id"));
	}
}
