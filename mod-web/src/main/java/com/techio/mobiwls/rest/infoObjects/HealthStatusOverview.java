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
	 * List of servers in critical state.
	 */
	public List<String> criticalServers = new ArrayList<String>();
	
	/**
	 * List of failed servers.
	 */
	public List<String> failedServers = new ArrayList<String>();
	
	/**
	 * List of healthy servers.
	 */
	public List<String> healthyServers = new ArrayList<String>();
	
	/**
	 * List of overloaded servers.
	 */
	public List<String> overloadedServers = new ArrayList<String>();
	
	/**
	 * List of servers in warning state.
	 */
	public List<String> warningServers = new ArrayList<String>();

	public List<String> getCriticalServers() {
		return criticalServers;
	}

	public List<String> getFailedServers() {
		return failedServers;
	}

	public List<String> getHealthyServers() {
		return healthyServers;
	}

	public List<String> getOverloadedServers() {
		return overloadedServers;
	}

	public List<String> getWarningServers() {
		return warningServers;
	}

	public void setCriticalServers(List<String> criticalServers) {
		this.criticalServers = criticalServers;
	}

	public void setFailedServers(List<String> failedServers) {
		this.failedServers = failedServers;
	}

	public void setHealthyServers(List<String> healthyServers) {
		this.healthyServers = healthyServers;
	}

	public void setOverloadedServers(List<String> overloadedServers) {
		this.overloadedServers = overloadedServers;
	}

	public void setWarningServers(List<String> warningServers) {
		this.warningServers = warningServers;
	}
	
	
}
