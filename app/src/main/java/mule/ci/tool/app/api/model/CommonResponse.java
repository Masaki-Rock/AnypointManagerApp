package mule.ci.tool.app.api.model;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class CommonResponse {

	private Integer total;
	
    private List<Map<String,Object>> data;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<Map<String, Object>> getData() {
		return data;
	}

	public void setData(List<Map<String, Object>> data) {
		this.data = data;
	}
	
	public Map<String,Object> getUser(String username) {
		List<Map<String, Object>> users = this.getData();
		for (Map<String, Object> user: users) {
			if (StringUtils.equals((String) user.get("username"), username)) {
				return user;
			}
		}
		return null;
	}
}
