package com.techio.mobiwls.rest.resources;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HealthStatusOverview implements Serializable {
	
	public static final String CACHE_KEY = "HealthStatusOverview";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	/**
	 * List of failed servers.
	 */
	public List<String> failedServers = new ArrayList<String>();
	
	/**
	 * List of servers in critical state.
	 */
	public List<String> criticalServers = new ArrayList<String>();
	
	/**
	 * List of overloaded servers.
	 */
	public List<String> overloadedServers = new ArrayList<String>();
	
	/**
	 * List of servers in warning state.
	 */
	public List<String> warningServers = new ArrayList<String>();
	
	/**
	 * List of healthy servers.
	 */
	public List<String> healthyServers = new ArrayList<String>();

	public List<String> getFailedServers() {
		return failedServers;
	}

	public void setFailedServers(List<String> failedServers) {
		this.failedServers = failedServers;
	}

	public List<String> getCriticalServers() {
		return criticalServers;
	}

	public void setCriticalServers(List<String> criticalServers) {
		this.criticalServers = criticalServers;
	}

	public List<String> getOverloadedServers() {
		return overloadedServers;
	}

	public void setOverloadedServers(List<String> overloadedServers) {
		this.overloadedServers = overloadedServers;
	}

	public List<String> getWarningServers() {
		return warningServers;
	}

	public void setWarningServers(List<String> warningServers) {
		this.warningServers = warningServers;
	}

	public List<String> getHealthyServers() {
		return healthyServers;
	}

	public void setHealthyServers(List<String> healthyServers) {
		this.healthyServers = healthyServers;
	}
	
	
}
