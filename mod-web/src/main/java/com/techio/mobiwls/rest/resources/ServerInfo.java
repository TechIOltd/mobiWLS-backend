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

package com.techio.mobiwls.rest.resources;

/**
 * @author <a href="mailto:filip@techio.com">Filip Slavik</a>
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
	
	/**
	 * runtime information related to this server
	 */
	private ServerRuntimeInfo runtimeInfo;

	public ServerRuntimeInfo getRuntimeInfo() {
		return runtimeInfo;
	}

	public void setRuntimeInfo(ServerRuntimeInfo runtimeInfo) {
		this.runtimeInfo = runtimeInfo;
	}

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
