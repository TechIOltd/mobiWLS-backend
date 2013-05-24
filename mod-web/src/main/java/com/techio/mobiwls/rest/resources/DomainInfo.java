/**
 * 
 */
package com.techio.mobiwls.rest.resources;

/**
 * @author slavikos
 * 
 */
public class DomainInfo {

	/**
	 * The release identifier for the configuration. This identifier will be
	 * used to indicate the version of the configuration. All server generated
	 * configurations will be established with the release identifier of the
	 * running server.
	 */
	private String configurationVersion;
	/**
	 * Specifies whether the Administration Server automatically deploys the
	 * Administration Console in the current domain.
	 */
	private boolean consoleEnabled;
	/**
	 * The context path that you want to use in URLs that specify the
	 * Administration Console
	 */
	private String consolePath;
	/**
	 * Defines the common version of all servers in a domain. In a domain
	 * containing servers that are not all at the same release version, this
	 * attribute is used to determine the feature level that servers will
	 * assume.
	 */
	private String domainVersion;
	/**
	 * Return the last time this domain was updated. This is guaranteed to be
	 * unique for a given transactional modification.
	 */
	private Long lastModificationTime;
	/**
	 * The user-specified name of this MBean instance.
	 */
	private String name;
	/**
	 * Specifies whether all servers in this domain run in production mode.
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
