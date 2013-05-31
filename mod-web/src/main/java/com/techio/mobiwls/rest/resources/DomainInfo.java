/**
 * 
 */
package com.techio.mobiwls.rest.resources;

import java.util.ArrayList;
import java.util.List;

/**
 * @author slavikos
 * 
 */
public class DomainInfo {
	
	public static final String CACHE_KEY = "DomainInfo";

	private List<ClusterInfo> clusters = new ArrayList<ClusterInfo>();
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
	private String consolePath
	;
	private List<JDBCResourceInfo> dataSources = new ArrayList<JDBCResourceInfo>();
	
	private List<DeploymentInfo> deployments = new ArrayList<DeploymentInfo>();
	/**
	 * Defines the common version of all servers in a domain. In a domain
	 * containing servers that are not all at the same release version, this
	 * attribute is used to determine the feature level that servers will
	 * assume.
	 */
	private String domainVersion;

	private List<JMSServerInfo> jmsServers = new ArrayList<JMSServerInfo>();

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

	/**
	 * The servers configured within this domain.
	 */
	private List<ServerInfo> servers = new ArrayList<ServerInfo>();

	private String version;

	public List<ClusterInfo> getClusters() {
		return clusters;
	}

	public String getConfigurationVersion() {
		return configurationVersion;
	}

	public String getConsolePath() {
		return consolePath;
	}



	public List<JDBCResourceInfo> getDataSources() {
		return dataSources;
	}



	public List<DeploymentInfo> getDeployments() {
		return deployments;
	}



	public String getDomainVersion() {
		return domainVersion;
	}



	public List<JMSServerInfo> getJmsServers() {
		return jmsServers;
	}



	public Long getLastModificationTime() {
		return lastModificationTime;
	}



	public String getName() {
		return name;
	}



	public List<ServerInfo> getServers() {
		return servers;
	}



	public String getVersion() {
		return version;
	}



	public boolean isConsoleEnabled() {
		return consoleEnabled;
	}



	public boolean isProductionMode() {
		return productionMode;
	}



	public void setClusters(List<ClusterInfo> clusters) {
		this.clusters = clusters;
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



	public void setDataSources(List<JDBCResourceInfo> dataSources) {
		this.dataSources = dataSources;
	}



	public void setDeployments(List<DeploymentInfo> deployments) {
		this.deployments = deployments;
	}



	public void setDomainVersion(String domainVersion) {
		this.domainVersion = domainVersion;
	}



	public void setJmsServers(List<JMSServerInfo> jmsServers) {
		this.jmsServers = jmsServers;
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



	public void setServers(List<ServerInfo> servers) {
		this.servers = servers;
	}



	public void setVersion(String version) {
		this.version = version;
	}



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DomainInfo [");
		if (configurationVersion != null) {
			builder.append("configurationVersion=");
			builder.append(configurationVersion);
			builder.append(", ");
		}
		builder.append("consoleEnabled=");
		builder.append(consoleEnabled);
		builder.append(", ");
		if (consolePath != null) {
			builder.append("consolePath=");
			builder.append(consolePath);
			builder.append(", ");
		}
		if (domainVersion != null) {
			builder.append("domainVersion=");
			builder.append(domainVersion);
			builder.append(", ");
		}
		if (lastModificationTime != null) {
			builder.append("lastModificationTime=");
			builder.append(lastModificationTime);
			builder.append(", ");
		}
		if (name != null) {
			builder.append("name=");
			builder.append(name);
			builder.append(", ");
		}
		builder.append("productionMode=");
		builder.append(productionMode);
		builder.append(", ");
		if (servers != null) {
			builder.append("servers=");
			builder.append(servers);
			builder.append(", ");
		}
		if (clusters != null) {
			builder.append("clusters=");
			builder.append(clusters);
			builder.append(", ");
		}
		if (jmsServers != null) {
			builder.append("jmsServers=");
			builder.append(jmsServers);
			builder.append(", ");
		}
		if (dataSources != null) {
			builder.append("dataSources=");
			builder.append(dataSources);
			builder.append(", ");
		}
		if (deployments != null) {
			builder.append("deployments=");
			builder.append(deployments);
		}
		builder.append("]");
		return builder.toString();
	}

}
