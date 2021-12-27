package mule.ci.tool.app.api.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class OrganizationResponse {
	
    private String name;
    private String id;
    private String clientId;
    private List<String> parentOrganizationIds;
    private List<String> subOrganizationIds;
    private List<String> tenantOrganizationIds;
    private Map<String,Object> subscription;
    private Map<String,Object> properties;
    private List<Map<String,Object>> environments;
    private Map<String,Object> entitlements;
    private Integer sessionTimeout;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public List<String> getParentOrganizationIds() {
		return parentOrganizationIds;
	}
	public void setParentOrganizationIds(List<String> parentOrganizationIds) {
		this.parentOrganizationIds = parentOrganizationIds;
	}
	public List<String> getSubOrganizationIds() {
		return subOrganizationIds;
	}
	public void setSubOrganizationIds(List<String> subOrganizationIds) {
		this.subOrganizationIds = subOrganizationIds;
	}
	public List<String> getTenantOrganizationIds() {
		return tenantOrganizationIds;
	}
	public void setTenantOrganizationIds(List<String> tenantOrganizationIds) {
		this.tenantOrganizationIds = tenantOrganizationIds;
	}
	public Map<String, Object> getSubscription() {
		return subscription;
	}
	public void setSubscription(Map<String, Object> subscription) {
		this.subscription = subscription;
	}
	public Map<String, Object> getProperties() {
		return properties;
	}
	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}
	public List<Map<String, Object>> getEnvironments() {
		return environments;
	}
	public void setEnvironments(List<Map<String, Object>> environments) {
		this.environments = environments;
	}
	public Map<String, Object> getEntitlements() {
		return entitlements;
	}
	public void setEntitlements(Map<String, Object> entitlements) {
		this.entitlements = entitlements;
	}
	public Integer getSessionTimeout() {
		return sessionTimeout;
	}
	public void setSessionTimeout(Integer sessionTimeout) {
		this.sessionTimeout = sessionTimeout;
	}
}
