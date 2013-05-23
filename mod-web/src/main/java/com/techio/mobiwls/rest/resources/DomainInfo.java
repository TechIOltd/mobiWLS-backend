/**
 * 
 */
package com.techio.mobiwls.rest.resources;

/**
 * @author slavikos
 *
 */
public class DomainInfo {
	
	private String configurationVersion;
	private boolean consoleEnabled;
	
	private String consolePath;
	private String domainVersion;
	private Long lastModificationTime;
	/**
	 * domain's name
	 */
	private String name;
	/**
	 * productionMode flag
	 */
	private boolean productionMode;
	public String getConfigurationVersion() {
		return configurationVersion;
	}
	public String getConsolePath() {
		return consolePath;
	}
	public String getDomainVersion() {
		return domainVersion;
	}
	public Long getLastModificationTime() {
		return lastModificationTime;
	}
	public String getName() {
		return name;
	}
	public boolean isConsoleEnabled() {
		return consoleEnabled;
	}
	
	public boolean isProductionMode() {
		return productionMode;
	}
	
	public void setConfigurationVersion(String configurationVersion) {
		this.configurationVersion = configurationVersion;
	}
	
	public void setConsoleEnabled(boolean consoleEnabled) {
		this.consoleEnabled = consoleEnabled;
	}
	
	public void setConsolePath(String consolePath) {
		this.consolePath = consolePath;
	}
	
	public void setDomainVersion(String domainVersion) {
		this.domainVersion = domainVersion;
	}
	public void setLastModificationTime(Long lastModificationTime) {
		this.lastModificationTime = lastModificationTime;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setProductionMode(boolean productionMode) {
		this.productionMode = productionMode;
	}
}
