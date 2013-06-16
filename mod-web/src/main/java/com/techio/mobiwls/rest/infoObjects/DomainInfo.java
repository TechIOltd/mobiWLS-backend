/*
 * Copyright (c) 2013 TechIO Ltd (http://techio.com)
 * 
 * (http://techio.com/portfolio/mobile-applications)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.techio.mobiwls.rest.infoObjects;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:filip@techio.com">Filip Slavik</a>
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
	private String consolePath;
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
