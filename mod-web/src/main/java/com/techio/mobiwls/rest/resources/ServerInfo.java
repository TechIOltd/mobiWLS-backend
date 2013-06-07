/**
 * 
 */
package com.techio.mobiwls.rest.resources;

/**
 * @author slavikos
 *
 */
public class ServerInfo {
	/**
	 * The default TCP port that this server uses to listen for regular (non-SSL) incoming connections.
	 */
	private Integer listenPort;
	
	/**
	 * An alphanumeric name for this server instance. (Spaces are not valid.)
	 */
	private String name;
	
	/**
	 * The IP address or DNS name this server uses to listen for incoming connections.
	 */
	private String listenAddress;
	
	/**
	 * The name of the cluster this server belongs to (if targeted to a cluster)
	 */
	private String partOfCluster;

	public Integer getListenPort() {
		return listenPort;
	}

	public void setListenPort(Integer listenPort) {
		this.listenPort = listenPort;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getListenAddress() {
		return listenAddress;
	}

	public void setListenAddress(String listenAddress) {
		this.listenAddress = listenAddress;
	}

	public String getPartOfCluster() {
		return partOfCluster;
	}

	public void setPartOfCluster(String partOfCluster) {
		this.partOfCluster = partOfCluster;
	}

	@Override
	public String toString() {
		return "ServerInfo [listenPort=" + listenPort + ", name=" + name
				+ ", listenAddress=" + listenAddress + ", partOfCluster="
				+ partOfCluster + "]";
	}
	
}
